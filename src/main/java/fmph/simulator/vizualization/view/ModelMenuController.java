package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.models.car.CarManagment;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import javafx.event.ActionEvent;
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
        CarManagment carManagment = context.getCarModel().getCarManagment();
        context.getCarModel().getCarManagment().getSetCar().forEach(
                e-> {
                    MenuItem menuItem = new MenuItem(e.getName());
                    menuItem.setDisable( e == carManagment.getActual());
                    menuItem.setOnAction( a ->  {
                        new Message("Set model "+e.getName(), MessageType.INFO);
                        carManagment.setActual(e);
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
