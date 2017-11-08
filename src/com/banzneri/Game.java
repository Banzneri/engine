package com.banzneri;

import com.banzneri.graphics.GameObject;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class Game extends Application {
    private Stage stage;
    private Screen screen;

    abstract public void start(Stage primaryStage) throws Exception;

    public void startGame(Stage stage, Screen screen) {
        setStage(stage);
        getStage().show();
        setScreen(screen);
    }

    abstract public void update();

    public double getWidth() {
        return getScreen().getWidth();
    }

    public double getHeight() {
        return getScreen().getHeight();
    }

    public void setWidth(double width) {
        stage.setWidth(width);
    }

    public void setHeight(double height) {
        stage.setHeight(height);
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
        stage.setScene(getScreen());
    }

    public Screen getScreen() {
        return screen;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ArrayList<GameObject> getGameObjects() {
        return getScreen().getGameObjects();
    }
}