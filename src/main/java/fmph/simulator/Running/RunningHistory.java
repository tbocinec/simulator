package fmph.simulator.Running;

import java.util.ArrayList;

public class RunningHistory {

    ArrayList<OneRun> runHistory;

    public RunningHistory(ArrayList<OneRun> runHistory) {
        this.runHistory = runHistory;
    }

    public RunningHistory() {
        this.runHistory = new ArrayList<>();
    }

    public void addRun(OneRun run){
        runHistory.add(run);
    }

    public OneRun getRun(double createdTime){
        return runHistory.stream().filter(e -> e.getCreateTime() == createdTime).findFirst().get();
    }

    public OneRun getLast(){
        return runHistory.get(runHistory.size());
    }
}
