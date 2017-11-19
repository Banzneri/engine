package com.banzneri.tileengine;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Layer {

    public String name;
    public int width;
    public int height;
    public LayerData layerData;
    public long firstNonZeroGid;
    public ArrayList<Tile> layerTiles = new ArrayList<>();
    public boolean isVisible;

    public long get(int x, int y) {
        return layerData.values[x + y * width];
    }

    public void set(int x, int y, long gid) {
        layerData.values[x + y * width] = gid;
    }

    public void setFirstNonZeroGid() {
        int di = 0;
        do {
            firstNonZeroGid = layerData.values[di++];
        }
        while (firstNonZeroGid == 0);
    }

    public ArrayList<Tile> getLayerTiles() {
        return layerTiles;
    }

    public void setLayerTiles(ArrayList<Tile> layerTiles) {
        this.layerTiles = layerTiles;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        for(Tile tile : layerTiles) {
            tile.setVisible(visible);
        }
    }

    public void draw(GraphicsContext gc) {
        layerTiles.forEach(e -> e.draw(gc));
    }

    @Override
    public String toString() {
        return "Layer{" + "name=" + name + ", width=" + width + ", height=" + height + ", layerData=" + layerData + '}';
    }
}
