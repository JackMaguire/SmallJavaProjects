package main;

import java.awt.*;
import javax.swing.*;

import devel_panes.*;

public class Main {

    private final static DevelPaneMVC  devel_pane1_mvc_ = new DevelPaneMVC( "DevelPane" ); 
    private final static DevelPane2MVC devel_pane2_mvc_ = new DevelPane2MVC(); 
    private final static DevelPane3MVC devel_pane3_mvc_ = new DevelPane3MVC(); 

    private final static MyMVC[] mvc_options = { devel_pane1_mvc_, devel_pane2_mvc_, devel_pane3_mvc_ };

    public static void main( String[] args ){
	JFrame F = new JFrame( "MultistageRosettaScripts" );
	F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	F.setExtendedState( JFrame.MAXIMIZED_BOTH );
	//F.setUndecorated( true );
	F.add( new MainView() );
	F.setVisible( true );
    }

    private final static class MainView extends JPanel {

	public MainView(){
	    setLayout( new GridLayout( 1, 2 ) );
	    add( new PanelWrapper( devel_pane1_mvc_.getNewView(), mvc_options ) );
	    add( new PanelWrapper( devel_pane2_mvc_.getNewView(), mvc_options ) );
	}

    }

}
