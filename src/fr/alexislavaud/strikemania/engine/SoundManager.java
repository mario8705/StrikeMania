package fr.alexislavaud.strikemania.engine;

import org.lwjgl.openal.*;

import java.util.*;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class SoundManager {
    private long deviceID, contextID;
    private Map<AudioClip, Integer> playingClips;

    public SoundManager() {
        this.deviceID = ALC10.alcOpenDevice((CharSequence) null);
        this.contextID = ALC10.alcCreateContext(deviceID, (int[]) null);
        ALC10.alcMakeContextCurrent(contextID);
        ALCCapabilities deviceCaps = ALC.createCapabilities(deviceID);
        AL.createCapabilities(deviceCaps);

        this.playingClips = new HashMap<>();
    }

    public void update() {
        Collection<Integer> sourcesID = playingClips.values();
        Iterator<Integer> it = sourcesID.iterator();

        while (it.hasNext()) {
            int sourceID = it.next().intValue();

            if (AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) != AL10.AL_PLAYING) {
                AL10.alDeleteSources(sourceID);
                it.remove();
            }
        }
    }

    private int createSourceFromAudioClip(AudioClip audioClip) {
        int sourceID = AL10.alGenSources();
        AL10.alSourcei(sourceID, AL10.AL_BUFFER, audioClip.getBufferID());

        return sourceID;
    }

    public void playOneShot(AudioClip audioClip) {
        int sourceID = createSourceFromAudioClip(audioClip);
        AL10.alSourcePlay(sourceID);

        playingClips.put(null, sourceID);
    }

    public void playOnce(AudioClip audioClip) {
        Set<Map.Entry<AudioClip, Integer>> entrySet = playingClips.entrySet();
        Iterator<Map.Entry<AudioClip, Integer>> it = entrySet.iterator();

        while (it.hasNext()) {
            Map.Entry<AudioClip, Integer> entry = it.next();

            if (entry.getKey() != null && entry.getKey() == audioClip) {
                int sourceID = entry.getValue().intValue();

                AL10.alSourceStop(sourceID);
                AL10.alDeleteSources(sourceID);
                it.remove();

                break;
            }
        }

        int sourceID = createSourceFromAudioClip(audioClip);
        AL10.alSourcePlay(sourceID);

        playingClips.put(audioClip, sourceID);
    }

    public void destroy() {
        ALC10.alcMakeContextCurrent(0L);
        ALC10.alcDestroyContext(contextID);
        ALC10.alcCloseDevice(deviceID);
    }
}
