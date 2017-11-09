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
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Optional;

public class Screen extends Scene {
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Rectangle> rectangles;
    private GraphicsContext gc;
    private GraphicsContext gcbackground;
    private InputListener listener;
    private Image backGroundImage;
    private Canvas canvas;
    private Canvas canvasBackground;
    private Game host;
    private GameLoop gameLoop;
    private Music music;
    private Group root;

    public Screen(double width, double height, Game host) {
        super(new Group(), width, height);
        gameObjects = new ArrayList<>();
        rectangles = new ArrayList<>();
        canvas = new Canvas(width, height);
        canvasBackground = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gcbackground = canvasBackground.getGraphicsContext2D();
        this.host = host;
        initRoot();
        initRectangles();
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    public Screen() {
        super(new Group(), 640, 480);
        gameObjects = new ArrayList<>();
    }

    private void initRectangles() {
        gameObjects.forEach(e -> rectangles.add(e.getRectangle()));
    }

    private void initRoot() {
        root = new Group();
        root.getChildren().add(canvasBackground);
        root.getChildren().add(canvas);
        setRoot(root);
    }

    private void initInput() {
        Optional<InputListener> l = Optional.ofNullable(listener);
        l.ifPresent(e -> {
            setOnKeyPressed(e::onKeyPressed);
            setOnKeyReleased(e::onKeyUp);
            setOnMouseClicked(e::onMouseClicked);
        });
    }

    private void drawBackground() {
        Optional<Image> bg = Optional.ofNullable(backGroundImage);
        bg.ifPresent(e -> gcbackground.drawImage(e, 0, 0, getWidth(), getHeight()));
    }

    public void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gcbackground.clearRect(0, 0, getWidth(), getHeight());
        drawBackground();
        gameObjects.forEach(o -> o.draw(gc));
    }

    public void update() {
        host.update();
        gameObjects.forEach(GameObject::move);
    }

    public void addListener(InputListener listener) {
        this.listener = listener;
        initInput();
    }

    public void addGameObject(GameObject gameObject) {
        rectangles.add(gameObject.getRectangle());
        gameObjects.add(gameObject);
        root.getChildren().add(gameObject.getRectangle());
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
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

    public float getDelta() {
        return gameLoop.delta();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(ArrayList<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }
}
