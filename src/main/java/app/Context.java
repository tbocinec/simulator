package app;

import com.Server;
import fmph.simulator.CarModel;
import fmph.simulator.Config;
import fmph.simulator.Map;
import fmph.simulator.map.LaserTag;
import fmph.simulator.vizualization.Visualize;
import fmph.simulator.vizualization.console.ConsolePanel;
import fmph.simulator.vizualization.controlPanel.ControlPanel;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class Context {
    private static Context context;

    //Component
    Visualize visualize;
    Server server;
    CarModel carModel;

    Stage primaryStage;
    ControlPanel controlPanel;
    ConsolePanel consolePanel;
    PropertiesConfiguration config = Config.getConfig();

    LaserTag lastSeenlaserTag;


    public LaserTag getLastSeenlaserTag() {
        return lastSeenlaserTag;
    }

    public void setLastSeenlaserTag(LaserTag lastSeenlaserTag) {
        this.lastSeenlaserTag = lastSeenlaserTag;
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

    Map map;

    private Context() {

    }

    public static Context getContext() {
        if (context == null) {
            context = new Context();
        }
        return context;
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

    public CarModel getCarModel() {
        if(carModel == null){
            carModel = new CarModel();
        }
        return carModel;
    }
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public ControlPanel getControlPanel() {
        if(controlPanel == null){
            controlPanel = new ControlPanel();
        }
        return  controlPanel;
    }

    public ConsolePanel getConsolePanel() {
        if(consolePanel == null){
            consolePanel = new ConsolePanel();
        }
        return  consolePanel;
    }
}
