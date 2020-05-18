package fmph.simulator.visualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.car.car.ModelManagment;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public class ModelMenuController extends Menu implements Contextable {


    public ModelMenuController(){
        registryToContext();
        //this.addEventHandler(ActionEvent.ANY, e -> init());
        init();
    }

    private void init(){
        this.getItems().remove(0,this.getItems().size());
        ModelManagment modelManagment = context.getCarManagement().getModelManagment();
        context.getCarManagement().getModelManagment().getSetCar().forEach(
                e-> {
                    MenuItem menuItem = new MenuItem(e.getName());
                    menuItem.setDisable( e == modelManagment.getActual());
                    menuItem.setOnAction( a ->  {
                        new Message("Set model "+e.getName(), MessageType.INFO);
                        modelManagment.setActual(e);
                        init();

                    });
                    getItems().add(menuItem);
                }
        );


    }

    @Override
    public void registryToContext() {
        context.setModelMenuController(this);
    }
}
