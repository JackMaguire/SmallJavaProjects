import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ControlPanel extends JPanel implements ActionListener,ChangeListener {

    private final View view_;

    private final static JSlider lambda_slider_ = new JSlider( 0, 100 );
    private final static TextButtPair lambda_pair_ = new TextButtPair();

    private final static JSlider floor_slider_ = new JSlider( 0, 100 );
    private final static TextButtPair floor_pair_ = new TextButtPair();

    public ControlPanel( View view ){
	view_ = view;

	lambda_slider_.setValue( 0 );
	floor_slider_.setValue( 0 );

	setLayout( new GridLayout( 1, 4 ) );
	add( lambda_slider_ );
	add( lambda_pair_ );
	add( floor_slider_ );
	add( floor_pair_ );

	lambda_slider_.addChangeListener( this );
	lambda_pair_.button().addActionListener( this );
	
	floor_slider_.addChangeListener( this );
	floor_pair_.button().addActionListener( this );
    }

    public void setValues( double lambda, double floor ){
	final int lambda_int = weightToSliderValue( lambda );
	final int floor_int = weightToSliderValue( floor );

	lambda_slider_.setValue( lambda_int );
	floor_slider_.setValue( floor_int );

	lambda_pair_.field().setText( String.format( "%.2f", lambda ) );
	floor_pair_.field().setText( String.format( "%.2f", floor ) );

	view_.setLambda( lambda );
	view_.setFloor( floor );
    }

    public static double sliderValueToWeight( int val ){
	return 0.01 * val;
    }

    public static int weightToSliderValue( double val ){
	return (int) (val*100);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
	
	if( e.getSource() == lambda_pair_.button() ){
	    final double value = Double.parseDouble( lambda_pair_.field().getText() );
	    lambda_slider_.setValue( weightToSliderValue( value ) );
	    view_.setLambda( value );
	}

	else if( e.getSource() == floor_pair_.button() ){
	    final double value = Double.parseDouble( floor_pair_.field().getText() );
	    floor_slider_.setValue( weightToSliderValue( value ) );
	    view_.setFloor( value );
	}
    }

    @Override
    public void stateChanged( ChangeEvent e ){
	if( e.getSource() == lambda_slider_ ){
	    final double value = sliderValueToWeight( lambda_slider_.getValue() );
	    lambda_pair_.field().setText( String.format( "%.2f", value ) );
	    view_.setLambda( value );
	}

	else if( e.getSource() == floor_slider_ ){
	    final double value = sliderValueToWeight( floor_slider_.getValue() );
	    floor_pair_.field().setText( String.format( "%.2f", value ) );
	    view_.setFloor( value );
	}
    }

    private static class TextButtPair extends JPanel {

	private final JTextField field_ = new JTextField( "0.0" );
	private final JButton button_ = new JButton( "Set" );

	public TextButtPair(){
	    setLayout( new GridLayout( 1, 2 ) );
	    add( field_ );
	    add( button_ );
	}

	public JTextField field(){
	    return field_;
	}

	public JButton button(){
	    return button_;
	}

    }

}
