package com.banzneri.PlatformGame;

import com.banzneri.Screen;
import javafx.scene.image.Image;

public class IntroScreen extends Screen {
    private Image introImage = new Image("/intro.png");
    private double fadeInTime = 0;
    private MyScreen myScreen;

    public IntroScreen(PlatformGame host) {
        super(1280, 960, host);
        myScreen = new MyScreen(1280, 960, host);
        getRoot().setOnMouseClicked(e -> getHost().setScreen(myScreen));
    }

    @Override
    public void update() {
        fadeInTime += getDelta();
        clearScreen();
        getGc().setGlobalAlpha(fadeInTime / 5);
        getGc().drawImage(introImage, 0, 0, getWidth(), getHeight());
        System.out.println(fadeInTime);
        if(fadeInTime > 5) {
            getHost().startGame(getHost().getStage(), myScreen);
            this.destroy();
        }
    }
}
