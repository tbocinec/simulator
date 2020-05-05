package fmph.simulator.vizualization.config;

import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfigController {

        public static void createPopUp(){
            final Stage myDialog = new Stage();
            myDialog.initModality(Modality.WINDOW_MODAL);

           ConfigSceneWraper sceneWraper = new ConfigSceneWraper();

            myDialog.setScene(sceneWraper.getScene());
            myDialog.show();
        }
}
