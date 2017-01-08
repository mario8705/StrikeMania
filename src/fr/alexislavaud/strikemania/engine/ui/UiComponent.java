package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Vector2f;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public abstract class UiComponent {
    protected UiComponent parent;
    public Vector2f position;
    protected Vector2f size;

    public UiComponent() {
        this.position = new Vector2f(0.0f, 0.0f);
        this.size = new Vector2f(0.0f, 0.0f);
    }

    public abstract void update(float tpf);
    public abstract void render(RenderManager renderManager);

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void onMouseMove(float x, float y) {}
    public void onMouseButtonDown(float x, float y, int button) {}
    public void onMouseButtonUp(float x, float y, int button) {}
    public void onMouseIn() {}
    public void onMouseOut() {}
}
