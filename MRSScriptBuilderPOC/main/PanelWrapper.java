package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelWrapper extends JPanel {

    public PanelWrapper( HashMap< String, MyMVC > panes, String key ){
	setLayout( new BorderLayout() );
	add( panes.get( key ).getNewView(), BorderLayout.CENTER );

	final SouthBar south_bar = new SouthBar( panes, key );
	add( south_bar, BorderLayout.SOUTH );
    }

    private final static class SouthBar extends JPanel {
	final private HashMap< String, MyMVC > panes_;

	public SouthBar( HashMap< String, MyMVC > panes, String starting_key ){
	    panes_ = panes;

	    final JComboBox< String > dropdown_menu = new JComboBox< String >();

	    String[] names = new String[ panes_.size() ];
	    int count = 0;

	    Iterator< ? > it = panes_.entrySet().iterator();
	    while ( it.hasNext() ){
		Map.Entry pair = (Map.Entry) it.next();
		names[ count++ ] = ""+pair.getKey();
	    }

	    int starting_pos = 0;

	    Arrays.sort( names );
	    for( int i=0; i<names.length; ++i ){
		String s = names[ i ];
		if( s.equals( starting_key ) ){
		    starting_pos = i;
		}
		dropdown_menu.addItem( s );
	    }

	    add( dropdown_menu );
	    dropdown_menu.setSelectedIndex( starting_pos );
	}

	public void paintComponent( Graphics g ){
	    g.setColor( Color.BLACK );
	    g.fillRect(0,0,getWidth(),getHeight());
	}
    }

    private final static class JComboBoxListener implements ItemListener {
	
	@Override
	public void itemStateChanged( ItemEvent e ){
	    //HashMap< String, MyMVC > pane_map = (HashMap< String, MyMVC >) e.getSource();
	    JComboBox box = (JComboBox) e.getSource();
	    final int selected_index = box.getSelectedIndex();
	    //TODO
	}

    }

}
