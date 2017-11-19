package com.banzneri.TestGame;

import com.banzneri.Screen;
import com.banzneri.animations.RotationAnimation;
import com.banzneri.geometry.Rect;
import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.GameObject;
import com.banzneri.particles.Emitter;
import com.banzneri.tileengine.TMXMapLoader;
import com.banzneri.tileengine.Tile;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
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
        getGc().clearRect(0, 0, getWidth(), getHeight());
        getGameObjects().forEach(e -> e.draw(getGc()));
        getGameObjects().forEach(e -> e.move(getDelta()));
    }

    public void addBullet(TestBullet b) {
        bullets.add(b);
        getHost().getGameObjects().add(b);
    }

    public void initGameObjects() {
        Emitter emitter = new Emitter(new Vector2d(0, getHeight()-60), getWidth(), this);
        emitter.emit();
        player = new TestPlayer(this);
        Rect rect = new Rect(100, 50, 100, 100);
        rect.setColor(Color.RED);
        rect.setSpeedX(1);
        rect.setPivotX(rect.getWidth()/2);
        rect.setPivotY(rect.getHeight()/2);
        rect.setRotation(20);
        TestBrick b1 = new TestBrick();
        TestBrick b2 = new TestBrick();
        b2.setX(500);
        b2.setY(30);
        b1.setX(500);
        b1.setY(400);
        b2.setPivotX(b2.getWidth()/2);
        b2.setPivotY(b2.getHeight()/2);
        b2.setRotation(45);
        RotationAnimation anim = new RotationAnimation(20, 360, b2);
        anim.start();
        RotationAnimation animation = new RotationAnimation(20, 360, rect);
        animation.start();
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
        handleBullets();
        handleWallHit();
        main : for(GameObject o1: getGameObjects()) {
            for (GameObject o2 : getGameObjects()) {
                if(o1.collidesWith(o2) && !o1.equals(o2)) {
                    swapVelocities(o1, o2);
                    break main;
                }
            }
        }
    }

    public void swapVelocities(GameObject o1, GameObject o2) {
        if(o1.equals(player) && o2 instanceof TestBullet || (o1 instanceof TestBullet) && o2.equals(player))
            return;
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
        getGameObjects().forEach(o -> {
            double x1 = o.getMinX();
            double x2 = o.getMaxX();
            double y1 = o.getMinY();
            double y2 = o.getMaxY();

            if(x1 < 0) {
                o.setX(0 - x1);
                o.setSpeedX(-o.getSpeedX());
            }
            else if(x2 > getWidth()) {
                o.setX(getWidth() - o.getWidth() - (x2 - getWidth()));
                o.setSpeedX(-o.getSpeedX());
            }
            else if(y1 < 0) {
                o.setY(0 - y1);
                o.setSpeedY(-o.getSpeedY());
            } else if(y2 > getHeight()) {
                o.setY(getHeight() - o.getHeight()  - (y2 - getHeight()));
                o.setSpeedY(-o.getSpeedY());
            }
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
