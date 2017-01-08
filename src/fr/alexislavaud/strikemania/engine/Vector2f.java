package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Vector2f {
    public float x, y;

    public Vector2f() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2f mul(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public Vector2f divide(float scalar) {
        return new Vector2f(x / scalar, y / scalar);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
