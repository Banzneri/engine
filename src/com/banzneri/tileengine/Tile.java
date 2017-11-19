package com.banzneri.tileengine;

import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile extends Sprite {
    private Image image;
    private long id;
    private double realX;
    private double realY;
    private TMXMap map;
    private String name;

    public Tile(Image image, long id, double x, double y, double width, double height, double realX, double realY, TMXMap map) {
        super(x, y, width, height, new Texture(image));
        setImage(image);
        setId(id);
        this.realX = realX;
        this.realY = realY;
        this.map = map;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, realX, realY, getWidth(), getHeight(), map.getX() + getX() * getWidth(), map.getY() + getY() * getHeight(), getWidth(), getHeight());
    }
}
