package devel_panes;

import java.awt.*;
import javax.swing.*;

import main.*;

public class DevelPane3MVC implements MyMVC {

    private final static DevelPaneModel model_ = new DevelPaneModel();

    public DevelPaneMVC(){
    }

    @Override
    public String getTitle() {
	return "DevelPane3";
    }

    @Override
    public JPanel getNewView() {
	final DevelPaneView view = new DevelPaneView( model_ );
	view.setController( new DevelPaneController( model_, view ) );
	return view;
    }

}
