package fr.alexislavaud.strikemania.engine;

import com.sun.xml.internal.ws.api.pipe.Engine;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.util.logging.Logger;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class DeviceWindow {
    private final long windowID;
    private int width, height;
    private Viewport viewport;

    public DeviceWindow(String title) throws EngineException {
        GLFWErrorCallback.createPrint(System.err).set();

        this.width = 1280;
        this.height = 720;
        this.viewport = new Viewport(width, height);

        if (!GLFW.glfwInit()) {
            throw new EngineException("glfwInit failed");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        this.windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (windowID == MemoryUtil.NULL) {
            throw new EngineException("glfwCreateWindow failed");
        }

        GLFW.glfwMakeContextCurrent(windowID);
        GL.createCapabilities();

        Logger logger = Logger.getLogger(DeviceWindow.class.getName());
        logger.info(String.format("OpenGL renderer: %s", GL11.glGetString(GL11.GL_RENDERER)));
        logger.info(String.format("OpenGL version: %s", GL11.glGetString(GL11.GL_VERSION)));
        logger.info(String.format("OpenGL vendor: %s", GL11.glGetString(GL11.GL_VENDOR)));
        logger.info(String.format("OpenGL Shader Language: %s", GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION)));
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void presentFrame() {
        GLFW.glfwSwapBuffers(windowID);
    }

    public void destroy() {
        if (windowID != MemoryUtil.NULL) {
            GLFW.glfwDestroyWindow(windowID);
        }

        GLFW.glfwTerminate();
    }

    public boolean isCloseRequested() {
        return (windowID != MemoryUtil.NULL) ? GLFW.glfwWindowShouldClose(windowID) : true;
    }

    public long getWindowID() {
        return windowID;
    }

    public void setWindowSize(int width, int height) {
        this.width = width;
        this.height = height;
        viewport.width = width;
        viewport.height = height;

        if (windowID != MemoryUtil.NULL) {
            GLFW.glfwSetWindowSize(windowID, width, height);
        }
    }

    public Viewport getViewport() {
        return viewport;
    }
}
