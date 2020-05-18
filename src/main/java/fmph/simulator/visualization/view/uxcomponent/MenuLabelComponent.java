package fmph.simulator.visualization.view.uxcomponent;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MenuLabelComponent  extends HBox {
    Label  label;

    public MenuLabelComponent(String labelText, Node node){
        super();
        this.setPadding(new Insets(2,2,2,2));
        label = new Label(labelText);
        label.setLabelFor(node);
        label.setMinWidth(210);
        this.getChildren().addAll(label,node);
    }
}
