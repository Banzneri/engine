package com.banzneri.tileengine;

public class TileSet {
    public int[] tileRet = new int[4];
    public long firstgid;
    public String name;
    public int tilewidth;
    public int tileheight;
    public int spacing;
    public Image image;

    public int[] get(long gid) {
        long i = gid - firstgid;
        int x = (int) (i % image.cols);
        int y = (int) (i / image.cols);
        tileRet[0] = x * (tilewidth + spacing); // x1
        tileRet[1] = y * (tileheight + spacing); // y1
        tileRet[2] = tileRet[0] + tilewidth; // x2
        tileRet[3] = tileRet[1] + tileheight; // y2
        return tileRet;
    }

    @Override
    public String toString() {
        return "TileSet{" + "firstgid=" + firstgid + ", name=" + name + ", tilewidth=" + tilewidth + ", tileheight=" + tileheight + ", image=" + image + '}';
    }
}
