package fr.alexislavaud.strikemania.ui;

import fr.alexislavaud.strikemania.engine.*;
import fr.alexislavaud.strikemania.engine.ui.*;
import fr.alexislavaud.strikemania.main.StrikeMania;

import java.io.IOException;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class MainMenu extends Screen {
    private Music music;
    private Image logo;
    private Button play;
    private Button options;
    private Button quit;

    @Override
    public void update(float tpf) {
        super.update(tpf);

        Vector2f size = getSize();

        for (UiComponent component : new UiComponent[] { logo, play, options, quit }) {
            Vector2f componentPosition = component.position;
            componentPosition.x = (size.x - component.getSize().x) / 2.0f;
        }

        play.position.y = 40.0f / 100.0f * size.y;
        options.position.y = 50.0f / 100.0f * size.y;
        quit.position.y = 60.0f / 100.0f * size.y;

        music.update();
    }

    @Override
    public void init(UiManager uiManager, AssetManager assetManager) {
        Texture logoTexture = null;

        try {
            logoTexture = assetManager.loadTexture("Assets/logo.png");
            logoTexture.releaseReference();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.logo = new Image(logoTexture);
        rootContainer.addChild(logo);

        this.play = new Button("Play!");
        this.options = new Button("Options");
        this.quit = new Button("Quit");

        play.setButtonCallback(() -> {
            uiManager.setCurrentScreen(null);
            StrikeMania.getInstance().loadLevel("");
        });

        options.setButtonCallback(() -> {
            uiManager.setCurrentScreen(new OptionsScreen());
        });

        quit.setButtonCallback(() -> Application.quit());

        rootContainer.addChilds(play, options, quit);

        this.music = Music.loadMusic("Assets/main_theme.ogg");
//        music.togglePlayPause();
    }

    @Override
    public void destroy() {
        if (logo != null) {
            logo.destroy();
        }

        music.destroy();
    }
}
