package fmph.simulator.vizualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConnectionInfoController extends VBox implements Contextable {

    Text url;
    Text status;
    Text lastComunincation;
    Text otherInfo;

    public ConnectionInfoController(){
        registryToContext();
        initText();
    }

    public void initText(){

        url = new Text("Server path : "+getServerPath());
        status = new Text("Server status : unconnect");
        lastComunincation = new Text("Last comunication at: ");
        otherInfo = new Text("Other info : ");

        this.getChildren().add(url);
        this.getChildren().add(status);
        this.getChildren().add(lastComunincation);
        this.getChildren().add(otherInfo);

    }


    private String getServerPath(){
        String ip = context.getServer().getMyIp();
        String port = String.valueOf(context.config.getInt("socket.port"));
        return  ip + ":"+port;
    }


    @Override
    public void registryToContext() {
        context.setConnectionInfoController(this);
    }
}
