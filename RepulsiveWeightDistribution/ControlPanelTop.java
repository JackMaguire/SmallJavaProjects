import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ControlPanelTop extends JPanel implements ActionListener {

    private final View view_;
    private final ControlPanel control_panel_;

    private final JButton set1_ = new JButton( "1" );
    private final JButton set2_ = new JButton( "2" );
    private final JButton set3_ = new JButton( "3" );

    private final JLabel buffer_ = new JLabel( " " );
    private final JButton decrease_font_size_ = new JButton( "-" );
    private final JButton increase_font_size_ = new JButton( "+" );

    public ControlPanelTop( View view, ControlPanel cp ){
	view_ = view;
	control_panel_ = cp;

	setLayout( new GridLayout( 1, 6 ) );
	add( set1_ );
	add( set2_ );
	add( set3_ );

	add( buffer_ );
	add( decrease_font_size_ );
	add( increase_font_size_ );

	set1_.addActionListener( this );
	set2_.addActionListener( this );
	set3_.addActionListener( this );
	decrease_font_size_.addActionListener( this );
	increase_font_size_.addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
	
	if( e.getSource() == set1_ ){
	    control_panel_.setValues( 0.15, 0 );
	}
	else if( e.getSource() == set2_ ){
	    control_panel_.setValues( 0, 0.06 );
	}
	else if( e.getSource() == set3_ ){
	    control_panel_.setValues( 0.1, 0.04 );
	}
	else if( e.getSource() == decrease_font_size_ ){
	    view_.decreaseFontSize();
	}
	else if( e.getSource() == increase_font_size_ ){
	    view_.increaseFontSize();
	}
    }

}
