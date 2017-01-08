package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class EngineException extends Exception {
    public EngineException() {
        super();
    }

    public EngineException(String message) {
        super(message);
    }

    public EngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
        super(message, cause, enableSuppresion, writableStackTrace);
    }
}
