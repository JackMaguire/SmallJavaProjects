package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelWrapper extends JPanel {

    private JPanel current_center_panel_;

    public PanelWrapper( HashMap< String, MyMVC > panes, String key ){
	setLayout( new BorderLayout() );
	current_center_panel_ = panes.get( key ).getNewView();
	add( current_center_panel_, BorderLayout.CENTER );

	final SouthBar south_bar = new SouthBar( panes, key );
	add( south_bar, BorderLayout.SOUTH );
    }

    protected void updateCenterPanel( JPanel new_panel ){
	//TODO find best way to remove current panel
	remove( current_center_panel_ );
	current_center_panel_ = new_panel;
	add( new_panel, BorderLayout.CENTER );
	repaint();
	revalidate();
    }

    private final class SouthBar extends JPanel {
	final private HashMap< String, MyMVC > panes_;
	private String[] names_;

	public SouthBar( HashMap< String, MyMVC > panes, String starting_key ){
	    panes_ = panes;

	    final JComboBox< String > dropdown_menu = new JComboBox< String >();

	    names_ = new String[ panes_.size() ];
	    int count = 0;

	    Iterator< ? > it = panes_.entrySet().iterator();
	    while ( it.hasNext() ){
		Map.Entry pair = (Map.Entry) it.next();
		names_[ count++ ] = ""+pair.getKey();
	    }

	    int starting_pos = 0;

	    Arrays.sort( names_ );
	    for( int i=0; i<names_.length; ++i ){
		String s = names_[ i ];
		if( s.equals( starting_key ) ){
		    starting_pos = i;
		}
		dropdown_menu.addItem( s );
	    }

	    add( dropdown_menu );
	    dropdown_menu.setSelectedIndex( starting_pos );
	    dropdown_menu.addItemListener( new JComboBoxListener( this ) );
	}

	public void paintComponent( Graphics g ){
	    g.setColor( Color.BLACK );
	    g.fillRect(0,0,getWidth(),getHeight());
	}

	public void updatePane( int index ){
	    final JPanel new_center_panel = panes_.get( names_[ index ] ).getNewView();
	    updateCenterPanel( new_center_panel );
	}
    }

    private final static class JComboBoxListener implements ItemListener {
	
	private final SouthBar owner_;

	public JComboBoxListener( SouthBar caller ){
	    owner_ = caller;
	}

	@Override
	public void itemStateChanged( ItemEvent e ){
	    JComboBox box = (JComboBox) e.getSource();
	    final int selected_index = box.getSelectedIndex();
	    owner_.updatePane( selected_index );
	}

    }

}
