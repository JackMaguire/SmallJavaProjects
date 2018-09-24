package HeatMapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Int2DHeatMapGenerator {

	// Settings:
	public static String filename1 = "";
	public static String filename2 = "";
	public static String output = "";

	private static int min = 0;
	private static int max = 50;
	private static int res = 1000;

	private static int box_size = 10;
	private static double m_ = 0;
	private static double b_ = 0;

	private static Colorer colorer = new RBColorer();

	public static void main( String[] args ) throws IOException {

		parse_args( args );

		DoubleSetIntHeatMap hm = new DoubleSetIntHeatMap( min, max, 1, min, max, 1, DoubleSetIntHeatMap.AddType.SINGLE );
		BufferedReader in = new BufferedReader( new FileReader( filename1 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			int x = (int) Double.parseDouble( s.split( "," )[ 0 ] );
			int y = (int) Double.parseDouble( s.split( "," )[ 1 ] );
			hm.addVal( x, y, hm.heat1() );
		}
		in.close();
		
		in = new BufferedReader( new FileReader( filename2 ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			int x = (int) Double.parseDouble( s.split( "," )[ 0 ] );
			int y = (int) Double.parseDouble( s.split( "," )[ 1 ] );
			hm.addVal( x, y, hm.heat2() );
		}
		in.close();

		//hm.normalize();
		Line line = null;
		if( m_ != 0 || b_ != 0 ) {
			line = new Line();
			line.m = m_;
			line.b = b_;
		}
		BufferedImage bi = hm.createImage( colorer, box_size, line );
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
			System.out.println( "Need -filename" );
			System.exit( 1 );
		}

		if( output.length() == 0 ) {
			System.out.println( "Need -output" );
			System.exit( 1 );
		}
	}

	private static BufferedImage drawImage( HeatMap hm, Colorer colorer ) {
		BufferedImage img = new BufferedImage( res, res, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2 = img.createGraphics();
		for( int i = 0; i < res; ++i ) {
			for( int j = 0; j < res; ++j ) {
				int x = i;
				int y = res - j;
				double x_val = min + ( max - min ) * ( (double) x ) / res;
				double y_val = min + ( max - min ) * ( (double) y ) / res;
				Color c = colorer.colorForVal( hm.interpolated_val( x_val, y_val ) );
				g2.setColor( c );
				g2.fillRect( x, y, 1, 1 );
			}
		}
		return img;
	}

}
