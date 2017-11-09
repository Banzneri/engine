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

    public Sprite(Texture texture, Rect rect) {
        super(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setTexture(texture);
        initView();
    }

    public Sprite(double x, double y, double width, double height, Texture texture) {
        super(x, y, width, height);
        setTexture(texture);
        initView();
    }

    @Override
    public void move() {
        getRectangle().relocate(getX(), getY());
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        getView().setX(getX());
        getView().setY(getY());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.transform(new Affine(new Rotate(getRotation(), getX(), getY())));
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        gc.restore();
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
        getView().setRotate(rotation);
    }

    @Override
    public Node getNode() {
        return getView();
    }
}
