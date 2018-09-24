package HeatMapGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FPHeatMap {

	private ArrayList< XY > data1_ = new ArrayList< XY >();
	private ArrayList< XY > data2_ = new ArrayList< XY >();
	
	public FPHeatMap( String filename1, String filename2 ) throws IOException {
		BufferedReader in = new BufferedReader( new FileReader( filename1 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			XY xy = new XY( Double.parseDouble( s.split( "," )[ 0 ] ), Double.parseDouble( s.split( "," )[ 1 ] ) );
			data1_.add( xy );
		}
		in.close();
		
		in = new BufferedReader( new FileReader( filename2 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			XY xy = new XY( Double.parseDouble( s.split( "," )[ 0 ] ), Double.parseDouble( s.split( "," )[ 1 ] ) );
			data2_.add( xy );
		}
		in.close();
	}
	
	public int numWithinR_data1( double x, double y, double R ) {
		int count = 0;
		double R2 = R * R;
		XY temp = new XY( x, y );
		for( XY xy : data1_ ) {
			if( temp.distance_squared( xy ) <= R2 )
				++count;
		}
		return count;
	}
	
	public int numWithinR_data2( double x, double y, double R ) {
		int count = 0;
		double R2 = R * R;
		XY temp = new XY( x, y );
		for( XY xy : data2_ ) {
			if( temp.distance_squared( xy ) <= R2 )
				++count;
		}
		return count;
	}
	
	private final static class XY{
		public double X;
		public double Y;
		
		public XY( double x, double y ) {
			X = x;
			Y = y;
		}
		
		public double distance_squared( XY other ) {
			return Math.pow( X*other.X, 2) + Math.pow( Y*other.Y, 2);
		}
	}
}
