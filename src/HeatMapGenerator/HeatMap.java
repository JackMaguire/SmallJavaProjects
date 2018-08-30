package HeatMapGenerator;

public class HeatMap {

	private final double[][] heat_;
	private final double[] x_vals_;
	private final double[] y_vals_;
	// private final int min

	private final double r_squared_;

	private double max_heat_ = 0;

	public HeatMap( double min_x, double max_x, double dx, double min_y, double max_y, double dy, double r ) {
		r_squared_ = r * r;

		final int num_x = 1 + (int) ( ( max_x - min_x ) / ( dx ) );
		final int num_y = 1 + (int) ( ( max_y - min_y ) / ( dy ) );
		heat_ = new double[ num_x ][ num_y ];
		for( int i = 0; i < num_x; ++i ) {
			for( int j = 0; j < num_y; ++j ) {
				heat_[ i ][ j ] = 0;
			}
		}

		x_vals_ = new double[ num_x ];
		for( int i = 0; i < num_x; ++i ) {
			x_vals_[ i ] = min_x + ( i * dx );
		}

		y_vals_ = new double[ num_y ];
		for( int i = 0; i < num_y; ++i ) {
			y_vals_[ i ] = min_y + ( i * dy );
		}
	}

	public void addVal( double x, double y ) {
		for( int i = 0; i < x_vals_.length; ++i ) {
			for( int j = 0; j < y_vals_.length; ++j ) {
				final double dist_sq = ( x_vals_[ i ] - x ) * ( x_vals_[ i ] - x )
						+ ( y_vals_[ j ] - y ) * ( y_vals_[ j ] - y );
				if( dist_sq <= r_squared_ ) {
					++heat_[ i ][ j ];
					if( heat_[ i ][ j ] > max_heat_ ) {
						max_heat_ = heat_[ i ][ j ];
					}
				}
			}
		}
	}

	public void normalize() {
		for( int i = 0; i < x_vals_.length; ++i ) {
			for( int j = 0; j < y_vals_.length; ++j ) {
				heat_[ i ][ j ] /= max_heat_;
			}
		}
		max_heat_ = 1;
	}

	public void smoothen() {
		final int num_x = x_vals_.length;
		final int num_y = y_vals_.length;
		double[][] heat2 = new double[ num_x ][ num_y ];

		for( int i = 0; i < num_x; ++i ) {
			for( int j = 0; j < num_y; ++j ) {
				// val/2 + sum(8 nbrs)/16
				double val = heat2[ i ][ j ] / 2;
				heat2[ i ][ j ] = heat_[ i ][ j ];

				for( int di = -1; di < 2; ++di ) {
					for( int dj = -1; dj < 2; ++dj ) {
						if( di == dj )
							continue;
						final int newi = i + di;
						final int newj = j + dj;
						if( newi < 0 || newj < 0 || newi == num_x || newj == num_y )
							continue;
						heat2[ i ][ j ] += heat_[ newi ][ newj ] / 16.0;
					}
				}
			}
		}
	}

	public double interpolated_val( double x, double y ) {
		boolean x_match = false;
		int I = 0;
		for( int i = 0; i < x_vals_.length; ++i ) {
			if( x == x_vals_[ i ] ) {
				I = i;
				x_match = true;
				break;
			} else if( x < x_vals_[ i ] ) {
				I = i;
				x_match = true;
				break;
			} else if( x > x_vals_[ i ] && i == x_vals_.length - 1 ) {
				I = i;
				x_match = true;
				break;
			} else if( x > x_vals_[ i ] && x < x_vals_[ i + 1 ] ) {
				I = i;
				x_match = false;
				break;
			}
		} // for i

		boolean y_match = false;
		int J = 0;
		for( int j = 0; j < y_vals_.length; ++j ) {
			if( y == y_vals_[ j ] ) {
				J = j;
				y_match = true;
				break;
			} else if( y < y_vals_[ j ] ) {
				J = j;
				y_match = true;
				break;
			} else if( y > y_vals_[ j ] && j == y_vals_.length - 1 ) {
				J = j;
				y_match = true;
				break;
			} else if( y > y_vals_[ j ] && y < y_vals_[ j + 1 ] ) {
				J = j;
				y_match = false;
				break;
			}
		} // for j

		if( x_match && y_match ) {
			return heat_[ I ][ J ];
		} else if( x_match ) {
			return calc_xmatch( x, y, I, J );
		} else if( y_match ) {
			return calc_ymatch( x, y, I, J );
		} else {
			return calc_no_match( x, y, I, J );
		}

	}

	private double calc_xmatch( double x, double y, int I, int J ) {
		double smaller_y_val = heat_[ I ][ J ];
		double larger_y_val = heat_[ I ][ J + 1 ];
		double frac = get_interp_factor( y_vals_[ J ], y_vals_[ J + 1 ], y );
		return ( smaller_y_val * ( 1 - frac ) ) + ( larger_y_val * frac );
	}

	private double calc_ymatch( double x, double y, int I, int J ) {
		double smaller_x_val = heat_[ I ][ J ];
		double larger_x_val = heat_[ I + 1 ][ J ];
		double frac = get_interp_factor( x_vals_[ I ], x_vals_[ I + 1 ], x );
		return ( smaller_x_val * ( 1 - frac ) ) + ( larger_x_val * frac );
	}

	private double dist( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( Math.pow( x1 - x2, 2 ) + Math.pow( y1 - y2, 2 ) );
	}

	private double calc_no_match( double x, double y, int I, int J ) {
		final double dist_top_left = dist( x, y, x_vals_[ I ], y_vals_[ J + 1 ] );
		final double dist_top_right = dist( x, y, x_vals_[ I + 1 ], y_vals_[ J + 1 ] );
		final double dist_bottom_left = dist( x, y, x_vals_[ I ], y_vals_[ J ] );
		final double dist_bottom_right = dist( x, y, x_vals_[ I + 1 ], y_vals_[ J ] );

		//System.out.println( dist_top_left );
		//System.out.println( dist_top_right );
		//System.out.println( dist_bottom_left );
		//System.out.println( dist_bottom_right );
		
		final double val_top_left = heat_[ I ][ J + 1 ];
		final double val_top_right = heat_[ I + 1 ][ J + 1 ];
		final double val_bottom_left = heat_[ I ][ J ];
		final double val_bottom_right = heat_[ I + 1 ][ J ];

		//System.out.println( val_top_left );
		//System.out.println( val_top_right );
		//System.out.println( val_bottom_left );
		//System.out.println( val_bottom_right );
		
		final double numerator = ( val_top_left / dist_top_left ) + ( val_top_right / dist_top_right )
				+ ( val_bottom_left / dist_bottom_left ) + ( val_bottom_right / dist_bottom_right );
		final double denominator = ( 1.0 / dist_top_left ) + ( 1.0 / dist_top_right )
				+ ( 1.0 / dist_bottom_left ) + ( 1.0 / dist_bottom_right );
		
		//System.out.println( numerator );
		//System.out.println( denominator );
		//System.exit( 1 );
		return numerator / denominator;
	}

	private double get_interp_factor( double before, double after, double i ) {
		return ( i - before ) / ( after - before );
	}
}
