package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public abstract class Application {
    private static Application instance = null;
    private final String appTitle;
    protected volatile boolean isRunning;
    protected DeviceWindow deviceWindow;
    protected AssetManager assetManager;
    protected SoundManager soundManager;
    protected RenderManager renderManager;
    protected UiManager uiManager;
    protected InputManager inputManager;

    public Application(String appTitle) {
        this.appTitle = appTitle;

        this.isRunning = false;
    }

    public void start() {
        if (isRunning)
            return;

        this.isRunning = true;
        instance = this;

        try {
            initApp();
            loop();
            destroyApp();
        }
        catch (EngineException e) {
            e.printStackTrace();

            this.isRunning = false;
        }
    }

    private void initApp() throws EngineException {
        this.deviceWindow = new DeviceWindow(appTitle);
        this.assetManager = new AssetManager();
        this.soundManager = new SoundManager();
        this.renderManager = new RenderManager(deviceWindow.getViewport());
        this.uiManager = new UiManager(assetManager, deviceWindow.getViewport());
        this.inputManager = new InputManager(deviceWindow);

        inputManager.addEventReceiver(uiManager);
    }

    private void destroyApp() {
        soundManager.destroy();
        assetManager.destroy();
        deviceWindow.destroy();
    }

    private void loop() {
        init();

        long lastFrameTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            float tpf = (currentTime - lastFrameTime) / 1000000000.0f;
            lastFrameTime = currentTime;

            deviceWindow.pollEvents();
            assetManager.collectGarbage();
            soundManager.update();
            // inputManager.update();

            uiManager.update(tpf);
            update(tpf);

            renderManager.beginScene();
            render();
            uiManager.render(renderManager);
            renderManager.endScene();

            deviceWindow.presentFrame();
        }

        destroy();
    }

    public static void quit() {
        instance.isRunning = false;
    }

    protected abstract void init();
    protected abstract void update(float tpf);
    protected abstract void render();
    protected abstract void destroy();
}
