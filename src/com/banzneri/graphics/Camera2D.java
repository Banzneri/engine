package com.banzneri.graphics;

import com.banzneri.Screen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

public class Camera2D {
    private double viewportWidth;
    private double viewPortHeight;
    private Screen host;
    private double x;
    private double y;

    public Camera2D(double viewportWidth, double viewPortHeight, Screen host) {
        setViewportWidth(viewportWidth);
        setViewPortHeight(viewPortHeight);
        setHost(host);
        setX(0);
        setY(0);
    }

    public double getViewportWidth() {
        return viewportWidth;
    }

    public void setViewportWidth(double viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public double getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(double viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    public Screen getHost() {
        return host;
    }

    public void setHost(Screen host) {
        this.host = host;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        host.getGc().translate(getX() - x, getY());
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        host.getGc().translate(getX(), getY() - y);
        this.y = y;
    }
}
