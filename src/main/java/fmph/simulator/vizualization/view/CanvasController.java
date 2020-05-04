package fmph.simulator.vizualization.view;

import fmph.simulator.Map;
import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.app.context.interfaces.Contextable;
import fmph.simulator.map.LaserTag;
import fmph.simulator.map.Segment;
import fmph.simulator.models.CarModel;
import fmph.simulator.models.CarState;
import fmph.simulator.vizualization.animate.DrawableCar;
import fmph.simulator.vizualization.animate.idealCar.IdealCar;
import fmph.simulator.vizualization.animate.idealCar.State;
import fmph.simulator.vizualization.animate.realCar.RealCarBaseModel;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.component.VisualizeConfig;
import fmph.simulator.vizualization.console.Message;
import fmph.simulator.vizualization.console.MessageType;
import fmph.simulator.vizualization.draw.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.apache.commons.configuration2.PropertiesConfiguration;


public class CanvasController extends Canvas implements Contextable {

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

    public void initialize(){
        gc = getGraphicsContext2D();
        map = ContextBuilder.getContext().getMap();

        state.setAnim_seg(map.getMap().getSegments().get(0)); //default value
        idealCar = new IdealCar(map.getMap());
        realCarModel = new RealCarBaseModel(map.getMap());


        this.setOnMouseClicked(event -> {
            printClickInfo(event);
        });

        resize();

        this.setOnMouseMoved(event -> {
            showPosition(event);
        });

        makeDrawableCar();

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
            CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
            if(drag) {
                carState.setPosX(Function.txinv(e.getX()));
                carState.setPosY(Function.tyinv(e.getY()));
            }
            else if(dragTurn){
                double d = distanceFromCar(e.getX(),e.getY());
                carState.setCarAngle(carState.getCarAngle()+d*1.3);
            }

        });


    }

    private double distanceFromCar(double x, double y){
        CarState carState = ContextBuilder.getContext().getRunManagement().getActualRun().getCarState();
        return Math.sqrt(  Math.pow(Function.txinv(x)-carState.getPosX(),2) + Math.pow(Function.tyinv(y)-carState.getPosY(),2));

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

        //System.out.println( "diff y" +diffMapY + " diff canvas "+ diffCanvasY +" ration" + ratioY );    //todo log
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
}


