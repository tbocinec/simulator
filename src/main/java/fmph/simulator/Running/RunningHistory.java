package fmph.simulator.Running;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


    @JsonIgnore
    public OneRun getLast(){
        if(runHistory.size() == 0){
            return null;
        }
        return runHistory.get(runHistory.size()-1);
    }

    public OneRun findElement(Double elementCreatetTime) {
        return runHistory.stream().filter(e -> e.getCreateTime()==elementCreatetTime).findFirst().get();
    }

    public void showAlltoGui() {
        runHistory.stream().forEach(e -> {
            ContextBuilder.getContext().getRunHistoryController().addElement(e.getCreateTime());
        });
    }

    public ArrayList<OneRun> getRunHistory() {
        return runHistory;
    }

    public void setRunHistory(ArrayList<OneRun> runHistory) {
        this.runHistory = runHistory;
    }
}
