package com.banzneri;

import com.banzneri.audio.Music;
import com.banzneri.engine.GameLoop;
import com.banzneri.graphics.GameObject;
import com.banzneri.input.InputListener;
import com.banzneri.particles.Particle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Optional;

public abstract class Screen extends Scene {
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Particle> particles = new ArrayList<>();
    private InputListener listener;
    private Image backGroundImage;
    private Game host;
    private GameLoop gameLoop;
    private Music music;
    private Group root;
    private Canvas canvas;
    private GraphicsContext gc;

    public Screen(double width, double height, Game host) {
        super(new Group(), width, height);
        setGameObjects(new ArrayList<>());
        canvas = new Canvas(width, height);
        setGc(canvas.getGraphicsContext2D());
        setHost(host);
        initRoot();
        setGameLoop(new GameLoop(this));
        gameLoop.start();
    }

    public Screen() {
        super(new Group(), 640, 480);
        gameObjects = new ArrayList<>();
    }

    private void initRoot() {
        root = new Group();
        root.getChildren().add(canvas);
        setRoot(root);
    }

    private void initInput() {
        Optional<InputListener> l = Optional.ofNullable(listener);
        l.ifPresent(e -> {
            setOnKeyPressed(e::onKeyPressed);
            setOnKeyReleased(e::onKeyUp);
            setOnMousePressed(e::onMousePressed);
            setOnMouseClicked(e::onMouseClicked);
            setOnMouseMoved(e::onMouseMoved);
            setOnMouseDragged(e::onMouseDragged);
            setOnMouseDragEntered(e::onMouseDragStart);
            setOnMouseDragExited(e::onMouseDragEnd);
            setOnMouseReleased(e::onMouseReleased);
        });
    }

    abstract public void update();

    public void moveObjects() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gameObjects.forEach(GameObject::move);
        particles.forEach(GameObject::moveAlternative);
        gameObjects.forEach(e -> e.draw(gc));
    }

    public void addListener(InputListener listener) {
        this.listener = listener;
        initInput();
    }

    public void addGameObject(GameObject gameObject) {
        if(gameObject instanceof Particle) {
            particles.add((Particle) gameObject);
        }
        else {
            gameObjects.add(gameObject);
        }
        root.getChildren().add(gameObject.getNode());
    }

    public void addGameObjects(ArrayList<GameObject> objects) {
        gameObjects.addAll(objects);
        //objects.forEach(e -> root.getChildren().add(e.getNode()));
    }

    public void removeGameObject(GameObject gameObject) {
        if(gameObject instanceof Particle) {
            particles.remove(gameObject);
        } else {
            gameObjects.remove(gameObject);
        }
        //root.getChildren().remove(gameObject.getNode());
    }

    public void removeGameObjects(ArrayList<GameObject> objects) {
        ArrayList<Node> toRemove = new ArrayList<>();
        objects.forEach(e -> toRemove.add(e.getNode()));
        gameObjects.removeAll(objects);
        //root.getChildren().removeAll(toRemove);
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

    public Image getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(Image backGroundImage) {
        this.backGroundImage = backGroundImage;
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

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
