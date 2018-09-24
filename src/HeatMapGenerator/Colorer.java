package HeatMapGenerator;

import java.awt.Color;

public interface Colorer {
	public Color colorForVal( double val );
	public Color colorForVal( double val1, double val2 );
	public Color colorForLine();
}
