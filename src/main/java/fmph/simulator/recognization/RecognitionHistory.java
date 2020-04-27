package fmph.simulator.recognization;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecognitionHistory {

    private ArrayList<HistoryElement> history = new ArrayList<>();
    SimpleDateFormat formatDate = new SimpleDateFormat("hh:s");

;
    public void addTag(LaserTag laserTag, Segment segment, BigDecimal time) {
        HistoryElement historyElement = new HistoryElement(laserTag,segment,time);
        history.add(historyElement);
        Platform.runLater(
                () -> {
                    ContextBuilder.getContext().getRecognitionHistoryController().addElement(historyElement.toString());
                }
        );

    }

    class HistoryElement{

        Segment segment;
        LaserTag laserTag;
        BigDecimal time;

        public HistoryElement(LaserTag laserTag, Segment segment, BigDecimal time) {
            this.laserTag = laserTag;
            this.time = time;
            this.segment = segment;
        }

        @Override
        public String toString() {
            String dateString = formatDate.format(new Date(time.longValue()));
            return String.format("Tag [%s] in segment [%s] at time %s",laserTag.getType(),segment.getSegmentId(),dateString);
        }
    }


}
