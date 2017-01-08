package fr.alexislavaud.strikemania.engine;

import org.lwjgl.opengl.GL11;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class RenderManager {
    private final Viewport viewport;

    public RenderManager(Viewport viewport) {
        this.viewport = viewport;
    }

    public void beginScene() {
        GL11.glClearColor(0.25f, 0.51f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glViewport(viewport.x, viewport.y, viewport.width, viewport.height);

        float width = viewport.width, height = viewport.height;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0f, width, height, 0.0f, 1.0f, -1.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    public void endScene() {

    }

    public void drawSprite(Sprite sprite) {
        Texture texture = null;
        Vector2f position = sprite.getPosition();
        Vector2f size = sprite.getSize();

        if ((texture = sprite.getTexture()) != null) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            GL11.glTranslatef(position.x, position.y, 0.0f);

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

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
    }

    public Viewport getViewport() {
        return viewport;
    }
}
