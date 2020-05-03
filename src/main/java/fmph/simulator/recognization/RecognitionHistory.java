package fmph.simulator.recognization;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.models.CarState;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

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

}
