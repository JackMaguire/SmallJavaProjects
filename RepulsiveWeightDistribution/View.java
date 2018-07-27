import java.awt.*;
import javax.swing.*;

public class View extends JPanel {

    private final static Color packing_color_ = new Color( 10, 10, 100 );
    private final static Color minimization_color_ = new Color( 100, 10, 10 );

    private double lambda_ = 0.0;
    private double floor_  = 0.0;

    private final static double[] weights = { 0.02, 0.25, 0.55, 1.0 };

    public View(){

    }

    public void paint( Graphics g ){
	g.setColor( Color.BLACK );
	g.drawLine( 1, 1, 5, 5 );

	final int horiz_unit = getWidth() / 10;
	final int y_buffer = getHeight() / 4;

	//packing
	g.setColor( packing_color_ );
	for( int i=0; i<4; ++i ){

	    final double value = correctedPackingValue( weights[ i ] );

	    final int x = horiz_unit * ( 2 * i + 1 );
	    final int y = (int) ( y_buffer + (2*y_buffer) * (1-value) );
	    final int height = (int ) ( (2*y_buffer) * value );

	    g.fillRect( x, y, horiz_unit, height );

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
