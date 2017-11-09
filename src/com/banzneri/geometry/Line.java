package com.banzneri.geometry;

import com.banzneri.graphics.GameObject;
import javafx.scene.Node;

public class Line extends GameObject {
    private javafx.scene.shape.Line line;

    public Line(double startX, double startY, double endX, double endY) {
        super(0, 0, 0, 0);
        line = new javafx.scene.shape.Line(startX, startY, endX, endY);
    }

    @Override
    public Node getNode() {
        return line;
    }

    public void setStartX(double startX) {
        line.setStartX(startX);
    }

    public void setStartY(double startY) {
        line.setStartY(startY);
    }

    public void setEndX(double endX) {
        line.setEndX(endX);
    }

    public void setEndY(double endY) {
        line.setEndY(endY);
    }
}
