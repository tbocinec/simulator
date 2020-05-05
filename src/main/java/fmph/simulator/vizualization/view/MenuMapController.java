package fmph.simulator.vizualization.view;

import fmph.simulator.Map;
import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MenuMapController extends Menu implements Contextable {
    String PATH_TO_MAP  = "data/map";

    public MenuMapController(){
        registryToContext();
        loadMap();
    }

    private void loadMap(){
        this.getItems().remove(0,this.getItems().size());
        Map map = context.getMap();
        try (Stream<Path> walk = Files.walk(Paths.get(PATH_TO_MAP))) {

             walk.filter(Files::isRegularFile).forEach(e-> {
                 MenuItem mapItem = new MenuItem(e.getFileName().toString());
                 mapItem.setOnAction(event -> {Map.mapPath =  e.toAbsolutePath().toString(); map.loadMap(); this.loadMap();} );
                 if(Map.mapPath.contains(e.getFileName().toString())){
                     mapItem.setDisable(true);
                 }

                 this.getItems().add(mapItem);
             });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registryToContext() {
        context.setMenuMapController(this);
    }
}
