package com.banzneri.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {
    private Media sound;
    MediaPlayer player;

    public Sound(String url) {
        sound = new Media(new File(url).toURI().toString());
        player = new MediaPlayer(sound);
    }

    public void play() {
        player.seek(Duration.ZERO);
        player.play();
    }
}
