import java.awt.*;
import javax.swing.*;

public class View extends JPanel {

    private final static Color packing_color_ = new Color( 70, 116, 193 );
    private final static Color minimization_color_ = new Color( 235, 125, 60 );

    private double lambda_ = 0.0;
    private double floor_  = 0.0;

    private int font_size_ = 25;

    private final static double[] weights = { 0.02, 0.25, 0.55, 1.0 };

    public View(){}

    public final void increaseFontSize(){
	increaseFontSize( 5 );
    }

    public final void increaseFontSize( int diff ){
	font_size_ += diff;
	repaint();
    }

    public final void decreaseFontSize(){
	decreaseFontSize( 5 );
    }

    public final void decreaseFontSize( int diff ){
	font_size_ -= diff;
	repaint();
    }

    public final void paint( Graphics g ){

	final double[] values = new double[ 8 ];

	final int horiz_unit = getWidth() / 10;
	final int y_buffer = getHeight() / 4;

	final int x_axis = y_buffer * 3;

	//packing
	g.setColor( packing_color_ );
	for( int i=0; i<4; ++i ){
	    final double value = correctedPackingValue( weights[ i ] );

	    final int x = horiz_unit * ( 2 * i + 1 );
	    final int y = (int) ( y_buffer + (2*y_buffer) * (1-value) );
	    final int height = x_axis - y;

	    values[ 2*i ] = value;

	    g.fillRect( x, y, horiz_unit, height );
	}

	//minimization
	g.setColor( minimization_color_ );
	for( int i=0; i<4; ++i ){
	    final double value = ( i == 3 ? 1 : correctedMinimizationValue( weights[ i ], weights[ i + 1 ] ) );

	    final int x = horiz_unit * ( 2 * i + 2 );
	    final int y = (int) ( y_buffer + (2*y_buffer) * (1-value) );
	    final int height = x_axis - y;

	    values[ 2*i + 1 ] = value;

	    g.fillRect( x, y, horiz_unit, height );
	}


	//floor
	g.setColor( Color.BLACK );	
	if( floor_ > 0 ) {
	    final int y = (int) ( y_buffer + (2*y_buffer) * (1-floor_) );
	    final int height = x_axis - y;
	    g.fillRect( horiz_unit, y, 8*horiz_unit, height );
	}

	//value labels
	g.setFont( new Font( "TimesRoman", Font.PLAIN, font_size_ ) ); 
	for( int i=0; i<8; ++i ){
	    final int x = horiz_unit * (i+1);
	    g.drawString( String.format( "%.3f", values[ i ] ), x, 3*y_buffer + 25 );
	}
    }

    public void setLambda( double setting ){
	lambda_ = setting;
	repaint();
    }

    public void setFloor( double setting ){
	floor_ = setting;
	repaint();
    }

    private double correctedPackingValue( double value ){
	return floor_ + ( 1 - floor_ ) * value;
    }

    private double correctedMinimizationValue( double value, double next_value ){
	return floor_ + ( 1 - floor_ ) * ( lambda_ * next_value + (1 - lambda_) * value );
    }

}
