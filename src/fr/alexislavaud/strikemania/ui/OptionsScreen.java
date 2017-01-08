package fr.alexislavaud.strikemania.ui;

import fr.alexislavaud.strikemania.engine.AssetManager;
import fr.alexislavaud.strikemania.engine.Color;
import fr.alexislavaud.strikemania.engine.UiManager;
import fr.alexislavaud.strikemania.engine.ui.Button;
import fr.alexislavaud.strikemania.engine.ui.Label;
import fr.alexislavaud.strikemania.engine.ui.Screen;
import fr.alexislavaud.strikemania.engine.ui.Slider;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class OptionsScreen extends Screen {
    private Label options;
    private Button back;
    private Slider musicVolume;

    public OptionsScreen() {

    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        options.position.x = (getSize().x - options.getFontSize().x * 4.0f) / 2.0f;
        back.position.set(5.0f, getSize().y - back.getSize().y - 5.0f);
    }

    @Override
    public void init(UiManager uiManager, AssetManager assetManager) {
        super.init(uiManager, assetManager);

        this.options = new Label("Options");
        options.setColor(new Color(0.0f, 0.0f, 0.0f));
        options.setScale(4.0f);
        rootContainer.addChild(options);

        this.back = new Button("Back");
        back.setButtonCallback(() -> uiManager.setCurrentScreen(new MainMenu()));
        rootContainer.addChild(back);

        this.musicVolume = new Slider();
        rootContainer.addChild(musicVolume);
    }
}
