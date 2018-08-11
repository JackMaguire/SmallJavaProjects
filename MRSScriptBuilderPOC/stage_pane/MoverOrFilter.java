package stage_pane;

public class MoverOrFilter {
    public String name;
    public boolean is_mover = true;

    public MoverOrFilter( String name, boolean is_mover ){
	this.name = name;
	this.is_mover = is_mover;
    }
}
