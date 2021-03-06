package fmph.simulator.visualization.view;

import fmph.simulator.Map;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.car.CarManagement;
import fmph.simulator.car.CarState;
import fmph.simulator.visualization.Tickable;
import fmph.simulator.visualization.animate.DrawableCar;
import fmph.simulator.visualization.animate.idealCar.IdealCar;
import fmph.simulator.visualization.animate.idealCar.State;
import fmph.simulator.visualization.animate.realCar.RealCarBaseModel;
import fmph.simulator.visualization.component.Function;
import fmph.simulator.visualization.component.VisualizeConfig;
import fmph.simulator.visualization.console.Message;
import fmph.simulator.visualization.console.MessageType;
import fmph.simulator.visualization.draw.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.commons.configuration2.PropertiesConfiguration;



public class CanvasController extends Canvas implements Contextable, Tickable {

    int w= 800;int h=600;
    GraphicsContext gc;
    Map map;
    State state = State.getState();

    IdealCar idealCar;
    DrawableCar realCarModel;

    public CanvasController(){
        registryToContext();
        setWidth(w);
        setHeight(h);
    }

    public void initialize() {
        gc = getGraphicsContext2D();

        initCar();
        this.setOnMouseClicked(event -> {
            printClickInfo(event);
        });

        resize();

        this.setOnMouseMoved(event -> {
            showPosition(event);
        });

        makeDrawableCar();


        if (ContextBuilder.getContext().config.getBoolean("app.enableScroll")) {
            makeScrolable();
        }

    }

    public void initCar() {
        map = ContextBuilder.getContext().getMap();

        state.setAnim_seg(map.getMap().getSegments().get(0)); //default value
        idealCar = new IdealCar(map.getMap());
        realCarModel = new RealCarBaseModel(map.getMap());
    }

    private void makeScrolable() {
        this.setOnScroll((ScrollEvent event) -> {
            VisualizeConfig visualizeConfig = VisualizeConfig.GetConfig();
            double xm = Function.txinv(event.getX()); //- canvas.offsetLeft
            double ym = Function.tyinv(event.getY());  //- canvas.offsetTop
            double delta = 1 + 0.1 * Math.max(-1, Math.min(1, (event.getDeltaY())));
            visualizeConfig.setQ(visualizeConfig.getQ() * delta);
            double xm2 = Function.txinv(event.getX()); //- canvas.offsetLeft
            double ym2 = Function.tyinv(event.getY());  //- canvas.offsetTop
            visualizeConfig.setCar_width_scale_factor(visualizeConfig.getCar_width_scale_factor() * delta);
            visualizeConfig.setCar_length_scale_factor(visualizeConfig.getCar_length_scale_factor() * delta);

            visualizeConfig.setTranslate_x(visualizeConfig.getTranslate_x() + xm2 - xm);
            visualizeConfig.setTranslate_y(visualizeConfig.getTranslate_y() + ym2 - ym);
            Function.rescale_shapes();

        });
    }



    Boolean drag = false;
    Boolean dragTurn = false;

    private void makeDrawableCar() {
        this.setOnMousePressed(e-> {
            if(e.isPrimaryButtonDown()) {
                if (distanceFromCar(e.getX(), e.getY()) < 0.1) {
                    drag = true;
                }
            }else{
                dragTurn = true;
            }
        });


        this.setOnMouseReleased(e-> {
            drag=false;
            dragTurn = false;
        });


        this.setOnMouseDragged(e->{
            CarManagement carManagement = ContextBuilder.getContext().getCarManagement();
            CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
            if(drag) {
                carState.getPos().setX(Function.txinv(e.getX()));
                carState.getPos().setY(Function.tyinv(e.getY()));
                carManagement.computeHelpPoint();
            }
            else if(dragTurn){
                double d = distanceFromCar(e.getX(),e.getY());
                double angle = Math.atan2(carState.getPos().getY()-e.getY(),carState.getPos().getX()-e.getX() );//atan2(p1.y - p2.y, p1.x - p2.x)
                carState.setCarAngle(carState.getCarAngle()+d*1.3);
                carManagement.computeHelpPoint();
            }

        });


    }

    private double distanceFromCar(double x, double y){
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        return Math.sqrt(  Math.pow(Function.txinv(x)-carState.getPos().getX(),2) + Math.pow(Function.tyinv(y)-carState.getPos().getY(),2));

    }


    void resize() {
        Pane parent = (Pane) getParent();
        w = (int) (parent.getWidth() );
        h = (int)  (parent.getHeight() );
        VisualizeConfig.GetConfig().setCw(w);
        VisualizeConfig.GetConfig().setCh(h);
        this.setWidth(w);
        this.setHeight(h);

        VisualizeConfig vc  = VisualizeConfig.GetConfig();
        vc.setQ(100);

        double diffMapY = Math.abs( vc.getMaprange()[2] -  vc.getMaprange()[3]);
        double diffCanvasY = Math.abs(  Function.tyinv(vc.getMarginy()) -  Function.tyinv(h - vc.getMarginy())   );

        double ratioY =  diffCanvasY  /diffMapY * 100;


        VisualizeConfig.GetConfig().setQ(ratioY);
    }




    public void showPosition(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        Double dx =  Math.round(Function.txinv(x)*100) / 100.0;
        Double dy =  Math.round(Function.tyinv(y)*100) / 100.0;

        ContextBuilder.getContext().getMainController().setLeftStatusText(String.format("Mouse distance from center [%fm,%fm]",dx,dy));

    }


    public void printClickInfo(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        Double dx =  Math.round(Function.txinv(x)*100) / 100.0;
        Double dy =  Math.round(Function.tyinv(y)*100) / 100.0;

        new Message("X,Y = ["+x+";"+y+"]  distance X,Y from center  [" + dx  + "m ;"+ dy + "m ]", MessageType.INFO);

        for (Segment segment : ContextBuilder.getContext().getMap().getMap().getSegments()) {
            int i = 1;
            for (LaserTag laserTag : segment.getLaserTags()) {
                i += 1;
                double xl = laserTag.getX();
                double yl = laserTag.getY();
                double distance = Math.sqrt(Math.pow(Function.txinv(x) - xl, 2) + Math.pow(Function.tyinv(y) - yl, 2));

                if (Math.abs(distance) < 0.05) {
                    new Message("click on segment " +
                            segment.getSegmentId() + " laser= " + laserTag.getType() + " in distance-" + distance, MessageType.INFO);

                }

            }
        }


    }
    public void paint(){
       Platform.runLater(new Runnable() {
            public void run(){
                try {
                    clearPain(gc);
                    paintMap();
                    paintCars();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }});

    }

    public void clearPain(GraphicsContext gc2) {
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.WHITE);
        gc.strokeRect(5,5,w-5,h-5);
    }

    public void paintCars() {
        if(context.config.getBoolean("app.showIdealCar")) {
            idealCar.animateCar(gc);
        }
        realCarModel.animateCar(gc);

    }

    public void paintMap() {
        PropertiesConfiguration config = context.config;
        if(config.getBoolean("view.RoadDraw")) {
            new RoadDraw(gc, map.getMap()).paint();
        }
        if(config.getBoolean("view.PathDraw")) {
            new PathDraw(gc, map.getMap()).paint();
        }
        if(config.getBoolean("view.IdentifiersDraw")) {
            new IdentifiersDraw(gc, map.getMap()).paint();
        }
        if(config.getBoolean("view.LabelDraw")) {
            new LabelDraw(gc, map.getMap()).paint();
        }
        if(config.getBoolean("view.RoadGapDraw")) {
            new RoadGapDraw(gc,map.getMap()).paint();
        }
        if(config.getBoolean("view.CenterDraw")) {
            new CenterDraw(gc,map.getMap()).paint();
        }
    }

    @Override
    public void registryToContext() {
        context.setCanvasController(this);

    }

    @Override
    public void tickSecond() {

    }

    @Override
    public void tick() {
        paint();
    }
}


