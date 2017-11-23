package com.banzneri.AStarTest;

import com.banzneri.Game;
import com.banzneri.Screen;
import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.Camera2D;
import com.banzneri.input.InputListener;
import com.banzneri.tileengine.TMXObject;
import com.banzneri.tileengine.TMXMap;
import com.banzneri.tileengine.TMXMapLoader;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class AStarScreen extends Screen implements InputListener {
    private static final double WIDTH = 300;
    private static final double HEIGHT = 15;

    private Vector2d startLocation = new Vector2d(0, 0);
    private ArrayList<Node> allNodes = new ArrayList<>();
    private Pathfinder pathfinder;
    private int tileSize = 32;
    private Node end = new Node(startLocation, tileSize);
    private Player player = new Player(startLocation.x, startLocation.y);
    private Camera2D camera2D = new Camera2D(getWidth(), getHeight(), this);
    private ArrayList<Node> neighbours = new ArrayList<>();

    public AStarScreen(Game host) {
        super(1200, 920, host);
        initGameObjects();
    }
    @Override
    public void update() {
        getTmxMap().get("bg").draw(getGc());
        player.draw(getGc());
        player.moveAlternative();
        if (!player.getPath().isEmpty()) {
            getGc().setStroke(Color.GOLD);
            getGc().setLineWidth(3);
            getGc().strokeOval(end.location.x + 2.5, end.location.y + 2.5, tileSize - 5, tileSize - 5);
            for(Node node : player.getPath()) {
                getGc().setFill(Color.BLACK);
                getGc().fillRect(node.location.x, node.location.y, node.size, node.size);
            }
        }
        for(Node node: allNodes) {
            getGc().setFill(Color.WHITE);
            getGc().setFont(new Font(getGc().getFont().getName(), 10));
            getGc().fillText(Double.toString(node.gValue), node.location.x, node.location.y);
            if(node.isWall) {
                getGc().fillRect(node.location.x, node.location.y, node.size, node.size);
            }
        }
        for(Node node : neighbours) {
            getGc().fillRect(node.location.x, node.location.y, node.size, node.size);
        }
        handleCamera();
    }

    private void initGameObjects() {
        player.setColor(Color.GREEN);

        TMXMap map = new TMXMapLoader().load("/a*test.tmx");
        map.get("bg").getLayerTiles().forEach(l -> {
            allNodes.add(new Node(new Vector2d(l.getX() * tileSize, l.getY() * tileSize), tileSize));
        });
        System.out.println(map.getTMXObjects());
        for(TMXObject wall : map.getTMXObjects()) {
            for(Node node : allNodes) {
                if (wall.getX() == node.location.x && wall.getY() == node.location.y) {
                    node.isWall = true;
                }
            }
        }
        pathfinder = new Pathfinder(allNodes);
        addTiledMap(map);
        //initNeighbours();
        addListener(this);
    }

    private void initNeighbours() {
        for (Node node : allNodes) {
            ArrayList<Node> neighbours = new ArrayList<>();
            if(node.location.x >= tileSize) {
                //System.out.println(getNodeByLocation(node.location.x - tileSize, node.location.y) + " left");
                neighbours.add(getNodeByLocation(node.location.x - tileSize, node.location.y));
            }
            if(node.location.x < getTmxMap().getWidth() - tileSize * 2) {
                //System.out.println(getNodeByLocation(node.location.x + tileSize, node.location.y) + " right");
                neighbours.add(getNodeByLocation(node.location.x + tileSize, node.location.y));
            }
            if(node.location.y >= tileSize) {
                //System.out.println(getNodeByLocation(node.location.x, node.location.y - tileSize) + " up");
                neighbours.add(getNodeByLocation(node.location.x, node.location.y - tileSize));
                neighbours.add(getNodeByLocation(node.location.x, node.location.y - tileSize));
            }
            if(node.location.y <= getTmxMap().getHeight() - tileSize * 2) {
                //System.out.println(getNodeByLocation(node.location.x, node.location.y + tileSize) + " down");
                neighbours.add(getNodeByLocation(node.location.x, node.location.y + tileSize));
            }
            node.neighbours = neighbours;
        }
    }

    private Node getNodeByLocation(double x, double y) {
        for(Node node : allNodes) {
            if(x == node.location.x && y == node.location.y) {
                System.out.println(node);
                return node;
            }
        }
        return null;
    }

    private void moveTo() {
        double nodeX = player.getX() - player.getX() % tileSize;
        double nodeY = player.getY() - player.getY() % tileSize;
        System.out.println("END: " + end);
        System.out.println("START: " + getNodeByLocation(nodeX, nodeY));
        ArrayList<Node> path = pathfinder.getPath(getNodeByLocation(end.location.x, end.location.y), getNodeByLocation(nodeX, nodeY));
        player.startPathfinding(path, end);
        System.out.println(path);
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        double x = camera2D.getX() + e.getX() - e.getX() % tileSize;
        double y = camera2D.getY() + e.getY() - e.getY() % tileSize;

        if (e.getButton().equals(MouseButton.PRIMARY)) {
            end = new Node(new Vector2d(x, y), tileSize);
            //System.out.println(end);
            moveTo();
        }

        if(e.getButton().equals(MouseButton.SECONDARY)) {
            neighbours = getNodeByLocation(x, y).neighbours;
        }
    }

    public void handleCamera() {
        camera2D.setX(player.getX() - getWidth() / 2);

        if(camera2D.getX() < 0) {
            camera2D.setX(0);
        }

        if(player.getX() + getWidth() / 2 > getTmxMap().getWidth()) {
            camera2D.setX(getTmxMap().getWidth() - getWidth());
        }

        /*if (player.getX() < camera2D.getX()) {
            player.setX(camera2D.getX() + 1);
        }

        if(player.getX() + player.getWidth() > getTmxMap().getWidth()) {
            player.setX(getTmxMap().getWidth() - player.getWidth());
        }
        */
    }

    @Override
    public void onKeyPressed(KeyEvent e) { }

    @Override
    public void onKeyUp(KeyEvent e) { }

    @Override
    public void onMouseMoved(MouseEvent e) { }

    @Override
    public void onMouseDragged(MouseEvent mouseEvent) { }

    @Override
    public void onMouseDragStart(MouseDragEvent mouseDragEvent) { }

    @Override
    public void onMouseDragEnd(MouseDragEvent mouseDragEvent) { }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void onMousePressed(MouseEvent mouseEvent) { }
}
