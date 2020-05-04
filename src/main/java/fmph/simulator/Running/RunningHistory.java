package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;

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
        ContextBuilder.getContext().getRunHistoryController().addElement(run.getCreateTime());
    }

    public OneRun getRun(double createdTime){
        return runHistory.stream().filter(e -> e.getCreateTime() == createdTime).findFirst().get();
    }

    public OneRun getLast(){
        if(runHistory.size() == 0){
            return null;
        }
        return runHistory.get(runHistory.size()-1);
    }

    public OneRun findElement(Double elementCreatetTime) {
        return runHistory.stream().filter(e -> e.getCreateTime()==elementCreatetTime).findFirst().get();
    }
}
