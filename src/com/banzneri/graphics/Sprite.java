package com.banzneri.graphics;

import com.banzneri.geometry.Rect;
import javafx.scene.canvas.GraphicsContext;

public class Sprite extends GameObject {
    private Texture texture;

    public Sprite(Texture texture, Rect rect) {
        setX(rect.getX());
        setY(rect.getY());
        setWidth(rect.getWidth());
        setHeight(rect.getHeight());
        setTexture(texture);
    }

    public Sprite(double x, double y, double width, double height, Texture texture) {
        setX(x);
        setX(y);
        setWidth(width);
        setHeight(height);
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
        //gc.drawImage(texture.getImage(), getX(), getY(), getWidth(), getHeight());
    }
}
