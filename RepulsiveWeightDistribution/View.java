import java.awt.*;
import javax.swing.*;

public class View extends JPanel {

    public View(){

    }

    public void paint( Graphics g ){
	g.setColor( Color.BLACK );
	g.drawLine( 1, 1, 5, 5 );
    }

}
