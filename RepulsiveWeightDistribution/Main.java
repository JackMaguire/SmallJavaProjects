import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main( String[] args ){
	JFrame F = new JFrame( "SlideShow" );
	F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	F.setExtendedState( JFrame.MAXIMIZED_BOTH );
	//F.setUndecorated( true );
	F.add( new MainView() );
	F.setVisible( true );
    }

    private final static class MainView extends JPanel {

	private final static View view_ = new View();
	private final static ControlPanel control_panel_ = new ControlPanel( view_ );
	private final static ControlPanelTop control_panel_top_ = new ControlPanelTop( view_, control_panel_ );

	public MainView(){
	    setLayout( new BorderLayout() );
	    add( view_, BorderLayout.CENTER );
	    add( control_panel_, BorderLayout.SOUTH );
	    add( control_panel_top_, BorderLayout.NORTH );
	}

    }

}
