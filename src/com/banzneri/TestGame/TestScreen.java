package com.banzneri.TestGame;

import com.banzneri.Screen;
import com.banzneri.geometry.Rect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TestScreen extends Screen {
    private ArrayList<TestBullet> bullets = new ArrayList<>();

    public TestScreen(double width, double height, TestGame host) {
        super(width, height, host);
        Rect rect = new Rect(20, 50, 100, 100);
        rect.setColor(Color.RED);
        rect.setSpeedX(1);
        rect.setRotation(20);
        TestBrick b1 = new TestBrick();
        TestBrick b2 = new TestBrick();
        b2.setX(200);
        b1.setX(200);
        b1.setY(300);
        addGameObject(rect);
        addGameObject(b1);
        addGameObject(b2);
        setBackGroundImage(new Image("/bg.jpg"));
    }

    public void addBullet(TestBullet b) {
        bullets.add(b);
        getHost().getGameObjects().add(b);
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
}
