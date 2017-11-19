package com.banzneri.tileengine;

import com.banzneri.graphics.GameObject;
import javafx.scene.image.Image;

import java.util.List;
import java.util.NavigableMap;

public class TMXMap {
    private List<Layer> layers;
    private NavigableMap<Long, TileSet> tileSets;
    private List<Object> objects;
    private String orientation;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private double x = 0;
    private double y = 0;

    public TMXMap(List<Layer> layers, NavigableMap<Long, TileSet> tileSets, List<Object> objects) {
        setLayers(layers);
        setTileSets(tileSets);
        setObjects(objects);
    }

    public void initTiles() {
        Image tilesetImage = new Image(getTileSetImageSourceById(1));

        getLayers().forEach(layer -> {
            for(int y = 0; y < layer.height; y++) {
                for(int x = 0; x < layer.width; x++) {
                    long gid = layer.get(x, y);
                    double tileSetX = getTileSetById(1).get(gid)[0]; // gets the x coordinate in the tileset image
                    double tileSetY = getTileSetById(1).get(gid)[1]; // gets the y coordinate in the tileset image
                    Tile tile = new Tile(tilesetImage, gid, x, y, getTileWidth(), getTileHeight(), tileSetX, tileSetY, this);
                    tile.setCollides(false);
                    if(gid != 0) {
                        layer.getLayerTiles().add(tile);
                    }
                }
            }
        });

        getObjects().forEach(e -> {
            e.setX(x + e.getX());
            e.setY(y + e.getY());
        });
    }

    public Layer get(String name) {
        for(Layer layer : layers) {
            if(layer.name.equals(name)) {
                return layer;
            }
        }
        return null;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public NavigableMap<Long, TileSet> getTileSets() {
        return tileSets;
    }

    public void setTileSets(NavigableMap<Long, TileSet> tileSets) {
        this.tileSets = tileSets;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public String getTileSetImageSourceById(long id) {
        return tileSets.floorEntry(id).getValue().image.source;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return width * tileWidth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public TileSet getTileSetById(long id) {
        return tileSets.floorEntry(id).getValue();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
