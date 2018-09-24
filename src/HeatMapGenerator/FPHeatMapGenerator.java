package HeatMapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class FPHeatMapGenerator {

	// Settings:
	public static String filename1 = "";
	public static String filename2 = "";
	public static String output = "";

	private static int min = 0;
	private static int max = 50;
	private static int res = 1000;

	private static double m_ = 0;
	private static double b_ = 0;

	private static Colorer colorer = new RBColorer();

	public static void main( String[] args ) throws IOException {

		parse_args( args );

		FPHeatMap hm = new FPHeatMap( filename1, filename2 );
		
		double radius = 3;
	
		double max_count = 0;//not int on purpose	
		for( int i = 0; i < res; ++i ) {
			double x = min + ( (double) i * (max - min) ) / res;
			for( int j = 0; j < res; ++j ) {
				double y = min + ( (double) j * (max - min) ) / res;
				double i_count = hm.numWithinR_data1( x, y, radius );
				double j_count = hm.numWithinR_data2( x, y, radius );
				if( i_count > max_count )
					max_count = i_count;
				if( j_count > max_count )
					max_count = j_count;
			}
		}
		
		System.out.println(max_count);
		
		Line line = null;
		if( m_ != 0 || b_ != 0 ) {
			line = new Line();
			line.m = m_;
			line.b = b_;
		}
		
		//Colorer colorer, int box_size, Line line, int min_x, int max_x, int width, int min_y, int max_y, int height
		BufferedImage bi = new BufferedImage( res, res, BufferedImage.TYPE_INT_ARGB );// hm.createImage( colorer, box_size, line, min, max-1, res, min, max-1, res );
		File outputfile = new File( output );
		ImageIO.write( bi, "png", outputfile );

		// hm.smoothen();
		// BufferedImage bi2 = hm.createImage( colorer );
		// File outputfile2 = new File( output + ".smooth.png" );
		// ImageIO.write(bi2, "png", outputfile2);
	}

	private static void parse_args( String[] args ) {
		for( int i = 0; i < args.length; ++i ) {
			if( args[ i ].equals( "-filename1" ) ) {
				filename1 = args[ ++i ];
				continue;
			}
			if( args[ i ].equals( "-filename2" ) ) {
				filename2 = args[ ++i ];
				continue;
			}
			if( args[ i ].equals( "-output" ) ) {
				output = args[ ++i ];
				continue;
			}
			if( args[ i ].equals( "-m" ) ) {
				m_ = Double.parseDouble( args[ ++i ] );
				continue;
			}
			if( args[ i ].equals( "-b" ) ) {
				b_ = Double.parseDouble( args[ ++i ] );
				continue;
			}
		}

		if( filename1.length() == 0 ) {
			System.out.println( "Need -filename1" );
			System.exit( 1 );
		}
		
		if( filename2.length() == 0 ) {
			System.out.println( "Need -filename2" );
			System.exit( 1 );
		}

		if( output.length() == 0 ) {
			System.out.println( "Need -output" );
			System.exit( 1 );
		}
	}

}
