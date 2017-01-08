package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class Mathf {
    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }
}
