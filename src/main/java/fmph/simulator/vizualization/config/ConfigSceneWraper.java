package fmph.simulator.vizualization.config;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.vizualization.view.uxcomponent.MenuLabelComponent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.commons.configuration2.PropertiesConfiguration;



public class ConfigSceneWraper {

    private Pane mainPain;
    private VBox box;
    private Scene scene;

    public ConfigSceneWraper() {
        mainPain = new Pane();
        scene = new Scene(mainPain);
        box = new VBox(2);
        box.paddingProperty().setValue(new Insets(10,15,20,10));
        mainPain.getChildren().add(box);
        prepareConfig();
    }

    private void prepareConfig() {
        PropertiesConfiguration config = ContextBuilder.getContext().config;
        Text header = new Text("Config was loaded at startup, from the config file : app.properties");
        header.setFont(new Font(15));
        header.setUnderline(true);
        box.getChildren().add(header);
        config.getKeys().forEachRemaining(e ->
        {
            String key = e;
            String value = config.getString(key);
            MenuLabelComponent item = new MenuLabelComponent(key,new Text(value));
            box.getChildren().add(item);
        });

    }

    public Scene getScene() {
        return scene;
    }


}
