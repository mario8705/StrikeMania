package fr.alexislavaud.strikemania.engine;

import org.lwjgl.opengl.GL11;

/**
 * Created by Alexis Lavaud on 20/12/2016.
 */
public final class Graphics {
    public static void drawImage(Texture texture, float x, float y, float width, float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0f);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(width, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(0.0f, height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }
}
