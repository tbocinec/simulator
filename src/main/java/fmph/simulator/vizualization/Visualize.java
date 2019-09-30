package fmph.simulator.vizualization;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Visualize extends Application implements Runnable {

	Thread Cavasthread;
	MyCanvas plocha;
	Boolean run = true;
	public void run() {
		while(run){				
			run = false;
			Platform.runLater(new Runnable() {			
				public void run() {
					// TODO Auto-generated method stub
					plocha.paint();
					
					 
				}
			});			
		
		 try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		plocha = new MyCanvas();	
		Cavasthread = new Thread(this);
		Cavasthread.start();	
		Scene scene = new Scene(new Pane(plocha));	
		primaryStage.setScene(scene);		
		primaryStage.setTitle("Simulate");
		primaryStage.show();
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
