package com.banzneri.tileengine;

import javafx.scene.image.Image;

import java.util.List;
import java.util.NavigableMap;

/**
 * The class for the Tiled map. TMXMap is created using TMXMapLoader. It parses a .tmx file, which is xml.
 * Has all the layers, tile sets and objects of the map. Also holds info about the size of the map, the orientation,
 * and tile width and height in pixels.
 */
public class TMXMap {
    private List<Layer> layers;
    private NavigableMap<Long, TileSet> tileSets;
    private List<TMXObject> TMXObjects;
    private String orientation;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private double x = 0;
    private double y = 0;

    /**
     * The constructor for TMXMap.
     *
     * @param layers All the drawing layers of the map.
     * @param tileSets All the tilesets used in the map.
     * @param TMXObjects All objects in the map.
     */
    public TMXMap(List<Layer> layers, NavigableMap<Long, TileSet> tileSets, List<TMXObject> TMXObjects) {
        setLayers(layers);
        setTileSets(tileSets);
        setTMXObjects(TMXObjects);
    }

    /**
     * Initiates Tile objects for all the layers. Makes them the right size, puts them in the right location (x, y),
     * and links them with the coordinates of the Tile graphics in the tile set, so the correct portion of the tile set
     * image is drawn. Also offsets the TMXObjects by x and y location of the TMXMap, if the map has been moved.
     */
    public void initTiles() {
        // The tile set image. This is given as a parameter to all the Tile objects, so they can draw from this.
        Image tilesetImage = new Image(getTileSetImageSourceById(1));

        // Iterates through all the layers, and creates Tile objects accordingly for all of them
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

        // If the map has been moved, set the TMXObjects' coordinates to correctly offset this.
        if(x != 0 && y != 0) {
            getTMXObjects().forEach(e -> {
                e.setX(x + e.getX());
                e.setY(y + e.getY());
            });
        }
    }

    /**
     * Returns a Layer object of a given name
     *
     * @param name The name of the layer. This is name given in Tiled Map editor.
     * @return The layer of the given name.
     */
    public Layer get(String name) {
        for(Layer layer : layers) {
            if(layer.name.equals(name)) {
                return layer;
            }
        }
        return null;
    }

    // GETTERS & SETTERS

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

    public List<TMXObject> getTMXObjects() {
        return TMXObjects;
    }

    public void setTMXObjects(List<TMXObject> TMXObjects) {
        this.TMXObjects = TMXObjects;
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
        return height * tileHeight;
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
