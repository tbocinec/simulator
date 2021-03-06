package fmph.simulator.app.context;

import fmph.simulator.Config;
import fmph.simulator.Map;
import fmph.simulator.Running.RunManagement;
import fmph.simulator.app.context.exceptions.ContextException;
import fmph.simulator.com.Server;
import fmph.simulator.car.CarManagement;

import fmph.simulator.visualization.Visualize;
import fmph.simulator.visualization.view.*;
import javafx.stage.Stage;
import org.apache.commons.configuration2.PropertiesConfiguration;

//Singleton
public class Context implements fmph.simulator.app.context.interfaces.Context {
    private static Context context;

    //Component
    Visualize visualize;
    Server server;
    CarManagement carManagement;
    //RecognitionHistory recognitionHistory;
    RunManagement runManagement;

    //Config
    PropertiesConfiguration config = Config.getConfig();
    Map map;

    //Controllers
    ConsoleController ConsoleController;
    CanvasController canvasController;
    MainController mainController;
    RecognitionHistoryController recognitionHistoryController;
    Stage primaryStage;
    CarInfoController carInfoController;
    MenuMapController menuMapController;
    AppPanelController appPanelController;
    ConnectionInfoController connectionInfoController;
    MenuViewController menuViewController;
    RunAppController runAppController;
    TimeController timeController;
    RunHistoryController runHistoryController;
    NoiseController noiseController;
    ModelMenuController modelMenuController;

    public CanvasController getCanvasController() {
        if(canvasController == null){
            throw new ContextException(CanvasController.class);
        }
        return canvasController;
    }

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }



    public ConsoleController getConsoleController() {
        if(ConsoleController == null){
            throw new ContextException(ConsoleController.class);
        }
        return ConsoleController;
    }

    public void setConsoleController(ConsoleController consoleController) {
        this.ConsoleController = consoleController;
    }



    public Map getMap() {
        if(map == null){
            map = new Map();
            map.loadMap();
        }
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    public Visualize getVisualize() {
        if (visualize == null) {
            if(primaryStage == null){
                throw  new RuntimeException("Not set primari stage");
            }
            visualize = new Visualize(primaryStage);
        }
        return visualize;
    }

    public void setVisualize(Visualize visualize) {
        this.visualize = visualize;
    }

    public Server getServer() {
        if (server == null) {
            server = new Server(config.getInt("socket.port"));
        }
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public CarManagement getCarManagement() {
        if(carManagement == null){
            carManagement = new CarManagement();
        }
        return carManagement;
    }

    public CarInfoController getCarInfoController() {
        if(carInfoController == null){
            throw new ContextException(ConsoleController.class);
        }
        return carInfoController;
    }

    @Override
    public void setCarInfoController(CarInfoController carInfoController) {
        this.carInfoController = carInfoController;
    }

    @Override
    public RecognitionHistoryController getRecognitionHistoryController() {
        return recognitionHistoryController;
    }

    @Override
    public void setMenuMapController(MenuMapController menuMapController) {
        this.menuMapController = menuMapController;

    }

    @Override
    public void setRecognitionHistoryController(RecognitionHistoryController recognitionHistoryController) {
        this.recognitionHistoryController = recognitionHistoryController;
    }


    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public AppPanelController getAppPanelController() {
        return appPanelController;
    }

    @Override
    public void setAppPanelController(AppPanelController appPanelController) {
        this.appPanelController = appPanelController;
    }

    @Override
    public ConnectionInfoController getConnectionInfoController() {
        return connectionInfoController;
    }

    @Override
    public void setConnectionInfoController(ConnectionInfoController connectionInfoController) {
        this.connectionInfoController = connectionInfoController;
    }
    public MenuViewController getMenuViewController() {
        return menuViewController;
    }

    public void setMenuViewController(MenuViewController menuViewController) {
        this.menuViewController = menuViewController;
    }

    public RunManagement getRunManagement() {
        if(runManagement==null) {
            runManagement = new RunManagement();
        }
        return runManagement;
    }

    public void setRunManagement(RunManagement runManagement) {
        this.runManagement = runManagement;
    }

    public RunAppController getRunAppController() {
        return runAppController;
    }

    public void setRunAppController(RunAppController runAppController) {
        this.runAppController = runAppController;
    }
    public TimeController getTimeController() {
        return timeController;
    }

    public void setTimeController(TimeController timeController) {
        this.timeController = timeController;
    }

    public RunHistoryController getRunHistoryController() {
        return runHistoryController;
    }

    public void setRunHistoryController(RunHistoryController runHistoryController) {
        this.runHistoryController = runHistoryController;
    }

    public NoiseController getNoiseController() {
        return noiseController;
    }

    public void setNoiseController(NoiseController noiseController) {
        this.noiseController = noiseController;
    }

    public ModelMenuController getModelMenuController() {
        return modelMenuController;
    }

    public void setModelMenuController(ModelMenuController modelMenuController) {
        this.modelMenuController = modelMenuController;
    }
}
