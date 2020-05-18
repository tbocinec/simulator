package fmph.simulator.car.car;

import java.util.ArrayList;
import java.util.List;

public class ModelManagment {
    Model actual;
    List<Model> setCar;

    public ModelManagment() {
        setCar = new ArrayList<Model>();
        setCar.add(new CarCustom());
        setCar.add(new Car1to8());
        setCar.add(new Car1to10());
        actual = setCar.get(0);
    }

    public Model getActual() {
        return actual;
    }

    public void setActual(Model actual) {
        this.actual = actual;
    }
    public void setActual(int index) {
        this.actual = setCar.get(index);
    }

    public List<Model> getSetCar() {
        return setCar;
    }
}
