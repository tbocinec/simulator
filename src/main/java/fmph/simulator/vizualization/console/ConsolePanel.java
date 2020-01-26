package fmph.simulator.vizualization.console;


import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;



public class ConsolePanel extends ScrollPane {

    TextFlow console;
    Button cleanButton;
    VBox  box;

    public ConsolePanel(){
        box = new VBox();
        this.setContent(box);
        setMaxHeight(80);
        setHeight(80);
        console = new TextFlow();
        this.getChildren().add(console);

        cleanButton = new Button("Clean console");
        box.getChildren().add(cleanButton);
    }

    public void addMsg(Message msg){
        switch (msg.type)
        {
            case ERROR:
                addError(msg.toString());
                break;
            case WARNING:
                addWarning(msg.toString());
                break;
            case INFO:
                addMsg(msg.toString());
                break;
        }

    }


    public void addMsg(String msg){
        final Text text = new Text(msg);
        text.setFont(new Font(10));
        text.setFill(Color.MEDIUMVIOLETRED);
        addText(text);

    }

    public void addWarning(String msg){
        Text text = new Text(msg);
        text.setFont(new Font(12));
        text.setFill(Color.YELLOW);
        addText(text);
    }

    public void addError(String msg){
        Text text = new Text(msg);
        text.setFont(new Font(12));
        text.setFill(Color.RED);
        addText(text);

    }

    public void addText(Text text){
      //  Region p = new Region();
       //S p.setPrefSize(Double.MAX_VALUE, 0.0);
        Platform.runLater( () -> box.getChildren().addAll(text));
        Platform.runLater( () -> this.setVvalue(40));
    }

}
