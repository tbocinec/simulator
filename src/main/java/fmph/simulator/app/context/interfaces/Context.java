package fmph.simulator.app.context.interfaces;

import fmph.simulator.Config;
import fmph.simulator.Map;
import fmph.simulator.Running.RunManagement;
import fmph.simulator.com.Server;
import fmph.simulator.car.CarManagement;
import fmph.simulator.visualization.Visualize;
import fmph.simulator.visualization.view.*;
import javafx.stage.Stage;
import org.apache.commons.configuration2.PropertiesConfiguration;

public interface Context {

    PropertiesConfiguration config = Config.getConfig();

    CanvasController getCanvasController();
    void setCanvasController(CanvasController canvasController);

    ConsoleController getConsoleController();
    void setConsoleController(ConsoleController consoleController);

    Map getMap();

    void setPrimaryStage(Stage primaryStage);

    Server getServer();

    Visualize getVisualize();

    CarManagement getCarManagement();

    CarInfoController getCarInfoController();
    void setCarInfoController(CarInfoController carInfoController);


    Stage getPrimaryStage();

    void setMainController(MainController mainController);
    MainController getMainController();

    void setRecognitionHistoryController(RecognitionHistoryController controller);
    RecognitionHistoryController getRecognitionHistoryController();

    void setMenuMapController(MenuMapController menuMapController);

    void setAppPanelController(AppPanelController appPanelController);

    ConnectionInfoController getConnectionInfoController();
    void setConnectionInfoController(ConnectionInfoController connectionInfoController);

    MenuViewController getMenuViewController();
    void setMenuViewController(MenuViewController menuViewController);

    RunManagement getRunManagement();

    void setRunManagement(RunManagement runManagement);

    RunAppController getRunAppController();

    void setRunAppController(RunAppController runAppController);

    TimeController getTimeController();

    void setTimeController(TimeController timeController);

    RunHistoryController getRunHistoryController();

    void setRunHistoryController(RunHistoryController runHistoryController);

    NoiseController getNoiseController();

    void setNoiseController(NoiseController noiseController);

    ModelMenuController getModelMenuController();

    void setModelMenuController(ModelMenuController modelMenuController);
}
