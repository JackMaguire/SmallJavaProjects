package stage_panes;

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
	final int width = getWidth();
	final int height = getHeight();
	final int num_stages = model_.numStages();
	final int x_unit = width / ( 1 + 2 * num_stages );
	final int max_height = height - 50;

	final int[][] counts_per_stage = new int[ num_stages ][ 2 ];//first element holds number of jobs, second holds number of jobs to keep

	final StageModel first_stage_model = model_.getStage( 0 );
	counts_per_stage[ 0 ][ 0 ] = num_input_jobs_ * first_stage_model.num_runs_per_input_struct;
	counts_per_stage[ 0 ][ 1 ] = first_stage_model.total_num_results_to_keep;
	if( counts_per_stage[ 0 ][ 1 ] > counts_per_stage[ 0 ][ 0 ] ){
	    counts_per_stage[ 0 ][ 1 ] = counts_per_stage[ 0 ][ 0 ];
	}

	for( int i=1; i<num_stages; ++i ){
	    final StageModel stage_model = model_.getStage( i );
	    counts_per_stage[ i ][ 0 ] = counts_per_stage[ i - 1 ][ 1 ] * stage_model.num_runs_per_input_struct;
	    counts_per_stage[ i ][ 1 ] = stage_model.total_num_results_to_keep;
	    if( counts_per_stage[ i ][ 1 ] > counts_per_stage[ i ][ 0 ] ){
		counts_per_stage[ i ][ 1 ] = counts_per_stage[ i ][ 0 ];
	    }
	}
    }

}
