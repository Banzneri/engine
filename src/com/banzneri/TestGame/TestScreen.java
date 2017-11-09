package com.banzneri.TestGame;

import com.banzneri.Game;
import com.banzneri.Screen;
import com.banzneri.animations.RotationAnimation;
import com.banzneri.geometry.Rect;
import com.banzneri.graphics.GameObject;
import com.banzneri.particles.Emitter;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class TestScreen extends Screen {
    private ArrayList<TestBullet> bullets = new ArrayList<>();
    private TestPlayer player;

    public TestScreen(double width, double height, TestGame host) {
        super(width, height, host);
        initGameObjects();
        addListener(new TestInputListener(player, (TestGame) getHost()));
    }

    @Override
    public void update() {
        checkCollision();
    }

    public void addBullet(TestBullet b) {
        bullets.add(b);
        getHost().getGameObjects().add(b);
    }

    public void initGameObjects() {
        player = new TestPlayer(this);
        Rect rect = new Rect(20, 50, 100, 100);
        rect.setColor(Color.RED);
        rect.setSpeedX(1);
        rect.setRotation(20);
        TestBrick b1 = new TestBrick();
        TestBrick b2 = new TestBrick();
        b2.setX(200);
        b1.setX(200);
        b1.setY(400);
        /*Emitter emitter = new Emitter(new Point2D(0, getHeight()-60), getWidth(), this);
        emitter.emit();*/
        b2.setRotation(45);
        RotationAnimation anim = new RotationAnimation(10, 1000, b2);
        anim.start();
        addGameObject(rect);
        addGameObject(b1);
        addGameObject(b2);
        addGameObject(player);
    }

    public void removeBullet(TestBullet b) {
        bullets.remove(b);
    }

    public ArrayList<TestBullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<TestBullet> bullets) {
        this.bullets = bullets;
    }

    private void checkCollision() {
        ArrayList<GameObject> collided = new ArrayList<>();
        main : for(GameObject o1: getGameObjects()) {
            for (GameObject o2 : getGameObjects()) {
                if(o1.collidesWith(o2) && !o1.equals(o2)) {
                    swapVelocities(o1, o2);
                    break main;
                }
            }
        }
        handleWallHit();
        handleBullets();
    }

    public void swapVelocities(GameObject o1, GameObject o2) {
        double o1VelocityX = o1.getSpeedX();
        double o1VelocityY = o1.getSpeedY();
        double o2VelocityX = o2.getSpeedX();
        double o2VelocityY = o2.getSpeedY();

        o1.setX(o1.getX() + o2VelocityX);
        o1.setSpeedX(o2VelocityX);
        o1.setY(o1.getY() + o2VelocityY);
        o1.setSpeedY(o2VelocityY);
        o2.setX(o2.getX() + o1VelocityX);
        o2.setSpeedX(o1VelocityX);
        o2.setY(o2.getY() + o1VelocityY);
        o2.setSpeedY(o1VelocityY);
    }

    public boolean isHit(TestBullet bullet, GameObject object) {
        boolean collides = bullet.collidesWith(object);
        boolean notPlayer = !object.equals(player);
        boolean notBullet = !object.equals(bullet);
        return collides && notPlayer && notBullet;
    }

    public boolean isWallHitHorizontal(GameObject o) {
        double x1 = o.getRectangle().getBoundsInParent().getMinX();
        double x2 = o.getRectangle().getBoundsInParent().getMaxX();
        return x1 < 0 || x2 > getWidth();
    }

    public boolean isWallHitVertical(GameObject o) {
        double y1 = o.getRectangle().getBoundsInParent().getMinY();
        double y2 = o.getRectangle().getBoundsInParent().getMaxY();
        return y1 < 0 || y2 > getHeight();
    }

    public boolean isOutOfBounds(TestBullet bullet) {
        return isWallHitVertical(bullet) || isWallHitHorizontal(bullet);
    }

    public void handleWallHit() {
        getGameObjects().forEach(object -> {
            if(isWallHitHorizontal(object)) object.setSpeedX(-object.getSpeedX());
            if(isWallHitVertical(object)) object.setSpeedY(-object.getSpeedY());
        });
    }

    public void handleBullets() {
        ArrayList<TestBullet> bullets = new ArrayList<>();
        ArrayList<GameObject> shotObjects = new ArrayList<>();
        getBullets().forEach(bullet -> getGameObjects().forEach(object -> {
            if(isHit(bullet, object)) {
                shotObjects.add(object);
                shotObjects.add(bullet);
            }
            if(isOutOfBounds(bullet)) {
                bullets.add(bullet);
                shotObjects.add(bullet);
            }
        }));
        removeGameObjects(shotObjects);
        getBullets().removeAll(bullets);
        removeGameObjects(shotObjects);
    }
}
