package fmph.simulator.app.context;

import fmph.simulator.Map;
import fmph.simulator.Running.RunManagement;
import fmph.simulator.app.context.interfaces.Context;
import fmph.simulator.com.Server;
import fmph.simulator.car.CarManagement;
import fmph.simulator.visualization.Visualize;
import fmph.simulator.visualization.view.*;
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
    public CarManagement getCarManagement() {
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

    @Override
    public ConnectionInfoController getConnectionInfoController() {
        return null;
    }

    @Override
    public void setConnectionInfoController(ConnectionInfoController connectionInfoController) {

    }

    @Override
    public MenuViewController getMenuViewController() {
        return null;
    }

    @Override
    public void setMenuViewController(MenuViewController menuViewController) {

    }

    @Override
    public RunManagement getRunManagement() {
        return null;
    }

    @Override
    public void setRunManagement(RunManagement runManagement) {

    }

    @Override
    public RunAppController getRunAppController() {
        return null;
    }

    @Override
    public void setRunAppController(RunAppController runAppController) {

    }

    @Override
    public TimeController getTimeController() {
        return null;
    }

    @Override
    public void setTimeController(TimeController timeController) {

    }

    @Override
    public RunHistoryController getRunHistoryController() {
        return null;
    }

    @Override
    public void setRunHistoryController(RunHistoryController runHistoryController) {

    }

    @Override
    public NoiseController getNoiseController() {
        return null;
    }

    @Override
    public void setNoiseController(NoiseController noiseController) {

    }

    @Override
    public ModelMenuController getModelMenuController() {
        return null;
    }

    @Override
    public void setModelMenuController(ModelMenuController modelMenuController) {

    }
}
