package HeatMapGenerator;

import java.awt.Color;

public class RBColorer implements Colorer {

	public RBColorer() {
	}

	@Override
	public Color colorForVal( double val ) {
		int a = (int) ( 255 * val );
		if( a < 0 )
			a = 0;
		if( a > 255 )
			a = 255;

		Color c = null;
		try {
			c = new Color( 255, 255 - a, 255 - a );
		}
		catch( Exception e ) {
			System.out.println( "val: " + val + " returned a: " + a );
			System.exit( 1 );
		}
		return c;
	}

	@Override
	public Color colorForVal( double val1, double val2 ) {// val1
		int a = (int) ( 255 * val1 );
		if( a < 0 )
			a = 0;
		if( a > 255 )
			a = 255;

		int b = (int) ( 255 * val2 );
		if( b < 0 )
			b = 0;
		if( b > 255 )
			b = 255;

		return new Color( a, 0, b );
	}

	public Color colorForValNO( double val1, double val2 ) {// val1 is red, val2 is blue
		/*    val1    val2    R   G   B
		 *       0       1    0   0  255
		 *       1       0   255  0   0
		 *       0       0   255 255 255   
		 *       1       1   255  0  255
		 */

		int a = (int) ( 255 * val1 );
		if( a < 0 )
			a = 0;
		if( a > 255 )
			a = 255;

		int b = (int) ( 255 * val2 );
		if( b < 0 )
			b = 0;
		if( b > 255 )
			b = 255;

		int B = 255 - Math.max( a, b );
		int R = 255;
		if( val1 < val2 ) {
			R -= ( b - a );
		}
		int G = 255;
		if( val2 < val1 ) {
			G -= ( a - b );
		}

		return new Color( R, G, B );
	}

	@Override
	public Color colorForLine() {
		return Color.white;
	}

}
