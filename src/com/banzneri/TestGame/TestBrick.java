package com.banzneri.TestGame;

import com.banzneri.geometry.Rect;
import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;

public class TestBrick extends Sprite {

    public TestBrick() {
        super(new Texture("/brick.png"), new Rect(0, 0, 100, 100));
        setSpeedX(-5);
    }
}
