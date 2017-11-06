package com.banzneri.graphics;

import com.banzneri.input.InputListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public class Sprite extends Rect {
    private Texture texture;
    private InputListener listener;

    public Sprite(Texture texture, Rect rect) {
        super(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setTexture(texture);
    }

    public Sprite(double x, double y, double width, double height, Texture texture) {
        super(x, y, width, height);
        setTexture(texture);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw(GraphicsContext gc) {
        //gc.fillRect(getX(), getY(), getWidth(), getHeight());
        gc.drawImage(texture.getImage(), getX(), getY(), getWidth(), getHeight());
    }

    public void addListener(InputListener listener) {

    }
}
