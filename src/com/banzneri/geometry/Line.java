package com.banzneri.geometry;

import com.banzneri.graphics.GameObject;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends GameObject {
    private javafx.scene.shape.Line line;

    public Line(double startX, double startY, double endX, double endY) {
        line = new javafx.scene.shape.Line(startX, startY, endX, endY);
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

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
    }
}
