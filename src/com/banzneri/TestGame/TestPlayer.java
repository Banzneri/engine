package com.banzneri.TestGame;

import com.banzneri.geometry.Rect;
import com.banzneri.particles.Emitter;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class TestPlayer extends Rect {
    private TestScreen host;
    private Emitter emitter;
    private TestRect rect;
    private TestLine line;

    public TestPlayer(TestScreen host) {
        super(50, 200, 50, 50);
        getRectangle().setFill(Color.RED);
        setHost(host);
        rect = new TestRect(100, 300, getWidth(), getHeight());
        line = new TestLine(getCenterLocation().getX(), getCenterLocation().getY(),
                        rect.getCenterLocation().getX(), rect.getCenterLocation().getY());
        host.addGameObject(rect);
        host.addGameObject(line);
        emitter = new Emitter(getLocation(), getWidth(), host);
        //emitter.emit();
    }

    public TestScreen getHost() {
        return host;
    }

    public void setHost(TestScreen host) {
        this.host = host;
    }

    public void shoot() {
        TestBullet bullet = new TestBullet(getX(), getY());
        host.addGameObject(bullet);
        TestScreen s = host;
        s.addBullet(bullet);
    }

    @Override
    public void move() {
        super.move();
        emitter.setLocation(new Point2D(getX(), getY() + getSpeedY()));
        line.setStartX(getCenterLocation().getX());
        line.setStartY(getCenterLocation().getY());
        line.setEndX(rect.getCenterLocation().getX());
        line.setEndY(rect.getCenterLocation().getY());
    }

    public TestRect getTestRect() {
        return rect;
    }
}
