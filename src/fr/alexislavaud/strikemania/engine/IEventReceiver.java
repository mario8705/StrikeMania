package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public interface IEventReceiver {
    void onMouseMove(float x, float y);
    void onMouseButtonDown(float x, float y, int button);
    void onMouseButtonUp(float x, float y, int button);
}
