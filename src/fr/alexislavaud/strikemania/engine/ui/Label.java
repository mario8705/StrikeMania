package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.Color;
import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;

import java.nio.ByteBuffer;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Label extends UiComponent {
    private String text;
    private float scale;
    private Color color;
    private String cachedText;
    private ByteBuffer fontCache;
    private int textQuads;
    private Vector2f fontSize;

    public Label() {
        this("");
    }

    public Label(String text) {
        this.text = text;
        this.scale = 1.0f;
        this.color = new Color(1.0f, 1.0f, 1.0f);
        this.fontSize = new Vector2f();
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void render(RenderManager renderManager) {
        updateFontCache();

        GL11.glColor4f(color.red, color.green, color.blue, color.alpha);

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, 0.0f);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, fontCache);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, textQuads * 4);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glPopMatrix();

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void updateFontCache() {
        if (cachedText == null || !cachedText.equals(text)) {
            fontCache = BufferUtils.createByteBuffer(text.length() * 512);
            textQuads = STBEasyFont.stb_easy_font_print(0.0f, 0.0f, text, null, fontCache);

            fontSize.x = STBEasyFont.stb_easy_font_width(text);
            fontSize.y = STBEasyFont.stb_easy_font_height(text);

            cachedText = text;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2f getFontSize() {
        return fontSize;
    }
}
