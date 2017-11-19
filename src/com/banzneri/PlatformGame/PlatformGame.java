package com.banzneri.PlatformGame;

import com.banzneri.Game;
import javafx.stage.Stage;

public class PlatformGame extends Game {

    @Override
    public void start(Stage primaryStage) throws Exception {
        startGame(primaryStage, new IntroScreen(this));
    }
}
