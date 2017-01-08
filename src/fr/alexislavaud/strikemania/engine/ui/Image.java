package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Texture;
import org.lwjgl.opengl.GL11;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Image extends UiComponent {
    private Texture texture;

    public Image() {

    }

    public Image(Texture texture) {
        this.texture = texture;
        texture.addReference();

        size.x = texture.getWidth();
        size.y = texture.getHeight();
    }

    public void destroy() {
        if (texture != null) {
            texture.releaseReference();
            texture = null;
        }
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void render(RenderManager renderManager) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (texture != null) {
            int previousBoundTextureID = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex2f(0.0f, 0.0f);
            GL11.glTexCoord2f(1.0f, 0.0f);
            GL11.glVertex2f(size.x, 0.0f);
            GL11.glTexCoord2f(1.0f, 1.0f);
            GL11.glVertex2f(size.x, size.y);
            GL11.glTexCoord2f(0.0f, 1.0f);
            GL11.glVertex2f(0.0f, size.y);
            GL11.glEnd();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, previousBoundTextureID);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void onMouseMove(float x, float y) {

    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {

    }

    @Override
    public void onMouseButtonUp(float x, float y, int button) {

    }

    @Override
    public void onMouseIn() {

    }

    @Override
    public void onMouseOut() {

    }
}
