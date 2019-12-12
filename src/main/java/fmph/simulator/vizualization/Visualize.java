package fmph.simulator.vizualization;

import app.Context;
import com.Server;
import com.sun.org.apache.xpath.internal.operations.Bool;
import fmph.simulator.vizualization.component.Function;
import fmph.simulator.vizualization.controlPanel.ControlPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Visualize  {
    MyCanvas plocha;
    Boolean run = true;
    Stage primaryStage;
	Scene scene;


	public Visualize(final Stage primaryStage) {
		//todo server change place
		this.plocha = new MyCanvas();
		this.primaryStage = primaryStage;

		BorderPane bPane = new BorderPane();
		bPane.setCenter(new Pane(plocha));
		bPane.setRight(Context.getContext().getControlPanel());
		bPane.setBottom(Context.getContext().getConsolePanel());

		this.scene = new Scene(bPane);

		bPane.setOnMouseClicked(event -> {
			double x = event.getX();
			double y = event.getY();
			System.out.println("X,Y = ["+x+";"+y+"]  transform  ["+ Function.tx(x)+";"+Function.ty(y)+"]" );
		});



		Platform.runLater(new Runnable() {
			public void run() {
				primaryStage.setScene(scene);
				primaryStage.setTitle("Simulate");
				primaryStage.show();
			}
		});
	}




    public void run() {
        while (run) {

			Context.getContext().getCarModel().run();
        	plocha.paint();
            try {
                Thread.sleep(220);
            } catch (InterruptedException e) {
                e.printStackTrace(); //todo exception
				System.out.println("chyba");
                System.exit(1);
            }
        }

    }



    public void stop(){
    	if(run == false){
    		throw new RuntimeException("Visualization already stopped");
		}
    	run = false;
	}



}
