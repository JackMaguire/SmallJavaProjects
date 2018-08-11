package stage_panes;

import java.awt.*;
import javax.swing.*;

import main.*;

public class StagePaneMVC implements MyMVC {

    private final static StagePaneModel model_ = new StagePaneModel();

    public StagePaneMVC(){
    }

    @Override
    public String getTitle() {
	return "Stages";
    }

    @Override
    public JPanel getNewView() {
	final StagePaneView view = new StagePaneView( model_ );
	final StagePaneController controller = new StagePaneController( model_, view );
	
	view.setController( controller );
	return view;
    }

}
