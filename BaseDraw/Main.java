import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main( String[] args ){
	JFrame F = new JFrame( "View" );
	F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	F.setExtendedState( JFrame.MAXIMIZED_BOTH );
	F.add( new MainView() );
	F.setVisible( true );
    }

    private final static class MainView extends JPanel {
	public MainView(){
	}

	public final void paint( Graphics g ){
	    g.setColor( Color.WHITE );
	}

    }

}
