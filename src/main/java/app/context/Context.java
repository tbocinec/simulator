package app.context;

import app.context.exceptions.ContextException;
import com.Server;
import fmph.simulator.models.CarModel;
import fmph.simulator.Config;
import fmph.simulator.Map;
import fmph.simulator.recognization.RecognitionHistory;
import fmph.simulator.vizualization.Visualize;
import fmph.simulator.vizualization.view.*;
import javafx.stage.Stage;
import org.apache.commons.configuration2.PropertiesConfiguration;

//Singleton
public class Context implements app.context.interfaces.Context {
    private static Context context;

    //Component
    Visualize visualize;
    Server server;
    CarModel carModel;
    RecognitionHistory recognitionHistory;

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

    @Override
    public RecognitionHistory getRecognizationHistory() {
        if(this.recognitionHistory == null){
            recognitionHistory = new RecognitionHistory();
        }
        return this.recognitionHistory;
    }

    @Override
    public void setRecognizationHistory(RecognitionHistory recognitionHistory) {
        this.recognitionHistory = recognitionHistory;

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





    //Geter and Setter
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

    @Override
    public CarModel getCarModel() {
        if(carModel == null){
            carModel = new CarModel();
        }
        return carModel;
    }
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
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



}