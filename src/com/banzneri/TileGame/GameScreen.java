package com.banzneri.TileGame;

import com.banzneri.Screen;
import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.GameObject;
import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;
import com.banzneri.particles.Emitter;
import com.banzneri.tileengine.TMXMap;
import com.banzneri.tileengine.TMXMapLoader;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GameScreen extends Screen {
    private ArrayList<GameObject> collideObjects = new ArrayList<>();
    private GameObject player;
    private Emitter emitter;

    public GameScreen(double width, double height, TileGame host) {
        super(width, height, host);
        initTiledMap();
        initGameObjects();
    }

    @Override
    public void update() {
        getGc().clearRect(0, 0, getWidth(), getHeight());
        //getGameObjects().forEach(e -> e.move(getDelta()));
        getTmxMap().get("ground").draw(getGc());
        player.draw(getGc());
        player.move(getDelta());
        getTmxMap().get("foreground1").draw(getGc());
        getTmxMap().get("foreground2").draw(getGc());
        checkCollision();
    }

    private void initGameObjects() {
        player = new Sprite(0, 0, 32, 32, new Texture("/player.png"));
        addListener(new MyListener(player));
        addGameObject(player);
        for(GameObject gameObject : getGameObjects()) {
            if(gameObject.isCollides()) {
                collideObjects.add(gameObject);
            }
        }
        emitter = new Emitter(new Vector2d(0, 0), getWidth(), this);
        //emitter.emit();
    }

    private void initTiledMap() {
        TMXMapLoader loader = new TMXMapLoader();
        TMXMap map = loader.load("/test.tmx");
        addTiledMap(map);
    }

    public void checkCollision() {
        for(GameObject gameObject : collideObjects) {
            if(!player.equals(gameObject)) {
                playerCollision(gameObject);
            }
        }
    }

    public void handleCollision(GameObject o1, GameObject o2) {
        double previousX1 = o1.getX() - o1.getSpeedX();
        double previousY1 = o1.getY() - o1.getSpeedY();
        double previousX2 = o2.getX() - o2.getSpeedX();
        double previousY2 = o2.getY() - o2.getSpeedY();

        if(o1.getSpeedX() != 0 && (isLeft(o1, o2) || isRight(o1, o2))) {
            o1.setX(previousX1);
        }
        if( o1.getSpeedY() != 0 && ( isDown(o1, o2) || isUp(o1, o2) )) {
            o1.setY(previousY1);
        }
        if( o2.getSpeedX() != 0 && ( isLeft(o2, o1) || isRight(o2, o1) )) {
            o2.setX(previousX2);
        }
        if( o2.getSpeedY() != 0 && ( isDown(o2, o1) || isUp(o2, o1) )) {
            o2.setY(previousY2);
        }
    }

    public void playerCollision(GameObject o2) {
        if( isLeft(player, o2) || isRight(player, o2) ) {
            player.setX(player.getX() - player.getSpeedX());
            //player.setSpeedX(0);
            System.out.println("horixontal");
        }
        if( isDown(player, o2) || isUp(player, o2) ) {
            player.setY(player.getY() - player.getSpeedY());
            //player.setSpeedY(0);
            System.out.println("vertical");
        }
    }

    public boolean isLeft(GameObject player, GameObject object) {
        double x = player.getMinX() - Math.abs(player.getSpeedX() * getDelta());
        return object.contains(x, player.getMinY()) || object.contains(x, player.getMaxY());
    }

    public boolean isRight(GameObject player, GameObject object) {
        double x = player.getMaxX() + Math.abs(player.getSpeedX() * getDelta());
        return object.contains(x, player.getMinY()) || object.contains(x, player.getMaxY());
    }

    public boolean isUp(GameObject player, GameObject object) {
        double y = player.getMinY() - Math.abs(player.getSpeedY() * getDelta());
        return object.contains(player.getMinX(), y) || object.contains(player.getMaxX(), y);
    }

    public boolean isDown(GameObject player, GameObject object) {
        double y = player.getMaxY() + Math.abs(player.getSpeedY() * getDelta());
        return object.contains(player.getMinX(), y) || object.contains(player.getMaxX(), y);
    }
}
