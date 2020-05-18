package fmph.simulator.visualization.view;

import fmph.simulator.Running.RunSaver;
import fmph.simulator.app.MainApp;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.visualization.config.ConfigController;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
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
    MenuItem openHistory;

    @FXML
    MenuItem saveHistory;

    @FXML
    MenuItem newApp;

    @FXML
    MenuItem close;

    @FXML
    MenuItem configViewAll;


    @FXML
    public void initialize(){
        Stage primaryStage = ContextBuilder.getContext().getPrimaryStage();
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            ContextBuilder.getContext().getCanvasController().resize();

        });
        ContextBuilder.getContext().getCanvasController().resize();

        saveHistory.setOnAction(e -> RunSaver.save());
        openHistory.setOnAction(e -> RunSaver.load());
        close.setOnAction(e -> MainApp.close(0));
        newApp.setOnAction(e -> MainApp.reset());

        configViewAll.setOnAction(e -> ConfigController.createPopUp());
        new Message("App UI is full load", MessageType.INFO);
    }


    public void aboutPopup(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATS");
        alert.setHeaderText("Automatic Transport System");
        WebView webView = new WebView();
        webView.getEngine().loadContent(context.config.getString("ats.about"));
        webView.setPrefSize(450, 200);
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }


    public void setLeftStatusText(String msg){
        leftStatus.setText(msg);
    }

    public void setRightStatusText(String msg){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                rightStatus.setText(msg);
            }
        });

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
