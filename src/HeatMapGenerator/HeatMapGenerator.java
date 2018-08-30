package HeatMapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class HeatMapGenerator {

	// Settings:
	public static String filename = "";
	public static String output = "";

	private static int min = 0;
	private static int max = 50;
	private static int res = 1000;
	
	private static Colorer colorer = new BWColorer();
	
	public static void main( String[] args ) throws IOException {

		parse_args( args );

		IntHeatMap hm = new IntHeatMap( min, max, 1, min, max, 1, IntHeatMap.AddType.SINGLE );
		BufferedReader in = new BufferedReader( new FileReader( filename ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			int x = Integer.parseInt( s.split( "," )[0] );
			int y = Integer.parseInt( s.split( "," )[1] );
			hm.addVal( x, y );
		}
		in.close();

		hm.normalize();
		BufferedImage bi = hm.createImage( colorer );
		File outputfile = new File( output );
		ImageIO.write(bi, "png", outputfile);
		
		hm.smoothen();
		BufferedImage bi2 = hm.createImage( colorer );
		File outputfile2 = new File( output + ".smooth.png" );
		ImageIO.write(bi2, "png", outputfile2);
	}
	
	public static void main2( String[] args ) throws IOException {

		parse_args( args );

		HeatMap hm = new HeatMap( min, max, 1, min, max, 1, 1 );
		BufferedReader in = new BufferedReader( new FileReader( filename ) );
		for( String s = in.readLine(); s != null; s = in.readLine() ) {
			double x = Double.parseDouble( s.split( "," )[0] );
			double y = Double.parseDouble( s.split( "," )[1] );
			hm.addVal( x, y );
		}
		in.close();

		hm.normalize();
		BufferedImage bi = drawImage( hm, new BWColorer() );
		File outputfile = new File( output );
		ImageIO.write(bi, "png", outputfile);
		
		hm.smoothen();
		BufferedImage bi2 = drawImage( hm, new BWColorer() );
		File outputfile2 = new File( output + ".smooth.png" );
		ImageIO.write(bi2, "png", outputfile2);
	}

	private static BufferedImage drawImage( HeatMap hm, Colorer colorer ) {
		BufferedImage img = new BufferedImage( res, res, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g2 = img.createGraphics();
		for( int i=0; i<res; ++i) {
			for( int j=0; j<res; ++j ) {
				int x = i;
				int y = res - j;
				double x_val = min + (max-min)*((double) x) / res;
				double y_val = min + (max-min)*((double) y) / res;
				Color c = colorer.colorForVal( hm.interpolated_val( x_val, y_val ) );
				g2.setColor( c );
				g2.fillRect( x, y, 1, 1 );
			}
		}
		return img;
	}

	private static void parse_args( String[] args ) {
		for( int i = 0; i < args.length; ++i ) {
			if( args[ i ].equals( "-filename" ) ) {
				filename = args[ ++i ];
				continue;
			}
			if( args[ i ].equals( "-output" ) ) {
				output = args[ ++i ];
				continue;
			}
		}
		
		if( filename.length() == 0 ) {
			System.out.println( "Need -filename" );
			System.exit( 1 );
		}
		
		if( output.length() == 0 ) {
			System.out.println( "Need -output" );
			System.exit( 1 );
		}
	}
}
