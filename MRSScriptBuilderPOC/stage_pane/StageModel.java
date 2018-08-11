package stage_pane;

import java.util.*;

//more of a struct than a class
public class StageModel {
    //TODO double check defaults
    public int num_runs_per_input_struct = 1;
    public int total_num_results_to_keep = 100;
    public int result_cutoff = 1;
    public int max_num_results_to_keep_per_instance = 0;
    public int max_num_results_to_keep_per_input_struct = 0;
    public boolean merge_results_after_this_stage = false;

    public ArrayList< MoverOrFilter > protocol;
}
