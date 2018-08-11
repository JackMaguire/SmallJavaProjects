package stage_panes;

import java.awt.*;
import javax.swing.*;

public class StagePaneView extends JPanel {

    private final StagePaneModel model_;

    public StagePaneView( StagePaneModel model ){
	model_ = model;

	setLayout( new BorderLayout() );
    }

}
