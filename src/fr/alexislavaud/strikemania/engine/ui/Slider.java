package fr.alexislavaud.strikemania.engine.ui;

import fr.alexislavaud.strikemania.engine.Mathf;
import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

/**
 * Created by Alexis Lavaud on 20/12/2016.
 */
public final class Slider extends UiComponent {
    private float value;
    private boolean draggingCursor;

    public Slider() {
        this.size = new Vector2f(256.0f, 32.0f);
        this.value = 0.0f;
        this.draggingCursor = false;

        // TODO debug
        this.position = new Vector2f(256.0f, 32.0f);
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void render(RenderManager renderManager) {
        GL11.glColor3f(1.0f, 0.47f, 0.0f);
        drawCursor();

        float sliderBarY = (size.y - 10.0f) / 2.0f;

        GL11.glColor3f(0.91f, 0.25f, 0.05f);
        GL11.glRectf(10.0f, sliderBarY, size.x - 10.0f, sliderBarY + 10.0f);

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawCursor() {
        GL11.glPushMatrix();
        GL11.glTranslatef(value * (size.x - 20.0f) + 10.0f, size.y / 2.0f, 0.0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-10.0f, -10.0f);
        GL11.glVertex2f(10.0f, -10.0f);
        GL11.glVertex2f(10.0f, 10.0f);
        GL11.glVertex2f(-10.0f, 10.0f);
        GL11.glEnd();

        GL11.glPopMatrix();
    }

    @Override
    public void onMouseMove(float x, float y) {
        if (draggingCursor) {
            value = Mathf.clamp(x / size.x, 0.0f, 1.0f);
        }

        System.out.println(value);
    }

    @Override
    public void onMouseButtonDown(float x, float y, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            draggingCursor = true;
        }
    }

    @Override
    public void onMouseButtonUp(float x, float y, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            onMouseMove(x, y);
            draggingCursor = false;
        }
    }
}
