package app.context.interfaces;

import com.Server;
import fmph.simulator.Map;
import fmph.simulator.models.CarModel;
import fmph.simulator.recognization.RecognitionHistory;
import fmph.simulator.vizualization.Visualize;
import fmph.simulator.vizualization.view.*;
import javafx.stage.Stage;

public interface Context {

    CanvasController getCanvasController();
    void setCanvasController(CanvasController canvasController);

    ConsoleController getConsoleController();
    void setConsoleController(ConsoleController consoleController);

    RecognitionHistory getRecognizationHistory();
    void setRecognizationHistory(RecognitionHistory recognizationHistory);

    Map getMap();

    void setPrimaryStage(Stage primaryStage);

    Server getServer();

    Visualize getVisualize();

    CarModel getCarModel();

    CarInfoController getCarInfoController();
    void setCarInfoController(CarInfoController carInfoController);


    Stage getPrimaryStage();

    void setMainController(MainController mainController);
    MainController getMainController();

    void setRecognitionHistoryController(RecognitionHistoryController controller);
    RecognitionHistoryController getRecognitionHistoryController();
}
