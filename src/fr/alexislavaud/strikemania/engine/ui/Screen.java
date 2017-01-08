package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.*;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public abstract class Screen implements IEventReceiver {
    protected Container rootContainer;

    public Screen() {
        this.rootContainer = new Container();
    }

    public void update(float tpf) {
        rootContainer.update(tpf);
    }

    public void render(RenderManager renderManager) {
        rootContainer.render(renderManager);
    }

    public void init(UiManager uiManager, AssetManager assetManager) {

    }

    public void destroy() {

    }

    public void setSize(float x, float y) {
        rootContainer.setSize(new Vector2f(x, y));
    }

    @Override
    public void onMouseMove(float x, float y) {
        rootContainer.onMouseMove(x, y);
    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {
        rootContainer.onMouseButtonDown(x, y, button);
    }

    @Override
    public void onMouseButtonUp(float x, float y, int button) {
        rootContainer.onMouseButtonUp(x, y, button);
    }

    public Container getRootContainer() {
        return rootContainer;
    }

    public Vector2f getSize() {
        return rootContainer.getSize();
    }
}
