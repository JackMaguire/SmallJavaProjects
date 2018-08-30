package HeatMapGenerator;

import java.awt.Color;

public class BWColorer implements Colorer {

	private final boolean white_is_one_;
	
	public BWColorer(  ) {
		white_is_one_ = false;
	}
	
	public BWColorer( boolean white_is_one ) {
		white_is_one_ = white_is_one;
	}
	
	@Override
	public Color colorForVal( double val ) {
		int a = (int)(255 * val);
		if( !white_is_one_ ) {
			a = 255 - a;
		}
		return new Color(a,a,a);
	}

}
