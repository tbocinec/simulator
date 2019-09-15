package fmph.simulator.vizualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Visualize extends Application implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MyCanvas plocha = new MyCanvas();			
		Scene scene = new Scene(new Pane(plocha));	
		primaryStage.setScene(scene);		
		primaryStage.setTitle("Simulate");
		primaryStage.show();
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
