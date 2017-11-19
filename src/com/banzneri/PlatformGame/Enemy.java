package com.banzneri.PlatformGame;

import com.banzneri.Screen;
import com.banzneri.animations.Animation;
import com.banzneri.audio.Sound;
import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.Texture;
import com.banzneri.particles.Emitter;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class Enemy extends Player {

    public Enemy(double x, double y, MyScreen host) {
        super(x, y, host);
        setHost(host);
        setTexture(new Texture("/enemy.png"));
        setStandTexture(new Texture("/enemy.png"));
        setSpeedX(1);
        setWalkAnim(new Texture("/enemy.gif"));
        setJumpTexture(new Texture("/enemy.png"));

    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isVisible()) {
            gc.save();
            if(!isGrounded()) {
                setTexture(getJumpTexture());
            } else if(getSpeedX() != 0 ) {
                setTexture(getWalkAnim());
            } else {
                setTexture(getStandTexture());
            }
            gc.transform(new Affine(new Rotate(getRotation(), getX() + getPivotX(), getY() + getPivotY())));
            if(isFlipped()) {
                gc.drawImage(getTexture().getImage(), 0, 0, getTexture().getImage().getWidth(), getTexture().getImage().getHeight(), getX() + getWidth(),getY(), -getWidth(), getHeight());
            } else {
                gc.drawImage(getTexture().getImage(), getX(), getY(), getWidth(), getHeight());
            }
            gc.restore();
        }
    }
}
