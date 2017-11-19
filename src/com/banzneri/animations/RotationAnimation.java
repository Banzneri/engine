package com.banzneri.animations;

import com.banzneri.Util;
import com.banzneri.graphics.GameObject;
import javafx.animation.AnimationTimer;

public class RotationAnimation extends Animation {
    private GameObject gameObject;
    private double angleInDegrees;
    private double duration;
    private AnimationTimer timer;
    private double pivotX;
    private double pivotY;


    public RotationAnimation(double durationInSeconds, double angleInDegrees, GameObject gameObject) {
        super(durationInSeconds);
        setGameObject(gameObject);
        setAngleInDegrees(angleInDegrees);
        setDuration(durationInSeconds);
    }

    public void start() {
        double startRotation = gameObject.getRotation();
        timer = new AnimationTimer() {
            long start = System.nanoTime();
            @Override
            public void handle(long now) {
                long elapsed = System.nanoTime() - start;
                if(Util.nanosToSeconds(elapsed) > duration) this.stop();
                gameObject.setRotation(getAngleInDegrees() * (Util.nanosToSeconds(elapsed) / duration));
            }
        };
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public double getAngleInDegrees() {
        return angleInDegrees;
    }

    public void setAngleInDegrees(double angleInDegrees) {
        this.angleInDegrees = angleInDegrees;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPivotX() {
        return pivotX;
    }

    public void setPivotX(double pivotX) {
        this.pivotX = pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }

    public void setPivotY(double pivotY) {
        this.pivotY = pivotY;
    }
}
