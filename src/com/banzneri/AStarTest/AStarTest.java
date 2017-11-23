package com.banzneri.AStarTest;

import com.banzneri.Game;
import javafx.stage.Stage;

public class AStarTest extends Game {

    @Override
    public void start(Stage primaryStage) throws Exception {
        startGame(primaryStage, new AStarScreen(this));
    }
}
