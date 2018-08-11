package stage_pane;

import java.awt.*;
import javax.swing.*;

public class ThroughputDiagramView extends JPanel {

    private final StagePaneModel model_;
    private final int num_input_jobs_;

    public ThroughputDiagramView( StagePaneModel model, int num_input_jobs ){
	model_ = model;
	num_input_jobs_ = num_input_jobs;

	setLayout( new BorderLayout() );
    }

    public void paint( Graphics g ){
	g.setColor( Color.BLACK );

	final int width = getWidth();
	final int height = getHeight();
	final int num_stages = model_.numStages();
	final int x_unit = width / ( 2 + 2 * num_stages );
	final int max_height = height - 50;

	final int[][] counts_per_stage = new int[ num_stages ][ 2 ];//first element holds number of jobs, second holds number of jobs to keep

	int max = 0;

	final StageModel first_stage_model = model_.getStage( 0 );
	counts_per_stage[ 0 ][ 0 ] = num_input_jobs_ * first_stage_model.num_runs_per_input_struct;
	counts_per_stage[ 0 ][ 1 ] = first_stage_model.total_num_results_to_keep;
	if( counts_per_stage[ 0 ][ 1 ] > counts_per_stage[ 0 ][ 0 ] ){
	    counts_per_stage[ 0 ][ 1 ] = counts_per_stage[ 0 ][ 0 ];
	    max = counts_per_stage[ 0 ][ 1 ];
	} else {
	    max = counts_per_stage[ 0 ][ 0 ];
	}

	for( int i=1; i<num_stages; ++i ){
	    final StageModel stage_model = model_.getStage( i );
	    counts_per_stage[ i ][ 0 ] = counts_per_stage[ i - 1 ][ 1 ] * stage_model.num_runs_per_input_struct;
	    counts_per_stage[ i ][ 1 ] = stage_model.total_num_results_to_keep;
	    if( counts_per_stage[ i ][ 1 ] > counts_per_stage[ i ][ 0 ] ){
		counts_per_stage[ i ][ 1 ] = counts_per_stage[ i ][ 0 ];
	    }

	    if( counts_per_stage[ i ][ 0 ] > max ){
		max = counts_per_stage[ i ][ 0 ];
	    }
	    if( counts_per_stage[ i ][ 1 ] > max ){
		max = counts_per_stage[ i ][ 0 ];
	    }
	}

	int[][][] x_y_positions = new int[ num_stages ][ 2 ][ 2 ];

	for( int i=0; i<num_stages; ++i ){
	    //X
	    x_y_positions[ i ][ 0 ][ 0 ] = x_unit * ( 1 + 2*i );
	    x_y_positions[ i ][ 1 ][ 0 ] = x_unit * ( 2 + 2*i );

	    //Y
	    x_y_positions[ i ][ 0 ][ 1 ] =
		y_for_value( counts_per_stage[ i ][ 0 ], max, max_height );
	    x_y_positions[ i ][ 1 ][ 1 ] =
		y_for_value( counts_per_stage[ i ][ 1 ], max, max_height );
	}
    }

    private int y_for_value( int value, int max, int height ){
	final double ratio = ((double) value) / ((double) max);
	return (int) ( ratio * height );
    }

}
