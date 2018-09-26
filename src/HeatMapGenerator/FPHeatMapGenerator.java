package HeatMapGenerator;

import java.awt.BasicStroke;
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

	public static int min = 0;
	public static int max = 50;

	public static int res = 1000;
	private static int stroke_width = 3;// 3 for 1000x1000

	public static double radius = 0.5;
	
	private static double m_ = 0;
	private static double b_ = 0;

	private static Colorer colorer = new RBColorer();

	private final static int num_threads = 1;
	
	public static double[][] counts_for_set1 = new double[ res ][ res ];
	public static double[][] counts_for_set2 = new double[ res ][ res ];
	
	public static void main( String[] args ) throws IOException, InterruptedException {

		parse_args( args );

		FPHeatMap hm = new FPHeatMap( filename1, filename2 );
		//hm.finalize();

		FPHeatMapGeneratorThread[] threads = new FPHeatMapGeneratorThread[ num_threads ];
		for( int i=0; i<num_threads; ++i ) {
			threads[ i ] = new FPHeatMapGeneratorThread( hm );
		}
		
		int current_ind = 0;
		for( int i=0; i < res; ++i ) {
			//Not doing continuous chunks
			//Hopefully this improves load-balancing
			threads[ current_ind++ ].indices_to_compute.add( i );
			if( current_ind >= num_threads )
					current_ind = 0;
		}
		
		for( int i=0; i<num_threads; ++i ) {
			threads[ i ].start();
		}
		
		for( int i=0; i<num_threads; ++i ) {
			threads[ i ].join();
		}
		
		double max_count = 0;// not int on purpose
		for( int i = 0; i < res; ++i ) {
			for( int j = 0; j < res; ++j ) {
				if( counts_for_set1[ i ][ j ] > max_count )
					max_count = counts_for_set1[ i ][ j ];
				if( counts_for_set2[ i ][ j ] > max_count )
					max_count = counts_for_set2[ i ][ j ];
			}
		}

		BufferedImage bi = new BufferedImage( res, res, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2 = bi.createGraphics();
		for( int i = 0; i < res; ++i ) {
			for( int j = 0; j < res; ++j ) {
				g2.setColor(
						colorer.colorForVal( counts_for_set1[ i ][ j ] / max_count, counts_for_set2[ i ][ j ] / max_count ) );
				g2.fillRect( i, j, 1, 1 );
			}
		}

		Line line = null;
		if( m_ != 0 || b_ != 0 ) {
			line = new Line();
			line.m = m_;
			line.b = b_;
		}
		double x0 = 0;
		double x1 = (double) max;
		double y0 = b_;
		double y1 = m_ * x1 + b_;

		int i0 = 0;
		int i1 = res + 1;
		int j0 = (int) ( min + ( (double) res ) * ( y0 - min ) / ( max - min ) );
		int j1 = (int) ( min + ( (double) res ) * ( y1 - min ) / ( max - min ) );

		g2.setColor( colorer.colorForLine() );
		g2.setStroke( new BasicStroke( stroke_width ) );// 3 for 1000x1000
		g2.drawLine( i0, j0, i1, j1 );

		// Colorer colorer, int box_size, Line line, int min_x, int max_x, int width,
		// int min_y, int max_y, int height
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
