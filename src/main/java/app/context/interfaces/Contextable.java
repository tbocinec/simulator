package app.context.interfaces;

import app.context.interfaces.Context;
import app.context.ContextBuilder;

public interface Contextable {
    Context context =  ContextBuilder.getContext();
    void registryToContext();
}
