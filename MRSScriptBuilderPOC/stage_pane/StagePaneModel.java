package stage_pane;

import java.util.*;

public class StagePaneModel {

    //private int num_stages_ = 1;
    private ArrayList< StageModel > total_num_jobs_to_keep_per_stage_;

    public StagePaneModel(){

    }

    public void addBlankStageAtEnd(){
	//TODO
    }

    public void addBlankStageAtPosition(){
	//TODO
    }

    public int numStages(){
	return total_num_jobs_to_keep_per_stage_.size();
    }

    public StageModel getStage( int zero_indexed_stage_index ){
	return total_num_jobs_to_keep_per_stage_.get( zero_indexed_stage_index );
    }
}
