package com.banzneri.TileGame;

import com.banzneri.Game;
import javafx.stage.Stage;

public class TileGame extends Game {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameScreen gameScreen = new GameScreen(1680, 1050, this);
        startGame(primaryStage, gameScreen);
    }
}
