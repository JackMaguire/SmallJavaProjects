package main;

import java.awt.*;
import javax.swing.*;

import java.util.*;

public class PanelWrapper extends JPanel {

    public PanelWrapper( HashMap< String, MyMVC > panes, String key ){
	setLayout( new BorderLayout() );
	add( panes.get( key ).getNewView(), BorderLayout.CENTER );

	final SouthBar south_bar = new SouthBar( panes );
	add( south_bar, BorderLayout.SOUTH );
    }

    private final static class SouthBar extends JPanel {
	final private HashMap< String, MyMVC > panes_;

	public SouthBar( HashMap< String, MyMVC > panes ){
	    panes_ = panes;

	    final JComboBox< String > i = new JComboBox< String >();

	    Iterator< ? > it = panes_.entrySet().iterator();
	    while ( it.hasNext() ){
		Map.Entry pair = (Map.Entry) it.next();
		i.addItem( ""+pair.getKey() );
	    }
	    add( i );
	}

	public void paintComponent( Graphics g ){
	    g.setColor( Color.BLACK );
	    g.fillRect(0,0,getWidth(),getHeight());
	}
    }

}
