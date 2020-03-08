package fmph.simulator.vizualization.draw;

import fmph.simulator.map.MapSchema;
import fmph.simulator.vizualization.component.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CenterDraw extends  AbstractDraw  {

    public CenterDraw(GraphicsContext gc, MapSchema map) {
        super(gc,map);
    }

    @Override
    public void paint() {
        gc.beginPath();
        gc.setFill(Color.RED);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(Function.tx(-0.02), Function.ty(-0.02));
        gc.lineTo(Function.tx(0.02), Function.ty(0.02));
        gc.moveTo(Function.tx(0.02), Function.ty(-0.02));
        gc.lineTo( Function.tx(-0.02), Function.ty(0.02));

        gc.stroke();
    }
}
