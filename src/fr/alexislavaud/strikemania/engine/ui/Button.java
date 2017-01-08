package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Vector2f;
import fr.alexislavaud.strikemania.engine.ui.UiComponent;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;

import java.nio.ByteBuffer;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Button extends UiComponent {
    private String text;
    private String cachedText;
    private ByteBuffer fontCache;
    private int textQuads;
    private Vector2f fontSize;
    private boolean buttonHovered, buttonDown;
    private Runnable buttonCallback;

    public Button(String text) {
        this.text = text;
        this.position = new Vector2f(0.0f, 0.0f);
        this.size = new Vector2f(256.0f, 64.0f);
        this.fontSize = new Vector2f();
        this.buttonHovered = false;
        this.buttonDown = false;
        this.buttonCallback = null;
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void render(RenderManager renderManager) {
        int bottomMarginPx = buttonDown ? 2 : 5;

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1.0f, 0.47f, 0.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glVertex2f(size.x, 0.0f);
        GL11.glVertex2f(size.x, size.y - bottomMarginPx);
        GL11.glVertex2f(0.0f, size.y - bottomMarginPx);

        GL11.glColor3f(0.91f, 0.25f, 0.05f);
        GL11.glVertex2f(0.0f, size.y - bottomMarginPx);
        GL11.glVertex2f(size.x, size.y - bottomMarginPx);
        GL11.glVertex2f(size.x, size.y);
        GL11.glVertex2f(0.0f, size.y);
        GL11.glEnd();

        GL11.glColor3f(0.0f, 0.0f, 0.0f);

        updateFontCache();

        GL11.glPushMatrix();
        GL11.glTranslatef((size.x - fontSize.x * 2.0f) / 2.0f, (size.y - bottomMarginPx - fontSize.y) / 2.0f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 1.0f);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, fontCache);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, textQuads * 4);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glPopMatrix();

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void updateFontCache() {
        if (cachedText != text) {
            fontCache = BufferUtils.createByteBuffer(text.length() * 512);
            textQuads = STBEasyFont.stb_easy_font_print(0.0f, 0.0f, text, null, fontCache);

            fontSize.x = STBEasyFont.stb_easy_font_width(text);
            fontSize.y = STBEasyFont.stb_easy_font_height(text);

            cachedText = text;
        }
    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            buttonDown = true;
        }
    }

    @Override
    public void onMouseButtonUp(float x, float y, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (buttonHovered && buttonDown && buttonCallback != null) {
                buttonCallback.run();
            }

            buttonDown = false;
        }
    }

    @Override
    public void onMouseIn() {
        this.buttonHovered = true;
    }

    @Override
    public void onMouseOut() {
        this.buttonHovered = false;
    }

    public void setButtonCallback(Runnable buttonCallback) {
        this.buttonCallback = buttonCallback;
    }
}
