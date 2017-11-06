package com.banzneri.MyGame;

import com.banzneri.Game;
import com.banzneri.Screen;
import com.banzneri.graphics.Rect;
import com.banzneri.graphics.Sprite;
import com.banzneri.input.InputListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MyInputListener implements InputListener {
    private Rect rect;
    private TestGame host;

    public MyInputListener(Rect rect, TestGame host) {
        this.rect = rect;
        this.host = host;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP:    rect.setVelocityY(-5); break;
            case S:
            case DOWN:  rect.setVelocityY(5); break;
            case A:
            case LEFT:  rect.setVelocityX(-5); break;
            case D:
            case RIGHT:  rect.setVelocityX(5); break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP:    rect.setVelocityY(0); break;
            case S:
            case DOWN:  rect.setVelocityY(0); break;
            case A:
            case LEFT:  rect.setVelocityX(0); break;
            case D:
            case RIGHT:  rect.setVelocityX(0); break;
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseButton.SECONDARY) {
            host.toggleMusic();
        } else {
            host.playSound();
        }
    }
}
