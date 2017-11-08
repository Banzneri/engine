package com.banzneri.TestGame;

import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;

public class TestPlayer extends Sprite {
    private TestGame host;

    public TestPlayer(TestGame host) {
        super(50, 50, 50, 50, new Texture("/brick.png"));
        setHost(host);
    }

    public TestGame getHost() {
        return host;
    }

    public void setHost(TestGame host) {
        this.host = host;
    }

    public void shoot() {
        TestBullet bullet = new TestBullet(getX(), getY());
        host.getScreen().addGameObject(bullet);
        TestScreen s = (TestScreen) host.getScreen();
        s.addBullet(bullet);
        host.setScreen(s);
    }
}
