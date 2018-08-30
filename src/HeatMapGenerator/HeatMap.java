package HeatMapGenerator;

public class HeatMap {

	private final double[][] heat_;
	private final double[] x_vals_;
	private final double[] y_vals_;
	//private final int min

	private final double r_;
	
	private double max_heat_ = 0;
	
	public HeatMap( double min_x, double max_x, double dx, double min_y, double max_y, double dy, double r ) {
		r_ = r;
		
		final int num_x = 1 + (int)( (max_x - min_x)/(dx) );
		final int num_y = 1 + (int)( (max_y - min_y)/(dy) );
		heat_ = new double[ num_x ][ num_y ];
		
		x_vals_ = new double[ num_x ];
		for( int i=0; i < num_x; ++i ) {
			x_vals_[ i ] = min_x + (i*dx);
		}
		
		y_vals_ = new double[ num_y ];
		for( int i=0; i < num_y; ++i ) {
			y_vals_[ i ] = min_y + (i*dy);
		}
	}
	
}
