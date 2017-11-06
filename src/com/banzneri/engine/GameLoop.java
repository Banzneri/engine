package com.banzneri.engine;

import com.banzneri.Screen;
import com.banzneri.graphics.GameObject;
import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private Screen host;
    private long before = System.nanoTime();
    private float delta;

    public GameLoop(Screen host) {
        this.host = host;
    }

    @Override
    public void handle(long now) {
        delta = (float) ((now - before) / 1E9);
        host.update();
        host.render();
        before = now;
    }

    public float delta() {
        return delta;
    }
}
