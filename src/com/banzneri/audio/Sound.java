package com.banzneri.audio;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Sound {
    private AudioClip sound;

    public Sound(String url) {
        sound = new AudioClip(new File(url).toURI().toString());
    }

    public void play() {
        sound.play(1.0);
    }
}
