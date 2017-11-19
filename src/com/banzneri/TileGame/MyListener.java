package com.banzneri.TileGame;


import com.banzneri.graphics.GameObject;
import com.banzneri.input.InputListener;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class MyListener implements InputListener {
    private GameObject rect;
    private boolean down = false;
    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean dragging = false;

    public MyListener(GameObject player) {
        this.rect = player;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP:    up = true; break;
            case S:
            case DOWN:  down = true; break;
            case A:
            case LEFT:  left = true; break;
            case D:
            case RIGHT: right = true; break;
            case SPACE: //rect.shoot();
        }

        move();
    }

    @Override
    public void onKeyUp(KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP:    up = false; break;
            case S:
            case DOWN:  down = false; break;
            case A:
            case LEFT:  left = false; break;
            case D:
            case RIGHT:  right = false; break;
        }

        move();
    }

    @Override
    public void onMousePressed(MouseEvent me) {
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
    }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void onMouseMoved(MouseEvent e) {

    }

    @Override
    public void onMouseDragStart(MouseDragEvent mouseDragEvent) {

    }

    @Override
    public void onMouseDragEnd(MouseDragEvent mouseDragEvent) {

    }

    @Override
    public void onMouseDragged(MouseEvent mouseEvent) {
    }

    private void move() {
        rect.setSpeedY(0);
        rect.setSpeedX(0);

        if(left)
            rect.setSpeedX(-2);

        if(right)
            rect.setSpeedX(2);

        if(up)
            rect.setSpeedY(-2);

        if(down)
            rect.setSpeedY(2);

        if(up && down)
            rect.setSpeedY(0);

        if(left && right)
            rect.setSpeedX(0);
    }
}
