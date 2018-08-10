package devel_panes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DevelPaneController implements ChangeListener {

    final private DevelPaneModel model_;
    final private DevelPaneView view_;

    public DevelPaneController( DevelPaneModel model, DevelPaneView view ){
	model_ = model;
	view_ = view;
    }

    @Override
    public void stateChanged( ChangeEvent e ){
	final JSlider slider = (JSlider) e.getSource();
	final int val = slider.getValue();
	model_.setValue( val );
	view_.repaint();
	//view_.revalidate();
    }


}
