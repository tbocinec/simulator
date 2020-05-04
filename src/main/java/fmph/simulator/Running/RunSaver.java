package fmph.simulator.Running;

import fmph.simulator.app.context.ContextBuilder;

public class RunSaver {

    static public void save(){
        ContextBuilder.getContext().getRunManagement().getRunningHistory();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(new SimpleBean());
    }
}
