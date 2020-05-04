package fmph.simulator.vizualization.popup;

import javafx.scene.control.Alert;

public class Warning {

    static public void  newWarning(String text,String title,String headerText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
