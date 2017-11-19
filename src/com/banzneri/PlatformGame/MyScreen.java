package com.banzneri.PlatformGame;

import com.banzneri.Screen;
import com.banzneri.audio.Music;
import com.banzneri.audio.Sound;
import com.banzneri.geometry.Rect;
import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.Camera2D;
import com.banzneri.graphics.GameObject;
import com.banzneri.particles.Emitter;
import com.banzneri.tileengine.TMXMap;
import com.banzneri.tileengine.TMXMapLoader;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;

public class MyScreen extends Screen {
    private ArrayList<GameObject> platforms = new ArrayList<>();
    private ArrayList<GameObject> ladders = new ArrayList<>();
    private Player player;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    private Vector2d gravity = new Vector2d(0, 0.3);
    private TMXMap tiledMap;
    private Camera2D camera2D;
    private double farthestWalked = 0;
    private Image enemyImage = new Image("/enemy.png");
    private Vector2d startLocation = new Vector2d(50, getHeight() / 2);
    private Emitter bloodEmitter;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Sound deathSound = new Sound("splash.mp3");
    private Music music = new Music("scifi.mp3");

    public MyScreen(double width, double height, PlatformGame host) {
        super(width, height, host);
        initGameObjects();
        camera2D = new Camera2D(getWidth(), getHeight(), this);
        getRoot().setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.SECONDARY)) {
                enemies.add(new Enemy(camera2D.getX() + e.getX(), e.getY(), this));
            }
        });
        setCursor(new ImageCursor(enemyImage));
        music.play();
    }

    /**
     * @see
     */
    @Override
    public void update() {
        clearScreen();
        tiledMap.get("bg").draw(getGc());
        tiledMap.get("fg").draw(getGc());
        player.draw(getGc());
        enemies.forEach(e -> e.draw(getGc()));
        new ArrayList<>(bullets).forEach(e -> e.draw(getGc()));
        player.moveAlternative();
        enemies.forEach(GameObject::moveAlternative);
        bullets.forEach(GameObject::moveAlternative);
        updatePlayer();
        handleCamera();
    }

    /**
     * Initializes all the tiles, creates the player object and adds a listener to the screen
     */
    private void initGameObjects() {
        player = new Player(startLocation.x, startLocation.y, this);
        Enemy enemy = new Enemy(100, startLocation.y, this);
        Enemy enemy1 = new Enemy(200, startLocation.y, this);
        enemies.add(enemy);
        enemies.add(enemy1);
        tiledMap = new TMXMapLoader().load("/scifi.tmx");
        tiledMap.setY(getHeight() / 4);
        tiledMap.initTiles();
        tiledMap.getObjects().forEach(e -> {
            Rect rect = new Rect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            if(!e.getName().equals("ladder")) {
                platforms.add(rect);
            } else {
                ladders.add(rect);
            }
        });
        addTiledMap(tiledMap);
        addListener(player);

        bloodEmitter = new Emitter(new Vector2d(0, 0), tiledMap.getTileWidth(), this);
        bloodEmitter.setMaxParticles(50000);
        bloodEmitter.setRateInSeconds(300);
        bloodEmitter.setParticleSize(3);
        bloodEmitter.setDuration(250);
    }

    /**
     * Checks collisions, sets player velocities
     */
    private void updatePlayer() {
        player.setAccelerationY(gravity.y);
        enemies.forEach(e -> e.setAccelerationY(gravity.y));

        if(player.isMovingLeft()) {
            player.setFlipped(true);
            player.setSpeedX(-3);
        } else if(player.isMovingRight()) {
            player.setFlipped(false);
            player.setSpeedX(3);
        } else {
            player.setSpeedX(0);
        }

        if(player.isMovingLeft() && player.isMovingRight()) {
            player.setSpeedX(0);
        }

        handleCollision();
    }

    /**
     * Handles the camera movement
     */
    private void handleCamera() {
        if(player.getX() > farthestWalked) {
            farthestWalked = player.getX();
            camera2D.setX(player.getX() - getWidth() / 2);
        } else {
            camera2D.setX(farthestWalked - getWidth() / 2);
        }

        if(camera2D.getX() < 0) {
            camera2D.setX(0);
        }

        if(player.getX() + getWidth() / 2 > tiledMap.getWidth()) {
            camera2D.setX(tiledMap.getWidth() - getWidth());
        }

        if (player.getX() < camera2D.getX()) {
            player.setX(camera2D.getX() + 1);
        }
        if(player.getX() + player.getWidth() > tiledMap.getWidth()) {
            player.setX(tiledMap.getWidth() - player.getWidth());
        }
    }

    public void handleCollision() {
        boolean isInAir = true;

        for(GameObject platform : platforms) {
            if (player.isDown(platform) && player.isMovingDown()) {
                player.setY(platform.getMinY() - player.getHeight());
                player.setSpeedY(0);
                isInAir = false;
                player.setGrounded(true);
            }
            if (player.isRight(platform) && player.isMovingRight()) {
                player.setX(platform.getMinX() - player.getWidth());
                player.setSpeedX(0);
            }
            if (player.isLeft(platform) && player.isMovingLeft()) {
                player.setX(platform.getMaxX());
                player.setSpeedX(0);
            }
            if (player.isUp(platform) && player.isMovingUp()) {
                player.setY(platform.getMaxY() + 5);
                player.setSpeedY(0);
            }

            for(Enemy enemy : enemies) {
                if (enemy.isDown(platform) && enemy.getSpeedY() > 0.0) {
                    enemy.setY(platform.getMinY() - enemy.getHeight());
                    enemy.setSpeedY(0);
                    enemy.setGrounded(true);
                }
                if (enemy.isRight(platform) && enemy.getSpeedX() > 0.0 || (enemy.isLeft(platform) && enemy.getSpeedX() < 0.0)) {
                    enemy.setSpeedX(-enemy.getSpeedX());
                }
            }
        }

        if (player.isGrounded()) {
            player.setJumped(false);
        }

        if(isInAir && !player.isJumped()) {
            player.setGrounded(false);
            player.setAccelerationY(gravity.y);
        }

        main : for(Enemy enemy : new ArrayList<>(enemies)) {
            if(player.isDown(enemy)   && player.isVisible()) {
                enemy.setVisible(false);
                die(enemy);
                if(player.isSpaceDOwn()) {
                    player.setSpeedY(-10);
                } else {
                    player.setSpeedY(-7);
                }
            }
            for(Bullet bullet : new ArrayList<>(bullets)) {
                if(bullet.collidesWith(enemy)) {
                    die(enemy);
                    enemy.setVisible(false);
                    bullets.remove(bullet);
                }
            }
            if(player.isRight(enemy)|| player.isLeft(enemy)) {
                farthestWalked = 0;
                player.setX(startLocation.x);
                player.setY(startLocation.y);
            }

            for(Enemy enemy1 : enemies) {
                if(enemy1.collidesWith(enemy) && !enemy1.equals(enemy)) {
                    enemy.setSpeedX(-enemy.getSpeedX());
                    enemy1.setSpeedX(-enemy1.getSpeedX());
                    enemy.setFlipped(!enemy.isFlipped());
                    enemy1.setFlipped(!enemy1.isFlipped());
                    break main;
                }
            }
        }

        player.setCanClimb(false);

        for(GameObject ladder : ladders) {
            if(player.collidesWith(ladder)) {
                player.setCanClimb(true);
                System.out.println("climb");
            }
        }
    }

    public void die(Enemy gameObject) {
        bloodEmitter.setLocation(new Vector2d(gameObject.getX(), gameObject.getY() + gameObject.getWidth()));
        bloodEmitter.emit();
        enemies.remove(gameObject);
        deathSound.play();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}
