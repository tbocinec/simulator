package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.vizualization.console.Message;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleController extends ScrollPane implements Contextable {

    TextFlow console;
    VBox box;
    SimpleDateFormat formatter;

    public ConsoleController(){
        registryToContext();
        prepareComponent();
    }

    private void prepareComponent() {
        box = new VBox();
        this.setContent(box);
       // setMaxHeight(250);
       // setHeight(250);
        formatter= new SimpleDateFormat("HH:mm:ss ");
        console = new TextFlow();
        this.getChildren().add(console);

        //todo
        prepareListener();
    }

    private void prepareListener() {
        ContextBuilder.getContext().getMainController().getConsoleClear().setOnMouseClicked(event -> this.removeAllText());
        ContextBuilder.getContext().getMainController().getConsoleDownload().setOnMouseClicked(event -> this.downloadText());
        ContextBuilder.getContext().getMainController().getConsoleFull().setOnMouseClicked(event -> this.fullScrean(event));
        ContextBuilder.getContext().getMainController().getConsoleHide().setOnMouseClicked(event -> this.hide(event));
    }

    private void hide(MouseEvent event) {

        if(  ContextBuilder.getContext().getMainController().getConsoleHide().isSelected()){
          this.setMinHeight(1);
            context.getMainController().getConsoleWrapper().setMinHeight(30);
        }
        else{
            context.getMainController().getConsoleWrapper().setMinHeight(180);
            this.setMinHeight(150);
        }
    }

    private void fullScrean(MouseEvent event) {
        if( ! ContextBuilder.getContext().getMainController().getConsoleFull().isSelected()){
            context.getMainController().getConsoleWrapper().setMinHeight(180);
        }
        else{
            context.getMainController().getConsoleWrapper().setMinHeight(context.getPrimaryStage().getHeight()-20);
        }

    }

    private void downloadText() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Console log (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(ContextBuilder.getContext().getPrimaryStage());

        if (file != null) {
            saveTextToFile(Message.messageHistory.toString(), file);
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException("Problem with save console export");
        }
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
        text.setFont(new Font(14));
        text.setFill(Color.MEDIUMVIOLETRED);
        addText(text);

    }

    public void addWarning(String msg){
        Text text = new Text(msg);
        text.setFont(new Font(16));
        text.setFill(Color.YELLOW);
        addText(text);
    }

    public void addError(String msg){
        Text text = new Text(msg);
        text.setFont(new Font(16));
        text.setFill(Color.RED);
        addText(text);

    }

    public void addText(Text text){
        //  Region p = new Region();
        //S p.setPrefSize(Double.MAX_VALUE, 0.0);

        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        text.setText(time+" "+text.getText());
        Platform.runLater( () -> box.getChildren().addAll(text));
        Platform.runLater( () -> this.setVvalue(40));
    }

    public void removeAllText(){
        box.getChildren().remove(0,box.getChildren().size());
    }


    @Override
    public void registryToContext() {
        context.setConsoleController(this);
    }
}
