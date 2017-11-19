package com.banzneri;

import com.banzneri.audio.Music;
import com.banzneri.engine.GameLoop;
import com.banzneri.graphics.Camera2D;
import com.banzneri.graphics.GameObject;
import com.banzneri.input.InputListener;
import com.banzneri.particles.Particle;
import com.banzneri.tileengine.TMXMap;
import com.banzneri.tileengine.Tile;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A Screen which the player can pass onto a Game. Every Game has one Screen, making it easy to
 * handle multiple screens for a game. Has a GameLoop, which is started as soon as the Screen is
 * created.
 *
 * Drawing is done on a javafx Canvas. The Screen creates a root Group node, the canvas is inserted
 * into the Group and then the Group is set to be the root of this Scene.
 */
public abstract class Screen extends Scene {
    /** A list of GameObjects which are handled by the Screen*/
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    /** A list of all the particles handled by this Screen*/
    private ArrayList<Particle> particles = new ArrayList<>();
    /** All the TMXMap tiles handled by this Screen*/
    private ArrayList<Tile> tiles = new ArrayList<>();
    /** The tiled map for this screen*/
    private TMXMap tmxMap;
    /** Every Screen has one listener*/
    private InputListener listener;
    /** The host Game object where this screen was created*/
    private Game host;
    /** An AnimationTimer, which handles the game loop*/
    private GameLoop gameLoop;
    /** The music in the scene*/
    private Music music;
    /** The root Node object, which is set to the Scene as root*/
    private Group root;
    /** The javafx canvas, which handles all the drawing*/
    private Canvas canvas;
    /** The GraphicsContext of the canvas*/
    private GraphicsContext gc;
    /** The camera for the Screen*/
    private Camera2D camera2D;

    /**
     * Constructor for Screen. Initializes the parameters and starts the game loop
     *
     * @param width The width of the scene
     * @param height The height of the scene
     * @param host The Game host object where the scene is located
     */
    public Screen(double width, double height, Game host) {
        super(new Group(), width, height);
        setGameObjects(new ArrayList<>());
        canvas = new Canvas(width, height);
        setGc(canvas.getGraphicsContext2D());
        setHost(host);
        initRoot();
        setGameLoop(new GameLoop(this));
        setCamera2D(new Camera2D(getWidth(), getHeight(), this));
        gameLoop.start();
    }

    public Screen() {
        super(new Group(), 640, 480);
    }

    /**
     * Initialises the Group root of this scene
     */
    private void initRoot() {
        root = new Group();
        root.getChildren().add(canvas);
        setRoot(root);
    }

    /**
     * Initializes the input listener if one is present
     */
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

    /**
     * Abstract update method which called in the game loop.
     */
    abstract public void update();

    /**
     * Sets the tiled map for this Screen. Also adds the tiles and objects to the screen
     *
     * @param map The tiled map to add
     */
    public void addTiledMap(TMXMap map) {
        tmxMap = map;
        map.getLayers().forEach(e -> tiles.addAll(e.getLayerTiles()));
    }

    /**
     * Draws and moves the particles
     */
    public void handleParticles() {
        particles.forEach(e -> {
            e.draw(gc);
            e.moveAlternative();
        });
    }

    /**
     * Adds an input listener to this Screen
     *
     * @param listener The listener to add
     */
    public void addListener(InputListener listener) {
        this.listener = listener;
        initInput();
    }

    /**
     * Clears the GraphicsContext
     */
    public void clearScreen() {
        getGc().clearRect(-getGc().getTransform().getTx(), -getGc().getTransform().getTy(), getWidth(), getHeight());
    }

    /**
     * Adds a GameObject to this screen
     *
     * @param gameObject The GameObject to add
     */
    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    /**
     * Adds an ArrayList of GameObjects to this screen
     *
     * @param objects The array to add
     */
    public void addGameObjects(ArrayList<GameObject> objects) {
        gameObjects.addAll(objects);
        //objects.forEach(e -> root.getChildren().add(e.getNode()));
    }

    /**
     * Removes a GameObject from this screen
     *
     * @param gameObject The GameObject to remove
     */
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);

        //root.getChildren().remove(gameObject.getNode());
    }

    /**
     * Removes an ArrayList of GameObjects from this screen
     *
     * @param objects The objects to remove
     */
    public void removeGameObjects(ArrayList<GameObject> objects) {
        gameObjects.removeAll(objects);
    }


    // SETTERS AND GETTERS


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

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public TMXMap getTmxMap() {
        return tmxMap;
    }

    public void setTmxMap(TMXMap tmxMap) {
        this.tmxMap = tmxMap;
    }

    public Camera2D getCamera2D() {
        return camera2D;
    }

    public void setCamera2D(Camera2D camera2D) {
        this.camera2D = camera2D;
    }

    public void destroy() {
        gameLoop.stop();
    }
}
