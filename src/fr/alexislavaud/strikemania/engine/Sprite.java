package fr.alexislavaud.strikemania.engine;

/**
 * Created by Alexis Lavaud on 19/12/2016.
 */
public final class Sprite {
    private Texture texture;
    private Vector2f position;
    private Vector2f size;

    public Sprite() {
        this.texture = null;
        this.position = new Vector2f();
        this.size = new Vector2f();
    }

    public Sprite(Texture texture) {
        this.texture = texture;
        this.position = new Vector2f();
        this.size = new Vector2f(texture.getWidth(), texture.getHeight());

        texture.addReference();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.releaseReference();
        }

        this.texture = texture;

        if (texture != null) {
            texture.addReference();
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }
}
