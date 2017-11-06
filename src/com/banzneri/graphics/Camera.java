package com.banzneri.graphics;

import com.banzneri.Screen;

public class Camera {
    private double viewportWidth;
    private double viewPortHeight;
    private Screen host;

    public Camera(double viewportWidth, double viewPortHeight, Screen host) {
        setViewportWidth(viewportWidth);
        setViewPortHeight(viewPortHeight);
        setHost(host);
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
}
