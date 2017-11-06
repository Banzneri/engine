package com.banzneri.graphics;

import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
    void move();
    void draw(GraphicsContext gc);
}
