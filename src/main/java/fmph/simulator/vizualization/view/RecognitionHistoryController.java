package fmph.simulator.vizualization.view;

import app.context.interfaces.Contextable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecognitionHistoryController extends VBox implements Contextable {

    private Button clearButton;

    public RecognitionHistoryController(){
        registryToContext();
        init();
    }

    private void  init(){
        clearButton = new Button("Clear");
        clearButton.setOnMouseClicked( e -> this.getChildren().remove(1,this.getChildren().size()));
        this.getChildren().add(clearButton);
    }



    @Override
    public void registryToContext() {
        context.setRecognitionHistoryController(this);
    }

    public void addElement(String elementText) {
        this.getChildren().add(new Text(elementText));
    }
}
