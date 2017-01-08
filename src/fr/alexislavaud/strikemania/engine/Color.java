package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Color {
    public float red, green, blue, alpha;

    public Color() {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
}
