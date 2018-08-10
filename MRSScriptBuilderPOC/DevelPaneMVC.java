import java.awt.*;
import javax.swing.*;

public class DevelPaneMVC implements MyMVC {

    private static DevelPaneModel model_;

    public JPanel getNewView(){
	DevelPaneView view = new DevelPaneView( model_ );
	view.setController( new DevelPaneController( model_, view ) );
    }

}
