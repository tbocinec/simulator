package fmph.simulator.visualization;

import fmph.simulator.app.context.ContextBuilder;
import fmph.simulator.visualization.view.CanvasController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Visualize  {
    CanvasController plocha;
    Boolean run = true;
    Stage primaryStage;
	Scene scene;


	public Visualize(final Stage primaryStage) {
		Parent root =null;
		try {
			URL urlToView = getClass().getResource("view/main.fxml");
			root = FXMLLoader.load(urlToView);
		} catch (IOException e) {
			//todo stop app and log
			e.printStackTrace();
			System.exit(3);
		}

		ContextBuilder.getContext().setPrimaryStage(primaryStage);
		this.primaryStage = primaryStage;
		this.scene = new Scene(root,1350,800);

		Platform.runLater(new Runnable() {
			public void run() {
				primaryStage.setScene(scene);
				primaryStage.setTitle("ATS");
				primaryStage.show();

			}
		});
	}

	//Visualize Thread
    public void run() {
		this.plocha = ContextBuilder.getContext().getCanvasController();
		ArrayList<Tickable> tickables = new  ArrayList<>();
		tickables.add(plocha);
		tickables.add(ContextBuilder.getContext().getRunManagement());

		plocha.initialize();
		try {
			Thread.sleep(200);
			int c=0;
			while (true) {
				if (run) {
					for(Tickable elemnt : tickables){
						elemnt.tick();
						if(c % 5 ==0){
							elemnt.tickSecond();
						}
					}
				}
				c++;
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    }



    public void stop(){
    	if(run == false){
    		throw new RuntimeException("Visualization already stopped");
		}
    	run = false;
	}


	public void pause() {
		this.run = !this.run;
	}
}
