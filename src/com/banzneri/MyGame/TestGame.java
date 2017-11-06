package com.banzneri.MyGame;

import com.banzneri.Game;
import com.banzneri.Screen;
import com.banzneri.audio.Music;
import com.banzneri.audio.Sound;
import com.banzneri.graphics.Rect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestGame extends Game {
    private Sound sound;
    private Music music;

    @Override
    public void start(Stage primaryStage) throws Exception {
        sound = new Sound("shot.wav");
        sound.play();
        music = new Music("music.mp3");
        music.play();
        startGame(primaryStage);
        Rect rect = new Rect(20, 50, 100, 100);
        rect.setColor(Color.RED);
        TestBrick b1 = new TestBrick();
        TestBrick b2 = new TestBrick();
        Screen screen = new Screen(1000, 1000, this);
        screen.addListener(new MyInputListener(rect, this));
        screen.addGameObject(rect);
        screen.addGameObject(b1);
        screen.addGameObject(b2);
        screen.setBackGroundImage(new Image("/bg.jpg"));
        setScreen(screen);
    }

    public void update() {

    }

    public void playSound() {
        sound.play();
    }

    public void toggleMusic() {
        if (music.isPlaying()) {
            music.pause();
        } else {
            music.play();
        }
    }
}
