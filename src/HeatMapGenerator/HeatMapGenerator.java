package HeatMapGenerator;

import java.io.*;

public class HeatMapGenerator {

	// Settings:
	public static String filename = "";
	public static String output = "";

	public static void main( String[] args ) throws IOException {

		parse_args( args );

		HeatMap hm = new HeatMap( 0, 100, 1, 0, 100, 1, 1 );
		BufferedReader in = new BufferedReader( new FileReader( filename ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			double x = Double.parseDouble( s.split( "," )[0] );
			double y = Double.parseDouble( s.split( "," )[1] );
			hm.addVal( x, y );
		}
		in.close();
		
	}

	private static void parse_args( String[] args ) {
		for( int i = 0; i < args.length; ++i ) {
			if( args[ i ].equals( "--filename" ) ) {
				filename = args[ ++i ];
				continue;
			}
			if( args[ i ].equals( "--output" ) ) {
				output = args[ ++i ];
				continue;
			}
		}
		
		if( filename.length() == 0 ) {
			System.out.println( "Need --filename" );
			System.exit( 1 );
		}
		
		if( output.length() == 0 ) {
			System.out.println( "Need --output" );
			System.exit( 1 );
		}
	}
}
