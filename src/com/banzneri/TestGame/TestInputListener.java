package com.banzneri.TestGame;


import com.banzneri.input.InputListener;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class TestInputListener implements InputListener {
    private TestPlayer rect;
    private TestGame host;
    private boolean down = false;
    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean dragging = false;

    public TestInputListener(TestPlayer player, TestGame host) {
        this.rect = player;
        this.host = host;
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
        if(rect.getTestRect().contains(me.getX(), me.getY())) {
            dragging = true;
            rect.getTestRect().setSpeed(0, 0);
            rect.getTestRect().setCollides(false);
        } else if(me.getButton() == MouseButton.SECONDARY) {
                host.toggleMusic();
        } else {
            host.playSound();
            rect.shoot(new Point2D(me.getX() - rect.getX(), me.getY() - rect.getY()).normalize());
        }
        System.out.println("Mouse: " + me.getX() + " " + me.getY());
        System.out.println("rect: " + rect.getTestRect().getRectangle().getTranslateX() + " " + rect.getTestRect().getRectangle().getTranslateY());
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
    }

    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
        dragging = false;
        rect.getTestRect().setCollides(true);
    }

    @Override
    public void onMouseMoved(MouseEvent e) {

    }

    @Override
    public void onMouseDragStart(MouseDragEvent mouseDragEvent) {

    }

    @Override
    public void onMouseDragEnd(MouseDragEvent mouseDragEvent) {
        dragging = false;
    }

    @Override
    public void onMouseDragged(MouseEvent mouseEvent) {
        if(dragging) {
            rect.getTestRect().setX(mouseEvent.getX() - rect.getTestRect().getWidth()/2);
            rect.getTestRect().setY(mouseEvent.getY() - rect.getTestRect().getHeight()/2);
            rect.getTestRect().setSpeed(0, 0);
        }
    }

    private void move() {
        rect.setSpeedY(0);
        rect.setSpeedX(0);

        if(left)
            rect.setSpeedX(-5);

        if(right)
            rect.setSpeedX(5);

        if(up)
            rect.setSpeedY(-5);

        if(down)
            rect.setSpeedY(5);

        if(up && down)
            rect.setSpeedY(0);

        if(left && right)
            rect.setSpeedX(0);
    }
}
