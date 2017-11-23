package com.banzneri.tileengine;

import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A Tile class. Tile objects are given to specific layers, and they represent the rectangular tiles
 * to be drawn on the canvas. Each tile has an Image, which points to the tile set image used in the Tile map.
 * They have coordinates for where they are to be drawn on the screen, and coordinates which point to a specific
 * region in the tile set image.
 */
public class Tile extends Sprite {
    private Image image;
    private long id;
    private double realX;
    private double realY;
    private TMXMap map;
    private String name;

    /**
     * Constructor for the Tile class.
     *
     * @param image The tile set image
     * @param id The id of this tile
     * @param x The x coordinate of this tile
     * @param y The y coordinate of this tile
     * @param width The width of this tile
     * @param height The height of this tile
     * @param realX The x coordinate in the tileset image
     * @param realY The y coordinate in the tileset image
     * @param map The TMXMap object, which hosts this tile
     */
    public Tile(Image image, long id, double x, double y, double width, double height, double realX, double realY, TMXMap map) {
        super(x, y, width, height, new Texture(image));
        setImage(image);
        setId(id);
        this.realX = realX;
        this.realY = realY;
        this.map = map;
    }

    /**
     * Draws the tile on the canvas. Draws a specific region from tileset image to a specific location on the screen.
     * Handles the offset if the TMXMap has been moved.
     *
     * @param gc The canvas GraphicsContext object
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, realX, realY, getWidth(), getHeight(), map.getX() + getX() * getWidth(), map.getY() + getY() * getHeight(), getWidth(), getHeight());
    }

    // GETTERS & SETTERS

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
}
