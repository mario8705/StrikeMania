package fr.alexislavaud.strikemania.engine;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class Texture implements IReferenceCounter {
    private int refCount;
    private int textureID;
    private final int width, height;
    private TextureFilter textureFilter;

    public Texture(ByteBuffer pixels, int width, int height, int bpp, TextureFilter textureFilter) {
        this.refCount = 1;
        this.width = width;
        this.height = height;
        this.textureFilter = textureFilter;

        int[] oldTextureID = new int[1];
        GL11.glGetIntegerv(GL11.GL_TEXTURE_BINDING_2D, oldTextureID);

        int format;
        int internalFormat;

        switch (bpp) {
            case 4:
                internalFormat = GL11.GL_RGBA;
                format = GL11.GL_RGBA;
                break;

            case 3:
                internalFormat = GL11.GL_RGB;
                format = GL11.GL_RGB;
                break;

            case 2:
                internalFormat = GL11.GL_RGB;
                format = GL11.GL_RGB16;
                break;

            case 1:
                internalFormat = GL11.GL_RGB;
                format = GL11.GL_LUMINANCE;
                break;

            default:
                throw new IllegalArgumentException("unknown image format");
        }

        int filter = (textureFilter == TextureFilter.Bilinear) ? GL11.GL_LINEAR : GL11.GL_NEAREST;

        this.textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, pixels);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, oldTextureID[0]);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }

    @Override
    public void addReference() {
        refCount++;
    }

    @Override
    public void releaseReference() {
        refCount--;
    }

    @Override
    public int getRefCount() {
        return refCount;
    }

    @Override
    public void destroy() {
        if (GL11.glIsTexture(textureID)) {
            GL11.glDeleteTextures(textureID);
            this.textureID = 0;
        }
    }
}
