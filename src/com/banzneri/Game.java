package com.banzneri;

import com.banzneri.graphics.GameObject;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The entry point for the game application. Sets up the javafx Stage and the Screen for the stage.
 * Starts up the javafx application.
 */
public abstract class Game extends Application {
    /** The javafx main stage */
    private Stage stage;
    /** The current screen of the game  */
    private Screen screen;

    /**
     * Starts the game. Sets the stage and screen and shows them. This should be called
     * from the start(Stage primaryStage) method from the extending class to start the game.
     *
     * @param stage The primaryStage stage from the class that extends Game
     * @param screen The screen to start the game with
     */
    public void startGame(Stage stage, Screen screen) {
        setStage(stage);
        getStage().show();
        setScreen(screen);
    }

    /**
     * An abstract start method, which is overridden from the javafx Application class,
     * where the user can create the Screen and start the application.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    abstract public void start(Stage primaryStage) throws Exception;

    /**
     * Setter for the Screen. If the game already has a screen (i.e. the user wants to change a screen), stops the
     * GameLoop AnimationTimer of the previous screen so it will be destroyed.
     *
     * @param screen The screen to set
     */
    public void setScreen(Screen screen) {
        if(getScreen() != null) {
            getScreen().getGameLoop().stop();
        }
        this.screen = screen;
        stage.setScene(screen);
        System.out.println(stage.getScene());
    }

    // SETTERS & GETTERS

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