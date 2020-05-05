package fmph.simulator.Running;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fmph.simulator.app.context.ContextBuilder;
import javafx.stage.FileChooser;

import java.io.*;

public class RunSaver {
    private static FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

    static public void save(){
        RunningHistory runHistory = ContextBuilder.getContext().getRunManagement().getRunningHistory();

        XmlMapper xmlMapper = new XmlMapper();
        String xml;
        try {
            xml = xmlMapper.writeValueAsString(runHistory);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Problem with save actual state",e);
        }
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files

        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(ContextBuilder.getContext().getPrimaryStage());

        if (file != null) {
            saveTextToFile(xml, file);
        }
    }

    static public void load(){
        XmlMapper xmlMapper = new XmlMapper();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(ContextBuilder.getContext().getPrimaryStage());
        if(file != null){
            try {
                RunningHistory runHistory =  xmlMapper.readValue(readFile(file), RunningHistory.class);
                ContextBuilder.getContext().getRunManagement().setRunningHistory(runHistory);
            } catch (IOException e) {
                throw new RuntimeException("Problem deserialize runHistory",e);
            }
        }

    }

    static private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException("Problem save actual state to file",ex);
        }
    }
    static private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch ( IOException ex) {
             throw new RuntimeException("Problem load file with history",ex);

        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                throw new RuntimeException("Problem load file with history,problem close buffer",ex);
            }
        }

        return stringBuffer.toString();
    }
}
