package fr.alexislavaud.strikemania.engine;

import org.lwjgl.openal.AL10;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class AudioEmitter {
    private int sourceID;
    private float volume;

    public AudioEmitter() {
        this.sourceID = AL10.alGenSources();
        this.volume = 1.0f;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
