package HeatMapGenerator;

import java.util.ArrayList;

public class FPHeatMapGeneratorThread extends Thread {

	public final ArrayList< Integer > indices_to_compute = new ArrayList< Integer >();
	private final FPHeatMap hm_;
	
	public FPHeatMapGeneratorThread( FPHeatMap hm ) {
		super();
		hm_ = hm;
	}
	
	public void run() {
		for( int i : indices_to_compute ) {
			double x = FPHeatMapGenerator.min + ( (double) i * ( FPHeatMapGenerator.max - FPHeatMapGenerator.min ) ) / FPHeatMapGenerator.res;
			for( int j = 0; j < FPHeatMapGenerator.res; ++j ) {
				double y = FPHeatMapGenerator.min + ( (double) j * ( FPHeatMapGenerator.max - FPHeatMapGenerator.min ) ) / FPHeatMapGenerator.res;
				double count1 = hm_.numWithinR_data1( x, y, FPHeatMapGenerator.radius );
				double count2 = hm_.numWithinR_data2( x, y, FPHeatMapGenerator.radius );
				FPHeatMapGenerator.counts_for_set1[ i ][ j ] = count1;
				FPHeatMapGenerator.counts_for_set2[ i ][ j ] = count2;
			}
		}
	}
	
}
