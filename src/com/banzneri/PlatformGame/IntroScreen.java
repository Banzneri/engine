package com.banzneri.PlatformGame;

import com.banzneri.Screen;
import javafx.scene.image.Image;

public class IntroScreen extends Screen {
    private Image introImage = new Image("/intro.png");
    private double fadeCounter = 0;
    private double fadeTime = 10;
    private MyScreen myScreen;

    public IntroScreen(PlatformGame host) {
        super(1280, 960, host);
        myScreen = new MyScreen(1280, 960, host);
        getRoot().setOnMouseClicked(e -> getHost().setScreen(myScreen));
    }

    @Override
    public void update() {
        fadeCounter += getDelta();
        clearScreen();
        getGc().setGlobalAlpha(fadeCounter / fadeTime);
        getGc().drawImage(introImage, 0, 0, getWidth(), getHeight());
        System.out.println(fadeCounter);
        if(fadeCounter > 10) {
            getHost().startGame(getHost().getStage(), myScreen);
        }
    }
}
