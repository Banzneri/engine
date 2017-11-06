package com.banzneri.MyGame;

import com.banzneri.Game;
import com.banzneri.graphics.Rect;
import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TestBrick extends Sprite {

    public TestBrick() {
        super(new Texture("/brick.png"), new Rect(0, 0, 100, 100));
        setVelocityX(10);
    }
}
