package com.banzneri.TestGame;

import com.banzneri.Game;
import com.banzneri.audio.Music;
import com.banzneri.audio.Sound;
import com.banzneri.graphics.GameObject;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestGame extends Game {
    private Sound sound;
    private Music music;
    private TestPlayer player;
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
        player = new TestPlayer(this);
        screen = new TestScreen(1680, 1050, this);
        screen.addListener(new TestInputListener(player, this));
        screen.addGameObject(player);
    }

    @Override
    public void update() {
        //checkCollision();
    }

    public void playSound() {
        sound.play();
    }

    public void toggleMusic() {
        if(music.isPlaying())
            music.pause();
        else music.play();
    }

    private void checkCollision() {
        ArrayList<GameObject> collided = new ArrayList<>();
        main : for(GameObject o1: getGameObjects()) {
            for (GameObject o2 : getGameObjects()) {
                if(o1.collidesWith(o2) && !o1.equals(o2)) {
                    collided.add(o1);
                    collided.add(o2);
                    break main;
                }
            }
        }
        handleObjectHit(collided);
        handleWallHit();
        handleBulletHit();
    }

    public boolean isHit(TestBullet bullet, GameObject object) {
        return bullet.collidesWith(object) && !object.equals(player) && !object.equals(bullet);
    }

    public boolean isWallHit(GameObject r) {
        return r.getX() < 0 || r.getX() + r.getWidth() > getWidth();
    }

    public void handleObjectHit(ArrayList<GameObject> collided) {
        collided.forEach(object -> {
            object.setX(object.getX() + (-object.getSpeed().getX()));
            object.setSpeedX(-object.getSpeed().getX());
        });
    }

    public void handleWallHit() {
        getGameObjects().forEach(object -> {
            if(isWallHit(object)) object.setSpeedX(-object.getSpeedX());
        });
    }

    public void handleBulletHit() {
        ArrayList<GameObject> shotObjects = new ArrayList<>();
        getBullets().forEach(bullet -> getGameObjects().forEach(object -> {
            if(isHit(bullet, object)) shotObjects.add(object);
        }));
        getGameObjects().removeAll(shotObjects);
    }

    public TestPlayer getPlayer() {
        return player;
    }

    public void setPlayer(TestPlayer player) {
        this.player = player;
    }

    public ArrayList<TestBullet> getBullets() {
        return screen.getBullets();
    }
}
