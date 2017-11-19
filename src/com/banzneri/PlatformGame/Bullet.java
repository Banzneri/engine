package com.banzneri.PlatformGame;

import com.banzneri.geometry.Rect;
import javafx.scene.paint.Color;

public class Bullet extends Rect {
    private boolean destroyed = false;

    public Bullet(double x, double y) {
        super(x, y, 6, 3);
        setColor(Color.DEEPSKYBLUE);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
