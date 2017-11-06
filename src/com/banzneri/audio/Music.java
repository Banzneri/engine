package com.banzneri.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music extends Sound {
    private boolean isPlaying = false;

    public Music(String url) {
        super(url);
    }

    @Override
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
