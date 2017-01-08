package fr.alexislavaud.strikemania.engine;

import fr.alexislavaud.strikemania.engine.ui.Screen;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class UiManager implements IEventReceiver {
    private Screen currentScreen;
    private Screen nextScreen;
    private final AssetManager assetManager;
    private final Viewport viewport;

    public UiManager(AssetManager assetManager, Viewport viewport) {
        this.assetManager = assetManager;
        this.viewport = viewport;
    }

    public void update(float tpf) {
        if (nextScreen != currentScreen) {
            if (currentScreen != null) {
                currentScreen.destroy();
            }

            this.currentScreen = nextScreen;

            if (currentScreen != null) {
                currentScreen.init(this, assetManager);
            }
        }

        if (currentScreen != null) {
            currentScreen.setSize(viewport.width, viewport.height);
            currentScreen.update(tpf);
        }
    }

    public void render(RenderManager renderManager) {
        if (currentScreen != null) {
            currentScreen.render(renderManager);
        }
    }

    @Override
    public void onMouseMove(float x, float y) {
        if (currentScreen != null) {
            currentScreen.onMouseMove(x, y);
        }
    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {
        if (currentScreen != null) {
            currentScreen.onMouseButtonDown(x, y, button);
        }
    }

    @Override
    public void onMouseButtonUp(float x, float y, int button) {
        if (currentScreen != null) {
            currentScreen.onMouseButtonUp(x, y, button);
        }
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.nextScreen = currentScreen;
    }
}
