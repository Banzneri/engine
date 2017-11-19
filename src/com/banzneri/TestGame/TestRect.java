package com.banzneri.TestGame;

import com.banzneri.geometry.Rect;
import javafx.scene.paint.Color;

public class TestRect extends Rect {
    private TestPlayer host;

    public TestRect(double x, double y, double width, double height) {
        super(x, y, width, height);
        setColor(Color.BLACK);
    }

    public TestPlayer getHost() {
        return host;
    }

    public void setHost(TestPlayer host) {
        this.host = host;
    }
}
