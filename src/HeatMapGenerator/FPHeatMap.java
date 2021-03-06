package HeatMapGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FPHeatMap {

	private ArrayList< XY > data1_ = new ArrayList< XY >();
	private ArrayList< XY > data2_ = new ArrayList< XY >();
	private CustomComparator comp_ = new CustomComparator();
	private double max_y_ = 0;
	
	public FPHeatMap( String filename1, String filename2 ) throws IOException {
		BufferedReader in = new BufferedReader( new FileReader( filename1 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			XY xy = new XY( Double.parseDouble( s.split( "," )[ 0 ] ), Double.parseDouble( s.split( "," )[ 1 ] ) );
			if( xy.Y > max_y_ ) {
				max_y_ = xy.Y;
			}
			data1_.add( xy );
		}
		in.close();

		in = new BufferedReader( new FileReader( filename2 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			XY xy = new XY( Double.parseDouble( s.split( "," )[ 0 ] ), Double.parseDouble( s.split( "," )[ 1 ] ) );
			if( xy.Y > max_y_ ) {
				max_y_ = xy.Y;
			}
			data2_.add( xy );
		}
		in.close();
		finalize2();
	}

	private void finalize2() {
		Collections.sort( data1_, comp_ );
		Collections.sort( data2_, comp_ );
		System.out.println( data1_.get( 0 ).X );
		System.out.println( data1_.get( data1_.size() - 1 ).X );
	}

	public int numWithinR_slow( double x, double y, double R, ArrayList< XY > data ) {
		if( y > max_y_ + R ) return 0;
		int count = 0;
		double R2 = R * R;
		XY temp = new XY( x, y );
		for( XY xy : data ) {
			// if( Math.abs( xy.X - temp.X ) > R ) continue;
			// if( Math.abs( xy.Y - temp.Y ) > R ) continue;
			if( temp.distance_squared( xy ) <= R2 )
				++count;
		}
		return count;
	}

	public int numWithinR_data1( double x, double y, double R ) {
		if( y > max_y_ + R ) return 0;	
		XY bounding_xy = new XY( x - R - 0.1, y );
		int lower_bound = Collections.binarySearch( data1_, bounding_xy, comp_ );
		if( lower_bound < 0 ) {
			lower_bound = 0;
		}
		
		/*bounding_xy = new XY( x + R + 0.1, y );
		int upper_bound = Collections.binarySearch( data1_, bounding_xy, comp_ );
		if( upper_bound < 0 ) {
			upper_bound = Math.abs( upper_bound );
		}*/

		int count = 0;
		double R2 = R * R;
		XY temp = new XY( x, y );

		for( int i = lower_bound; i < data1_.size(); ++i ) {
			XY xy = data1_.get( i );
			if( temp.distance_squared( xy ) <= R2 ) {
				++count;
			}
			/*else if( xy.X - x > R ) {
				break;
			}*/
		}

		/*if( Math.random() < 0.005 ) {// Test slow way
			int real_count = numWithinR_slow( x, y, R, data1_ );
			if( real_count != count ) {
				System.err.println( "real_count != count (1)" );
				System.err.println( real_count + " != " + count );
				System.err.println( x + "\t" + y );
				System.exit( 1 );
			}
		}*/
		return count;
	}

	public int numWithinR_data2( double x, double y, double R ) {
		if( y > max_y_ + R ) return 0;	

		XY bounding_xy = new XY( x - R - 0.1, y );
		int lower_bound = Collections.binarySearch( data2_, bounding_xy, comp_ );
		if( lower_bound < 0 ) {
			lower_bound = 0;
		}

		/*bounding_xy = new XY( x + R + 0.1, y );
		int upper_bound = Collections.binarySearch( data1_, bounding_xy, comp_ );
		if( upper_bound < 0 ) {
			upper_bound = Math.abs( upper_bound ) + 2;
		}
		if( upper_bound > data2_.size() ) {
			upper_bound = data2_.size();
		}*/
		
		int count = 0;
		double R2 = R * R;
		XY temp = new XY( x, y );

		for( int i = lower_bound; i < data2_.size(); ++i ) {
			XY xy = data2_.get( i );
			if( temp.distance_squared( xy ) <= R2 ) {
				++count;
			}
			/*else if( xy.X - x > R ) {
				break;
			}*/
		}

		if( Math.random() < 0.005 ) {// Test slow way
			int real_count = numWithinR_slow( x, y, R, data2_ );
			if( real_count != count ) {
				System.err.println( "real_count != count (2)" );
				System.err.println( real_count + " != " + count );
				//System.err.println( data2_.get( lower_bound ).X + "\t" + data2_.get( upper_bound - 1 ).X );
				System.err.println( x + "\t" + y );
				System.exit( 1 );
			}
		}
		return count;
	}

	private static class CustomComparator implements Comparator< XY > {
		@Override
		public int compare( XY o1, XY o2 ) {
			return Double.compare( o1.X, o2.X );
		}
	}

	private final static class XY {
		public double X;
		public double Y;

		public XY( double x, double y ) {
			X = x;
			Y = y;
		}

		public double distance_squared( XY other ) {
			return Math.pow( X - other.X, 2 ) + Math.pow( Y - other.Y, 2 );
		}

	}
}
