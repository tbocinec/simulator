package fmph.simulator.vizualization;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

public class MyCanvas extends Canvas{
	int w=900;int h=900;	
	
	public MyCanvas(){
		setWidth(w);
		setHeight(h);			
	}
	public void paint(){			
		GraphicsContext gc = getGraphicsContext2D();
		//clear
		gc.clearRect(0, 0,w, h);		//gc.fillRect(450, h, -600, -600);
		
	
		
	}
}