package fr.alexislavaud.strikemania.ui;

import fr.alexislavaud.strikemania.engine.*;
import fr.alexislavaud.strikemania.engine.ui.Screen;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

/**
 * Created by Alexis Lavaud on 20/12/2016.
 */
public final class SplashScreen extends Screen {
    private UiManager uiManager;
    private Texture splashTexture;
    private float time;

    public SplashScreen() {
        this.time = 0.0f;
    }

    @Override
    public void update(float tpf) {
        time += tpf;

        if (time > 4.0f) {
            uiManager.setCurrentScreen(new MainMenu());
        }
    }

    @Override
    public void render(RenderManager renderManager) {
        Vector2f size = getSize();

        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glRectf(0.0f, 0.0f, size.x, size.y);

        float alpha = 0.0f;

        if (time < 1.0f) {
            alpha = time;
        }
        else if (time >= 1.0f && time < 3.0f) {
            alpha = 1.0f;
        }
        else if (time >= 3.0f && time < 4.0f) {
            alpha = 4.0f - time;
        }

        if (splashTexture != null) {
            float x = (size.x - splashTexture.getWidth()) / 2.0f;
            float y = (size.y - splashTexture.getHeight()) / 2.0f;

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);

            Graphics.drawImage(splashTexture, x, y, splashTexture.getWidth(), splashTexture.getHeight());

            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {
        if (time >= 1.0f) {
            uiManager.setCurrentScreen(new MainMenu());
        }
    }

    @Override
    public void init(UiManager uiManager, AssetManager assetManager) {
        this.uiManager = uiManager;

        try {
            this.splashTexture = assetManager.loadTexture("Assets/splash.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (splashTexture != null) {
            splashTexture.releaseReference();
        }
    }
}
