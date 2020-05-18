package fmph.simulator.visualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RunHistoryController extends VBox implements Contextable {

    private Button clearButton;
    DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");

    public RunHistoryController(){
        registryToContext();
        init();
    }

    private void  init(){
        clearButton = new Button("Clear");
        clearButton.setOnMouseClicked(e-> removeAll());
        this.getChildren().add(clearButton);
    }

    public void removeAll(){
        this.getChildren().remove(1,this.getChildren().size());
    }


    @Override
    public void registryToContext() {
        context.setRunHistoryController(this);
    }

    public void addElement(Double elementCreatetTime) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(elementCreatetTime.longValue());
        Text element = new Text("Runt start at  "+df.format(cal.getTime()));

        element.setOnMouseClicked(e ->{
            context.getRunManagement().getHistorytRun(elementCreatetTime);
        });

        this.getChildren().add(element);
    }


}
