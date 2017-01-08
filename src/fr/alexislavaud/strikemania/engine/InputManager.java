package fr.alexislavaud.strikemania.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class InputManager {
    private List<IEventReceiver> eventReceiverList;
    private boolean[] keys;
    private Vector2f mousePosition;

    public InputManager(DeviceWindow deviceWindow) {
        this.eventReceiverList = new ArrayList<>();
        this.keys = new boolean[GLFW.GLFW_KEY_LAST];
        this.mousePosition = new Vector2f();

        GLFW.glfwSetKeyCallback(deviceWindow.getWindowID(), new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                // On my computer some keys are -1 and generate an IndexOutOfBoundException

                if (key > 0) {
                    if (action == GLFW.GLFW_PRESS) {
                        keys[key] = true;
                    } else if (action == GLFW.GLFW_RELEASE) {
                        keys[key] = false;
                    }
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(deviceWindow.getWindowID(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mousePosition.x = (float) xpos;
                mousePosition.y = (float) ypos;

                dispatchMouseMove(mousePosition.x, mousePosition.y);
            }
        });

        GLFW.glfwSetMouseButtonCallback(deviceWindow.getWindowID(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW.GLFW_PRESS) {
                    dispatchMouseButtonDown(mousePosition.x, mousePosition.y, button);
                }
                else if (action == GLFW.GLFW_RELEASE) {
                    dispatchMouseButtonUp(mousePosition.x, mousePosition.y, button);
                }
            }
        });
    }

    private void dispatchMouseMove(float x, float y) {
        for (IEventReceiver eventReceiver : eventReceiverList) {
            eventReceiver.onMouseMove(x, y);
        }
    }

    private void dispatchMouseButtonDown(float x, float y, int button) {
        for (IEventReceiver eventReceiver : eventReceiverList) {
            eventReceiver.onMouseButtonDown(x, y, button);
        }
    }

    private void dispatchMouseButtonUp(float x, float y, int button) {
        for (IEventReceiver eventReceiver : eventReceiverList) {
            eventReceiver.onMouseButtonUp(x, y, button);
        }
    }

    public void addEventReceiver(IEventReceiver eventReceiver) {
        eventReceiverList.add(eventReceiver);
    }

    public void removeEventReceiver(IEventReceiver eventReceiver) {
        eventReceiverList.remove(eventReceiver);
    }

    public Vector2f getMousePosition() {
        return mousePosition;
    }
}
