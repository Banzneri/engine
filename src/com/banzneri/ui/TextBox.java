package com.banzneri.ui;

import com.banzneri.graphics.GameObject;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class TextBox extends GameObject {
    private String text;

    public TextBox(double x, double y, double width, double height, String text) {
        super(x, y, width, height);
        setText(text);
    }

    @Override
    public void draw(GraphicsContext gc) {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Node getNode() {
        return new Text(text);
    }
}
