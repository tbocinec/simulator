package fmph.simulator.recognization;

import fmph.simulator.app.context.ContextBuilder;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RecognitionHistory {

    private LinkedList<HistoryElement> history = new LinkedList<HistoryElement>();

    public void addTag(HistoryElement historyElement ){
        history.add(historyElement);

        Platform.runLater(
                () -> {
                    ContextBuilder.getContext().getRecognitionHistoryController().addElement(historyElement.toString());
                }
        );

    }

    public HistoryElement getNearst(double time){
        if(history == null || history.size() == 0){
            return null;
        }
        ListIterator<HistoryElement> it = history.listIterator(0);
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

    public void removeNews(double runTimeSecond) {
        history =  history.stream().filter(e -> e.getTimeRecognization() > runTimeSecond).collect(Collectors.toCollection(LinkedList::new));
    }
}
