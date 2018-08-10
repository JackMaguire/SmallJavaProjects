package devel_panes;

public class DevelPaneModel {

    private int value_ = 150;

    public DevelPaneModel(){}

    public void setValue( int setting ){
	value_ = setting;
    }

    public int value(){
	return value_;
    }

    public int getValue(){
	return value_;
    }

}
