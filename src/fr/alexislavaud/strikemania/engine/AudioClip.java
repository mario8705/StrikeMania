package fr.alexislavaud.strikemania.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

import java.io.InputStream;
import java.nio.ShortBuffer;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class AudioClip {
    private final int samplesCount;
    private int bufferID;

    public AudioClip(int channels, int samplesCount, int sampleRate, ShortBuffer samples) {
        this.samplesCount = samplesCount;
        this.bufferID = AL10.alGenBuffers();
        AL10.alBufferData(bufferID, (channels == 2) ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16, samples, sampleRate);
    }

    public int getSamplesCount() {
        return samplesCount;
    }

    public int getBufferID() {
        return bufferID;
    }

    public void destroy() {
        if (AL10.alIsBuffer(bufferID)) {
            AL10.alDeleteBuffers(bufferID);
            this.bufferID = 0;
        }
    }

    public static AudioClip loadAudioClip(AssetManager assetManager, String filename) {
        int[] error = new int[1];
        long handle = STBVorbis.stb_vorbis_open_filename(filename, error, null);

        if (handle == MemoryUtil.NULL) {
            return null;
        }

        STBVorbisInfo info = STBVorbisInfo.malloc();
        STBVorbis.stb_vorbis_get_info(handle, info);

        ShortBuffer samples = BufferUtils.createShortBuffer(info.channels() * STBVorbis.stb_vorbis_stream_length_in_samples(handle));
        STBVorbis.stb_vorbis_get_samples_short_interleaved(handle, info.channels(), samples);

        AudioClip audioClip = new AudioClip(info.channels(),
                STBVorbis.stb_vorbis_stream_length_in_samples(handle),
                info.sample_rate(), samples);

        info.free();
        STBVorbis.stb_vorbis_close(handle);

        return audioClip;
    }
}
