package com.banzneri;

import com.banzneri.audio.Music;
import com.banzneri.engine.GameLoop;
import com.banzneri.graphics.GameObject;
import com.banzneri.input.InputListener;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Optional;

public class Screen extends Scene {
    private ArrayList<GameObject> gameObjects;
    private GraphicsContext gc;
    private GraphicsContext gcbackground;
    private InputListener listener;
    private Image backGroundImage;
    private Canvas canvas;
    private Canvas canvasBackground;
    private Game host;
    private GameLoop gameLoop;
    private Music music;

    public Screen(double width, double height, Game host) {
        super(new Group(), width, height);
        gameObjects = new ArrayList<>();
        canvas = new Canvas(width, height);
        canvasBackground = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gcbackground = canvasBackground.getGraphicsContext2D();
        this.host = host;

        Group root = new Group();
        root.getChildren().add(canvasBackground);
        root.getChildren().add(canvas);
        setRoot(root);

        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    public Screen() {
        super(new Group(), 640, 480);
        gameObjects = new ArrayList<>();
    }

    public void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gcbackground.clearRect(0, 0, getWidth(), getHeight());
        Optional<Image> bg = Optional.ofNullable(backGroundImage);
        bg.ifPresent(e -> gcbackground.drawImage(backGroundImage, 0, 0, getWidth(), getHeight()));
        gameObjects.forEach(o -> o.draw(gc));
    }

    public void update() {
        gameObjects.forEach(GameObject::move);
        host.update();
    }

    public void initInput() {
        Optional<InputListener> l = Optional.ofNullable(listener);
        l.ifPresent(e -> {
            setOnKeyPressed(e::onKeyPressed);
            setOnKeyReleased(e::onKeyUp);
            setOnMouseClicked(e::onMouseClicked);
        });
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public GraphicsContext getGcbackground() {
        return gcbackground;
    }

    public void setGcbackground(GraphicsContext gcbackground) {
        this.gcbackground = gcbackground;
    }

    public Image getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(Image backGroundImage) {
        this.backGroundImage = backGroundImage;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvasBackground() {
        return canvasBackground;
    }

    public void setCanvasBackground(Canvas canvasBackground) {
        this.canvasBackground = canvasBackground;
    }

    public Game getHost() {
        return host;
    }

    public void setHost(Game host) {
        this.host = host;
    }

    public AnimationTimer getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void addListener(InputListener listener) {
        this.listener = listener;
        initInput();
    }

    public float getDelta() {
        return gameLoop.delta();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
