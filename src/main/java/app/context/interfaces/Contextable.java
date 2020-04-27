package app.context.interfaces;

import app.context.ContextBuilder;

public interface Contextable {
    Context context =  ContextBuilder.getContext();
    void registryToContext();
}
