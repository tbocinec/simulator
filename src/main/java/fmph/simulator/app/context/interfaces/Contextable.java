package fmph.simulator.app.context.interfaces;

import fmph.simulator.app.context.ContextBuilder;

public interface Contextable {
    Context context =  ContextBuilder.getContext();
    void registryToContext();
}
