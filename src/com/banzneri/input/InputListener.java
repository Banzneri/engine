package com.banzneri.input;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;


public interface InputListener {
    /**
     * Using the javafx KeyEvents
     * @param e
     */
    void onKeyPressed(KeyEvent e);
    void onKeyUp(KeyEvent e);
    void onMouseClicked(MouseEvent e);
    void onMouseMoved(MouseEvent e);
    void onMouseDragged(MouseEvent mouseEvent);
    void onMouseDragStart(MouseDragEvent mouseDragEvent);
    void onMouseDragEnd(MouseDragEvent mouseDragEvent);
    void onMouseReleased(MouseEvent mouseEvent);
    void onMousePressed(MouseEvent mouseEvent);
}
