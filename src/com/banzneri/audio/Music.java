package com.banzneri.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    private boolean isPlaying = false;
    private MediaPlayer player;
    private Media music;

    public Music(String url) {
        music = new Media(new File(url).toURI().toString());
        player = new MediaPlayer(music);
    }

    public void play() {
        isPlaying = true;
        player.play();
    }

    public void stop() {
        isPlaying = false;
        player.stop();
    }

    public void pause() {
        isPlaying = false;
        player.pause();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
