package com.banzneri.TestGame;

import com.banzneri.geometry.Rect;
import javafx.scene.paint.Color;

public class TestBullet extends Rect {
    public TestBullet(double x, double y) {
        super(x, y, 5, 5);
        setColor(Color.AQUA);
        setSpeedY(-20);
    }
}
