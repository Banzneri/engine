package com.banzneri.graphics;

import com.banzneri.geometry.Rect;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Sprite extends GameObject {
    private Texture texture;
    private ImageView view;
    private boolean flipped;

    public Sprite(Texture texture, Rect rect) {
        super(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setTexture(texture);
        initView();
        setFlipped(false);
    }

    public Sprite(double x, double y, double width, double height, Texture texture) {
        super(x, y, width, height);
        setTexture(texture);
        initView();
    }

    @Override
    public void move(double delta) {
        super.move(delta);
        getView().setX(getX());
        getView().setY(getY());
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isVisible()) {
            gc.save();
            gc.transform(new Affine(new Rotate(getRotation(), getX() + getPivotX(), getY() + getPivotY())));
            if(flipped) {
                gc.drawImage(getTexture().getImage(), 0, 0, getTexture().getImage().getWidth(), getTexture().getImage().getHeight(), getX() + getWidth(),getY(), -getWidth(), getHeight());
            } else {
                gc.drawImage(getTexture().getImage(), getX(), getY(), getWidth(), getHeight());
            }
            gc.restore();
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public void initView() {
        setView(new ImageView(texture.getImage()));
        getView().setX(getX());
        getView().setY(getY());
        getView().setFitWidth(getWidth());
        getView().setFitHeight(getHeight());
        setRotation(getRotation());
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    @Override
    public void setRotation(double rotation) {
        super.setRotation(rotation);
        getView().getTransforms().clear();
        Rotate rotate = new Rotate();
        rotate.setAngle(getRotation());
        rotate.setPivotX(getX() + getPivotX());
        rotate.setPivotY(getY() + getPivotY());
        getView().getTransforms().add(new Affine(rotate));
    }

    @Override
    public Node getNode() {
        return getView();
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
}
