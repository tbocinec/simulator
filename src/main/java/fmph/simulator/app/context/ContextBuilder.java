package fmph.simulator.app.context;

import fmph.simulator.app.context.interfaces.Context;

public class ContextBuilder {

    private ContextBuilder(){}
    private static Context context;


    static public Context getContext(){
        if(context == null){
           //context = new fmph.simulator.app.context.Context();
            context = new ContextMock();  //for moc and for SceneBuilder
        }
        return context;
    }
}
