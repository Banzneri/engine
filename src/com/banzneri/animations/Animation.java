package com.banzneri.animations;

import javafx.animation.Transition;

public abstract class Animation {
    private Transition transition;
    private double duration;
    private double cycleCount;

    public Animation(double duration) {
        setDuration(duration);
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(double cycleCount) {
        this.cycleCount = cycleCount;
    }
}
