import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main( String[] args ){
	JFrame F = new JFrame( "SlideShow" );
	F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	//F.setExtendedState( JFrame.MAXIMIZED_BOTH );
	//F.setUndecorated( true );
	F.setVisible( true );
	F.add( new MainView() );
    }

    private final static class MainView extends JPanel {

	private final static View view_ = new View();
	private final static ControlPanel control_panel_ = new ControlPanel( view_ );

	public MainView(){
	    setLayout( new BorderLayout() );
	    add( view_, BorderLayout.CENTER );
	    add( control_panel_, BorderLayout.SOUTH );
	}

    }

}
