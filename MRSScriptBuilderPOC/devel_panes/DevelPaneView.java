import java.awt.*;
import javax.swing.*;

public class DevelPaneView extends JPanel {
    
    private final DevelPaneModel model_;
    private final JSlider slider_ = new JSlider( 0, 255 );

    public DevelPaneView( DevelPaneModel model ){
	model_ = model;
	add( slider_ );
    }

    public void setController( DevelPaneController controller ){
	slider_.addChangeListener( controller );
    }

    @Override
    public void paintComponent( Graphics g ){
	//super.paint( g );

	final int val = model_.getValue();
	g.setColor( new Color( val, val, val ) );
	g.fillRect( 0, 0, getWidth(), getHeight() );

	//System.out.println( val );
    }

}
