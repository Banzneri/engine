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

    public void initGame() {
        sound = new Sound("shot.wav");
        sound.play();
        music = new Music("music.mp3");
        screen = new TestScreen(1680, 1050, this);
    }

    @Override
    public void update() {

    }

    public void playSound() {
        sound.play();
    }

    public void toggleMusic() {
        if(music.isPlaying())
            music.pause();
        else music.play();
    }

    public ArrayList<TestBullet> getBullets() {
        return screen.getBullets();
    }
}
