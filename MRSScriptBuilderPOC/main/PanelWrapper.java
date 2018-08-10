package main;

import java.awt.*;
import javax.swing.*;

public class PanelWrapper extends JPanel {

    public PanelWrapper( JPanel panel, MyMVC[] mvc_options ){
	setLayout( new BorderLayout() );
	add( panel, BorderLayout.CENTER );

	final SouthBar south_bar = new SouthBar( mvc_options );
	add( south_bar, BorderLayout.SOUTH );
    }

    private final static class SouthBar extends JPanel {
	final MyMVC[] mvc_options_;

	public SouthBar( MyMVC[] mvc_options ){
	    mvc_options_ = mvc_options;
	}

	public void paint( Graphics g ){
	    super.paint( g );

	    g.setColor( Color.BLACK );
	    g.fillRect(0,0,getWidth(),getHeight());
	}
    }

}
