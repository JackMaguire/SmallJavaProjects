package HeatMapGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DoubleSetIntHeatMap {

	private double[][] heat1_; public double[][] heat1(){ return heat1_; }
	private double[][] heat2_; public double[][] heat2(){ return heat2_; }
	private final int[] x_vals_;
	private final int[] y_vals_;

	private final int dx_;
	private final int dy_;

	private double max_heat_ = 0;

	enum AddType {
		SINGLE, NBR4
	}

	private final AddType add_type_;
	
	public DoubleSetIntHeatMap( int min_x, int max_x, int dx, int min_y, int max_y, int dy, AddType add_type ) {
		add_type_ = add_type;
		dx_ = dx;
		dy_ = dy;
		final int num_x = 1 + (int) ( ( max_x - min_x ) / ( dx ) );
		final int num_y = 1 + (int) ( ( max_y - min_y ) / ( dy ) );
		heat1_ = new double[ num_x ][ num_y ];
		heat2_ = new double[ num_x ][ num_y ];
		for( int i = 0; i < num_x; ++i ) {
			for( int j = 0; j < num_y; ++j ) {
				heat1_[ i ][ j ] = 0;
				heat2_[ i ][ j ] = 0;
			}
		}

		x_vals_ = new int[ num_x ];
		for( int i = 0; i < num_x; ++i ) {
			x_vals_[ i ] = min_x + ( i * dx );
		}

		y_vals_ = new int[ num_y ];
		for( int i = 0; i < num_y; ++i ) {
			y_vals_[ i ] = min_y + ( i * dy );
		}
	}

	public void addVal( int x, int y, double[][] map ) {
		for( int i = 0; i < x_vals_.length; ++i ) {
			for( int j = 0; j < y_vals_.length; ++j ) {
				if( x_vals_[ i ] == x && y_vals_[ j ] == y ) {
					++map[ i ][ j ];
					if( add_type_ == AddType.NBR4 ) {
						if( i > 0 )
							++map[ i - 1 ][ j ];
						if( j > 0 )
							++map[ i ][ j - 1 ];
						if( i < x_vals_.length - 1 )
							++map[ i + 1 ][ j ];
						if( j < y_vals_.length - 1 )
							++map[ i ][ j + 1 ];
					}
					return;
				}
			}
		}
	}

	public void normalize( double[][] map ) {
		double max_heat = 0;
		int max_i = 0;
		int max_j = 0;
		System.out.println( map[ 0 ][ 0 ] );
		for( int i = 0; i < x_vals_.length; ++i ) {
			for( int j = 0; j < y_vals_.length; ++j ) {
				// System.out.println( i + j );
				if( map[ i ][ j ] > max_heat ) {
					max_i = i;
					max_j = j;
					max_heat = map[ i ][ j ];
				}
			}
		}
		System.out.println( "max_i = " + max_i + " max_j = " + max_j );

		for( int i = 0; i < x_vals_.length; ++i ) {
			for( int j = 0; j < y_vals_.length; ++j ) {
				map[ i ][ j ] /= max_heat;
			}
		}
	}

	/*public void smoothen() {
		final int num_x = x_vals_.length;
		final int num_y = y_vals_.length;
		double[][] heat2 = new double[ num_x ][ num_y ];

		for( int i = 0; i < num_x; ++i ) {
			for( int j = 0; j < num_y; ++j ) {
				// val/2 + sum(8 nbrs)/16
				heat2[ i ][ j ] = heat1_[ i ][ j ] / 2;

				for( int di = -1; di < 2; ++di ) {
					for( int dj = -1; dj < 2; ++dj ) {
						if( di == dj )
							continue;
						final int newi = i + di;
						final int newj = j + dj;
						if( newi < 0 || newj < 0 || newi == num_x || newj == num_y )
							continue;
						heat2[ i ][ j ] += heat1_[ newi ][ newj ] / 16.0;
					}
				}
			}
		}

		heat1_ = heat2;
	}*/

	int val2y( double val, int box_size, int ny ) {
		double bs = (double) box_size;
		int y = (int) ( bs * val / dy_ ) + (int) ( bs * ( ( val % dy_ ) / dy_ ) );
		System.out.println( "Value of " + val + " goes to a y of " + y );
		return ny * box_size - y;
	}

	BufferedImage createImage( Colorer colorer, int box_size, Line line ) {
		final int nx = x_vals_.length;
		final int ny = y_vals_.length;
		BufferedImage img = new BufferedImage( nx * box_size, ny * box_size, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2 = img.createGraphics();
		for( int i = 0; i < nx; ++i ) {
			for( int j = 0; j < ny; ++j ) {
				int x = i * box_size;
				int y = ( ny - j ) * box_size;
				Color c = colorer.colorForVal( heat1_[ i ][ j ] );
				g2.setColor( c );
				g2.fillRect( x, y, box_size, box_size );
			}
		}

		if( line != null ) {
			g2.setColor( Color.RED );
			g2.setStroke( new BasicStroke( 3 ) );
			int x1 = box_size / 2;
			int x2 = ( nx - 1 ) * box_size + box_size / 2;
			double x_val1 = x_vals_[ 0 ];
			double x_val2 = x_vals_[ nx - 1 ];

			double y_val1 = line.m * x_val1 + line.b;
			double y_val2 = line.m * x_val2 + line.b;
			int y1 = val2y( y_val1, box_size, ny );
			int y2 = val2y( y_val2, box_size, ny );
			g2.drawLine( x1, y1, x2, y2 );
		}
		return img;
	}
}
