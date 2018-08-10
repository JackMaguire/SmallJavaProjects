package devel_panes;

import java.awt.*;
import javax.swing.*;

import main.*;

public class DevelPaneMVC implements MyMVC {

    private final static DevelPaneModel model_ = new DevelPaneModel();
    private String title_;

    public DevelPaneMVC( String title ){
	title_ = title;
    }

    @Override
    public String getTitle() {
	return title_;
    }

    @Override
    public JPanel getNewView() {
	final DevelPaneView view = new DevelPaneView( model_ );
	final DevelPaneController controller = new DevelPaneController( model_, view );
	
	view.setController( controller );
	return view;
    }

}
