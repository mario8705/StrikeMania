package fr.alexislavaud.strikemania.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import java.nio.ShortBuffer;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class Music {
    private long handle;
    private final int bufferSize;
    private final int channels, format, sampleRate;
    private boolean isLooping;
    private int sourceID;
    private int[] buffers;
    private ShortBuffer samplesBuffer;
    private boolean isPlaying;

    private Music(long handle, int channels, int sampleRate) {
        this.handle = handle;
        this.buffers = new int[3];

        this.sourceID = AL10.alGenSources();
        AL10.alGenBuffers(buffers);

        this.bufferSize = sampleRate * channels;
        this.channels = channels;
        this.format = (channels == 2) ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
        this.sampleRate = sampleRate;
        this.samplesBuffer = BufferUtils.createShortBuffer(bufferSize);
        this.isPlaying = false;

        for (int bufferID : buffers) {
            fillBuffer(bufferID);
            AL10.alSourceQueueBuffers(sourceID, bufferID);
        }
    }

    public void update() {
        if (AL10.alIsSource(sourceID)) {
            int processed = AL10.alGetSourcei(sourceID, AL10.AL_BUFFERS_PROCESSED);

            while (processed-- != 0) {
                int bufferID = AL10.alSourceUnqueueBuffers(sourceID);
                fillBuffer(bufferID);
                AL10.alSourceQueueBuffers(sourceID, bufferID);
            }

            int actualSourceState = AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE);

            if (isPlaying && actualSourceState != AL10.AL_PLAYING) {
                AL10.alSourcePlay(sourceID);
            }
        }
    }

    private void fillBuffer(int bufferID) {
        samplesBuffer.clear();
        int n = STBVorbis.stb_vorbis_get_samples_short_interleaved(handle, 2, samplesBuffer);
        samplesBuffer.flip();

        if (n <= 0) {
            if (isLooping) {
                STBVorbis.stb_vorbis_seek_start(handle);

                // Temporary disable <i>isLooping</i> to avoid stack overflow

                isLooping = false;
                fillBuffer(bufferID);
                isLooping = true;
            }
            else {
                this.isPlaying = false;
                AL10.alSourceStop(sourceID);
                STBVorbis.stb_vorbis_seek_start(handle);
            }
        }
        else {
            samplesBuffer.limit(n * channels);
            AL10.alBufferData(bufferID, format, samplesBuffer, sampleRate);
        }
    }

    public void togglePlayPause() {
        if (isPlaying) {
            AL10.alSourcePause(sourceID);
        }
        else {
            AL10.alSourcePlay(sourceID);
        }

        isPlaying = !isPlaying;
    }

    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void destroy() {
        if (AL10.alIsSource(sourceID)) {
            AL10.alSourceStop(sourceID);
            AL10.alDeleteSources(sourceID);
        }

        for (int bufferID : buffers) {
            if (AL10.alIsBuffer(bufferID)) {
                AL10.alDeleteBuffers(bufferID);
            }
        }

        if (handle != MemoryUtil.NULL) {
            STBVorbis.stb_vorbis_close(handle);
        }

        this.sourceID = 0;
        buffers[0] = buffers[1] = buffers[2] = 0;
        this.handle = 0;
    }

    // TODO: move this in AssetManager
    public static Music loadMusic(String filename) {
        int[] error = new int[1];
        long handle = STBVorbis.stb_vorbis_open_filename(filename, error, null);

        if (handle == MemoryUtil.NULL) {
            return null;
        }

        int channels, sampleRate;

        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            STBVorbis.stb_vorbis_get_info(handle, info);

            channels = info.channels();
            sampleRate = info.sample_rate();
        }

        return new Music(handle, channels, sampleRate);
    }
}
