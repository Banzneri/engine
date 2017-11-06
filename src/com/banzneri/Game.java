package com.banzneri;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class Game extends Application {
    private Stage stage;
    private Screen screen;

    public void init() {
    };

    abstract public void start(Stage primaryStage) throws Exception;

    public void initGame() {

    }

    public void startGame(Stage stage) {
        this.stage = stage;
        this.stage.show();
        setScreen(screen);
    }

    abstract public void update();

    public double getWidth() {
        return stage.getWidth();
    }

    public double getHeight() {
        return stage.getHeight();
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
}