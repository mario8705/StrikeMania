package fr.alexislavaud.strikemania.engine;

import org.lwjgl.stb.STBImage;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Alexis Lavaud on 18/12/2016.
 */
public final class AssetManager {
    private Map<String, IReferenceCounter> loadedAssets;

    public AssetManager() {
        this.loadedAssets = new HashMap<>();
    }

    public Texture loadTexture(String filename) throws IOException {
        return loadTexture(filename, TextureFilter.Bilinear);
    }

    public Texture loadTexture(String filename, TextureFilter textureFilter) throws IOException {
        IReferenceCounter refCounter = null;

        if ((refCounter = loadedAssets.get(filename)) != null) {
            try {
                Texture texture = (Texture) refCounter;
                texture.addReference();

                return texture;
            }
            catch (ClassCastException e) {
                return null;
            }
        }
        else {
            int[] width = new int[1];
            int[] height = new int[1];
            int[] bpp = new int[1];

            ByteBuffer pixels = STBImage.stbi_load(filename, width, height, bpp, 0);

            if (pixels == null) {
                return null;
            }

            Texture texture = new Texture(pixels, width[0], height[0], bpp[0], textureFilter);
            loadedAssets.put(filename, texture);

            System.out.println("Texture loaded: " + filename);

            return texture;
        }
    }

    public void collectGarbage() {
        Set<String> keySet = loadedAssets.keySet();
        Iterator<String> it = keySet.iterator();

        int deletedAssets = 0;

        while (it.hasNext()) {
            String key = it.next();
            IReferenceCounter refCounter = loadedAssets.get(key);

            if (refCounter.getRefCount() <= 0) {
                refCounter.destroy();
                it.remove();

                deletedAssets++;
            }
        }

        if (deletedAssets > 0) {
            System.out.println("Deleted assets: " + deletedAssets);
        }
    }

    public void destroy() {
        Collection<IReferenceCounter> values = loadedAssets.values();

        for (IReferenceCounter referenceCounter : values) {
            referenceCounter.destroy();
        }

        loadedAssets.clear();
    }
}
