package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Contextable {

    public MainController(){
        registryToContext();
    }


    @FXML
    Label leftStatus;

    @FXML
    Label rightStatus;

    @FXML
    ToggleButton consoleHide;

    @FXML
    Button consoleClear;

    @FXML
    Button consoleDownload;

    @FXML
    ToggleButton consoleFull;


    @FXML
    VBox consoleWrapper;


    @FXML
    public void initialize(){
        Stage primaryStage = ContextBuilder.getContext().getPrimaryStage();
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            ContextBuilder.getContext().getCanvasController().resize();

        });
        ContextBuilder.getContext().getCanvasController().resize();
    }

    @FXML
    public void aboutPopup(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATS");
        alert.setHeaderText("Automatic Transport System");
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
    }


    public void setLeftStatusText(String msg){
        leftStatus.setText(msg);
    }

    public void setRightStatusText(String msg){
        rightStatus.setText(msg);
    }


    @Override
    public void registryToContext() {
        context.setMainController(this);
    }

    public ToggleButton getConsoleHide() {
        return consoleHide;
    }

    public Button getConsoleClear() {
        return consoleClear;
    }

    public Button getConsoleDownload() {
        return consoleDownload;
    }

    public ToggleButton getConsoleFull() {
        return consoleFull;
    }
    public VBox getConsoleWrapper() {
        return consoleWrapper;
    }
}
