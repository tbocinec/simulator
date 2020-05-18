package fmph.simulator.visualization.view;

import fmph.simulator.app.context.interfaces.Contextable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void setStatus(String state) {
        status.setText("Server status "+state);
    }

    public void setAdditionalInfo(String s) {
        otherInfo.setText("Other info : "+s);
    }

    public void setlastComunication(boolean send) {
        String time  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        String type = send ? "Send" : "Receive";
        lastComunincation.setText("Last comunication  " +type + " at "+time);
    }
}
