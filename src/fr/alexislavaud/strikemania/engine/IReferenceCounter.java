package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public interface IReferenceCounter {
    void addReference();
    void releaseReference();
    int getRefCount();
    void destroy();
}
