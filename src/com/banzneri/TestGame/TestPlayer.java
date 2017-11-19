package com.banzneri.TestGame;

import com.banzneri.geometry.Rect;
import com.banzneri.geometry.Vector2d;
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
        rect = new TestRect(100, 300, getWidth(), getHeight());
        line = new TestLine(getCenterLocation().x, getCenterLocation().y,
                        rect.getCenterLocation().x, rect.getCenterLocation().y);
        emitter = new Emitter(getLocation(), getWidth(), host);
        emitter.setParticleSize(1);
        emitter.emit();

        setColor(Color.RED);
        setHost(host);
        host.addGameObject(line);
        host.addGameObject(rect);
    }

    public TestScreen getHost() {
        return host;
    }

    public void setHost(TestScreen host) {
        this.host = host;
    }

    public void shoot(Point2D velocity) {
        TestBullet bullet = new TestBullet(getX(), getY());
        bullet.setSpeedX(velocity.getX() * 20);
        bullet.setSpeedY(velocity.getY() * 20);
        host.addGameObject(bullet);
        TestScreen s = host;
        s.addBullet(bullet);
    }

    @Override
    public void move(double delta) {
        super.move(delta);
        emitter.setLocation(new Vector2d(getX(), getY() + getSpeedY()));
        line.setStartX(getCenterLocation().x);
        line.setStartY(getCenterLocation().y);
        line.setEndX(rect.getCenterLocation().x);
        line.setEndY(rect.getCenterLocation().y);
    }

    public TestRect getTestRect() {
        return rect;
    }
}
