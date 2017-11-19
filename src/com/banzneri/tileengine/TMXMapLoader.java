package com.banzneri.tileengine;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.banzneri.geometry.Rect;
import com.banzneri.graphics.GameObject;

import javafx.scene.paint.Color;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses a .tmx file
 */
public class TMXMapLoader {

    private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private List<Layer> layers = new ArrayList<>();
    private NavigableMap<Long, TileSet> tileSets = new TreeMap<>();
    private List<Object> objects = new ArrayList<>();
    private String orientation;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private TMXMap tmxMap;

    public TMXMap load(String mapName) {
        clear();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(getClass().getResourceAsStream(mapName));
            Element map = document.getDocumentElement();

            initAttributes(map);
            loadTileSets(map);
            loadLayers(map);
            loadObjects(map);
            initTMXMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmxMap;
    }

    private void initAttributes(Element map) {
        orientation = map.getAttributes().getNamedItem("orientation").getNodeValue();
        width = Integer.parseInt(map.getAttributes().getNamedItem("width").getNodeValue());
        height = Integer.parseInt(map.getAttributes().getNamedItem("height").getNodeValue());
        tileWidth = Integer.parseInt(map.getAttributes().getNamedItem("tilewidth").getNodeValue());
        tileHeight = Integer.parseInt(map.getAttributes().getNamedItem("tileheight").getNodeValue());
    }

    private void loadTileSets(Element map) {
        NodeList tilesetNodes = map.getElementsByTagName("tileset");

        for (int i = 0; i < tilesetNodes.getLength(); i++) {
            Node tileset = tilesetNodes.item(i);
            TileSet tileSetObj = new TileSet();
            tileSetObj.firstgid = Long.parseLong(tileset.getAttributes().getNamedItem("firstgid").getNodeValue());
            tileSetObj.name = tileset.getAttributes().getNamedItem("name").getNodeValue();
            tileSetObj.tilewidth = Integer.parseInt(tileset.getAttributes().getNamedItem("tilewidth").getNodeValue());
            tileSetObj.tileheight = Integer.parseInt(tileset.getAttributes().getNamedItem("tileheight").getNodeValue());

            if (tileset.getAttributes().getNamedItem("spacing") != null) {
                tileSetObj.spacing = Integer.parseInt(tileset.getAttributes().getNamedItem("spacing").getNodeValue());
            }

            NodeList images = ((Element) tileset).getElementsByTagName("image");
            Node image = images.item(0);
            Image imageObj = new Image();
            imageObj.source = image.getAttributes().getNamedItem("source").getNodeValue();
            imageObj.width = Integer.parseInt(image.getAttributes().getNamedItem("width").getNodeValue());
            imageObj.height = Integer.parseInt(image.getAttributes().getNamedItem("height").getNodeValue());
            imageObj.cols = imageObj.width / tileSetObj.tilewidth;
            imageObj.rows = imageObj.height / tileSetObj.tileheight;
            tileSetObj.image = imageObj;
            tileSets.put(tileSetObj.firstgid, tileSetObj);
        }
    }

    private void loadLayers(Element map) {
        NodeList layerNodes = map.getElementsByTagName("layer");

        for (int i = 0; i < layerNodes.getLength(); i++) {
            Node layer = layerNodes.item(i);
            Layer layerObj = new Layer();
            layerObj.name = layer.getAttributes().getNamedItem("name").getNodeValue();
            layerObj.width = Integer.parseInt(layer.getAttributes().getNamedItem("width").getNodeValue());
            layerObj.height = Integer.parseInt(layer.getAttributes().getNamedItem("height").getNodeValue());

            NodeList datas = ((Element) layer).getElementsByTagName("data");
            Node data = datas.item(0);
            LayerData layerDataObj = new LayerData();
            layerDataObj.encoding = data.getAttributes().getNamedItem("encoding").getNodeValue();
            layerDataObj.values = getDataValues(data.getTextContent());
            layerObj.layerData = layerDataObj;
            layerObj.setFirstNonZeroGid();
            layers.add(layerObj);
        }
    }

    private void loadObjects(Element map) {
        NodeList objectNodes = map.getElementsByTagName("object");

        for (int i = 0; i < objectNodes.getLength(); i++) {
            Node object = objectNodes.item(i);
            int x = Integer.parseInt(object.getAttributes().getNamedItem("x").getNodeValue());
            int y = Integer.parseInt(object.getAttributes().getNamedItem("y").getNodeValue());
            int width = Integer.parseInt(object.getAttributes().getNamedItem("width").getNodeValue());
            int height = Integer.parseInt(object.getAttributes().getNamedItem("height").getNodeValue());
            String name = object.getAttributes().getNamedItem("name").getNodeValue();
            Object tile = new Object(x, y, width, height, name);
            objects.add(tile);
        }
    }

    private void initTMXMap() {
        tmxMap = new TMXMap(layers, tileSets, objects);
        tmxMap.setWidth(width);
        tmxMap.setHeight(height);
        tmxMap.setOrientation(orientation);
        tmxMap.setTileWidth(tileWidth);
        tmxMap.setTileHeight(tileHeight);
        tmxMap.initTiles();
    }

    public void clear() {
        layers.clear();
        tileSets.clear();
    }

    private static long[] getDataValues(String values) {
        String[] valuesArray = values.split(",");
        long[] ret = new long[valuesArray.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Long.parseLong(valuesArray[i].trim());
        }
        return ret;
    }
}

