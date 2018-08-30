package HeatMapGenerator;
import java.io.*;
public class HeatMapGenerator {

	//Settings:
	public static String filename;
	public static String output;
	
	public static void main( String[] args ) throws IOException {
		
		parse_args( args );
		
	}

	private static void parse_args( String[] args ) {
		for( int i=0; i<args.length; ++i) {
			if( args[i].equals( "--filename" ) ) {
				filename = args[ ++i ];
				continue;
			}
			if( args[i].equals( "--output" ) ) {
				output = args[ ++i ];
				continue;
			}
		}
	}
}

