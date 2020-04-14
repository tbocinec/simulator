package app.context.exceptions;

public class ContextException extends RuntimeException {

    public ContextException() {
        super("Controller was not loaded by fxml loader");
    }

    public ContextException(Class consoleControllerClass) {
        super("Controller for class "+consoleControllerClass.getSimpleName()+" was not loaded by fxml loader");
    }
}
