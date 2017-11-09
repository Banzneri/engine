package com.banzneri.animations;

import com.banzneri.graphics.GameObject;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class RotationAnimation extends Animation {
    private GameObject gameObject;
    private double angleInDegrees;


    public RotationAnimation(double durationInSeconds, double angleInDegrees, GameObject gameObject) {
        super(durationInSeconds);
        setGameObject(gameObject);
        setAngleInDegrees(angleInDegrees);
        RotateTransition transition = new RotateTransition(Duration.seconds(durationInSeconds), gameObject.getNode());
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setByAngle(getAngleInDegrees());
        transition.setAutoReverse(true);
        setTransition(transition);
    }

    public void start() {
        getTransition().play();
    }

    public void stop() {
        getTransition().stop();
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
}
