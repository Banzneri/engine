package com.banzneri.TestGame;

import com.banzneri.Game;
import com.banzneri.audio.Music;
import com.banzneri.audio.Sound;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestGame extends Game {
    private Sound sound;
    private Music music;

    private TestScreen screen;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initGame();
        startGame(primaryStage, screen);
    }

    private void initGame() {
        sound = new Sound("shot.wav");
        sound.play();
        music = new Music("music.mp3");
        screen = new TestScreen(1680, 1050, this);
    }

    void playSound() {
        sound.play();
    }

    void toggleMusic() {
        if(music.isPlaying())
            music.pause();
        else music.play();
    }

    public ArrayList<TestBullet> getBullets() {
        return screen.getBullets();
    }
}
