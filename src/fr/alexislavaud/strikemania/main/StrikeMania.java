package fr.alexislavaud.strikemania.main;

import fr.alexislavaud.strikemania.Level;
import fr.alexislavaud.strikemania.engine.*;
import fr.alexislavaud.strikemania.ui.MainMenu;
import fr.alexislavaud.strikemania.ui.OptionsScreen;

import java.io.IOException;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class StrikeMania extends Application {
    private static StrikeMania instance = null;
    private Level level;

    public StrikeMania() {
        super("StrikeMania");
    }

    Sprite mushroomSprite;

    @Override
    protected void init() {
        try {
            Texture texture = assetManager.loadTexture("Assets/mushroom.png");
            this.mushroomSprite = new Sprite(texture);
            mushroomSprite.setSize(new Vector2f(1280, 720));
            texture.releaseReference();
        } catch (IOException e) {
            e.printStackTrace();
        }

     //   uiManager.setCurrentScreen(new SplashScreen());
        uiManager.setCurrentScreen(new MainMenu());
    }

    @Override
    protected void update(float tpf) {
        if (deviceWindow.isCloseRequested()) {
            isRunning = false;
        }
    }

    @Override
    protected void render() {
 //       renderManager.drawSprite(mushroomSprite);

        if (level != null) {
            level.render(renderManager);
        }
    }

    @Override
    protected void destroy() {

    }

    public void loadLevel(String filename) {
        try {
            this.level = new Level(assetManager.loadTexture("Assets/tileset_candy.png", TextureFilter.Nearest));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Level loaded");

        // TODO: create a proper 'load' method
    }

    public static StrikeMania getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new StrikeMania();
        instance.start();
    }
}
