package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Viewport {
    public int x, y;
    public int width, height;

    public Viewport() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Viewport(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Viewport(int width, int height) {
        this(0, 0, width, height);
    }
}
