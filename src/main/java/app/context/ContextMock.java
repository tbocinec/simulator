package app.context;

import app.context.interfaces.Context;
import com.Server;
import fmph.simulator.Map;
import fmph.simulator.models.CarModel;
import fmph.simulator.recognization.RecognitionHistory;
import fmph.simulator.vizualization.Visualize;
import fmph.simulator.vizualization.view.*;
import javafx.stage.Stage;

public class ContextMock implements Context {


    @Override
    public CanvasController getCanvasController() {
        return null;
    }

    @Override
    public void setCanvasController(CanvasController canvasController) {

    }

    @Override
    public ConsoleController getConsoleController() {
        return null;
    }

    @Override
    public void setConsoleController(ConsoleController consoleController) {

    }

    @Override
    public RecognitionHistory getRecognizationHistory() {
        return null;
    }

    @Override
    public void setRecognizationHistory(RecognitionHistory recognizationHistory) {

    }

    @Override
    public Map getMap() {
        return null;
    }

    @Override
    public void setPrimaryStage(Stage primaryStage) {

    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public Visualize getVisualize() {
        return null;
    }

    @Override
    public CarModel getCarModel() {
        return null;
    }

    @Override
    public CarInfoController getCarInfoController() {
        return null;
    }

    @Override
    public void setCarInfoController(CarInfoController carInfoController) {

    }

    @Override
    public Stage getPrimaryStage() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setRecognitionHistoryController(RecognitionHistoryController controller) {

    }

    @Override
    public RecognitionHistoryController getRecognitionHistoryController() {
        return null;
    }

    @Override
    public void setMenuMapController(MenuMapController menuMapController) {

    }

    @Override
    public void setAppPanelController(AppPanelController appPanelController) {

    }
}
