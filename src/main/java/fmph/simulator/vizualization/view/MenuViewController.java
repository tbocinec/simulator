package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.control.*;

public class MenuViewController extends Menu implements Contextable {

    RadioMenuItem RoadDraw ;
    RadioMenuItem PathDraw ;
    RadioMenuItem IdentifiersDraw;
    RadioMenuItem LabelDraw;
    RadioMenuItem RoadGapDraw;
    RadioMenuItem CenterDraw;


    public MenuViewController(){
        registryToContext();
        init();
    }

    public void init(){
        RoadDraw = new RadioMenuItem("Show Road");
        RoadDraw.setSelected(context.config.getBoolean("view.RoadDraw"));
        RoadDraw.setOnAction(e-> context.config.setProperty("view.RoadDraw", RoadDraw.isSelected()));

        PathDraw  = new RadioMenuItem("Show Path Road");
        PathDraw.setSelected(context.config.getBoolean("view.PathDraw"));
        PathDraw.setOnAction(e-> context.config.setProperty("view.PathDraw", PathDraw.isSelected()));

        IdentifiersDraw = new RadioMenuItem("Show Identifiers");
        IdentifiersDraw.setSelected(context.config.getBoolean("view.IdentifiersDraw"));
        IdentifiersDraw.setOnAction(e-> context.config.setProperty("view.IdentifiersDraw", IdentifiersDraw.isSelected()));

        LabelDraw = new RadioMenuItem("Show Label");
        LabelDraw.setSelected(context.config.getBoolean("view.LabelDraw"));
        LabelDraw.setOnAction(e-> context.config.setProperty("view.LabelDraw", LabelDraw.isSelected()));

        RoadGapDraw = new RadioMenuItem("Show RoadGap");
        RoadGapDraw.setSelected(context.config.getBoolean("view.RoadGapDraw"));
        RoadGapDraw.setOnAction(e-> context.config.setProperty("view.RoadGapDraw", RoadGapDraw.isSelected()));

        CenterDraw = new RadioMenuItem("Show Center");
        CenterDraw.setSelected(context.config.getBoolean("view.CenterDraw"));
        CenterDraw.setOnAction(e-> context.config.setProperty("view.CenterDraw", CenterDraw.isSelected()));

        this.getItems().addAll(RoadDraw,PathDraw,IdentifiersDraw,LabelDraw,RoadGapDraw,CenterDraw);
    }



    @Override
    public void registryToContext() {
        context.setMenuViewController(this);
    }
}
