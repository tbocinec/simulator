package fmph.simulator.car.recognization;

import fmph.simulator.app.context.ContextBuilder;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RecognitionHistory implements Serializable {

    private  LinkedList<HistoryElement> history = new LinkedList<HistoryElement>();

    public synchronized void addTag(HistoryElement historyElement ){
        getHistory().add(historyElement);
        addTagToGui(historyElement);


    }

    private synchronized void addTagToGui(HistoryElement historyElement){
        if(1==1){return;}
        Platform.runLater(
                () -> {
                    ContextBuilder.getContext().getRecognitionHistoryController().addElement(getHistory().toString());
                }
        );
    }

    public synchronized HistoryElement  getNearst(double time){
        if(history == null || history.size() == 0){
            return null;
        }
        ListIterator<HistoryElement> it = getHistory().listIterator(0);
        HistoryElement nearst = null;
        while (it.hasNext()){
            HistoryElement he =  it.next();
            if(he.getTimeRecognization() > time ){  //todo change to receive time
                break;
            }
            nearst =he;
        }
        return nearst;
    }
    public synchronized void showAll() {
        Platform.runLater( () ->  ContextBuilder.getContext().getRecognitionHistoryController().removeAll());
        this.getHistory().stream().forEach(e -> addTagToGui(e));
    }

    public synchronized void removeNews(double runTimeSecond) {
        setHistory(getHistory().stream().filter(e -> e.getTimeRecognization() > runTimeSecond).collect(Collectors.toCollection(LinkedList::new)));
    }

    public synchronized LinkedList<HistoryElement> getHistory() {
        return history;
    }

    public synchronized void  setHistory(LinkedList<HistoryElement> history) {
        this.history = history;
    }


}
