package com.banzneri.PlatformGame;

import com.banzneri.audio.Sound;
import com.banzneri.graphics.GameObject;
import com.banzneri.graphics.Sprite;
import com.banzneri.graphics.Texture;
import com.banzneri.input.InputListener;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Player extends Sprite implements InputListener {
    private boolean walking = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean grounded = false;
    private boolean spaceDOwn = false;
    private boolean jumped = false;
    private boolean shooting = false;
    private boolean running = false;
    private boolean canClimb = false;
    private boolean climbing = false;
    private Texture standTexture;
    private Texture walkAnim;
    private Texture jumpTexture;
    private Texture shootRunAnim;
    private Texture shootStand;
    private Texture climbTexture;
    private Rectangle collisionBox;
    private MyScreen host;
    private Timeline shootTime = new Timeline(new KeyFrame(Duration.millis(200), e -> shoot()));
    private Sound laserSound = new Sound("laser.mp3");
    private PauseTransition shootPause = new PauseTransition(Duration.millis(200));

    public Player(double x, double y, MyScreen host) {
        super(x, y, 64, 64, new Texture("/Hat_man1.png"));
        standTexture  = new Texture("/stand.png");
        walkAnim = new Texture("/walk.gif");
        jumpTexture = new Texture("/jumping.png");
        shootRunAnim = new Texture("/shootrun.gif");
        shootStand = new Texture(("/shooting.png"));
        climbTexture = new Texture("/climb.gif");
        collisionBox = new Rectangle(x + 6, y + 6, 20, 20);
        setHost(host);
        shootTime.setCycleCount(Animation.INDEFINITE);
        shootPause.setOnFinished(e -> {
            shooting = false;
        });
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isVisible()) {
            gc.save();
            if(climbing) {
                setTexture(climbTexture);
            } else if(!grounded) {
                setTexture(jumpTexture);
            } else if(shooting) {
                if(isMovingLeft() || isMovingRight()) {
                    setTexture(shootRunAnim);
                } else {
                    setTexture(shootStand);
                }
            }
            else if(isMovingLeft() || isMovingRight()) {
                setTexture(walkAnim);
            } else {
                setTexture(standTexture);
            }
            gc.transform(new Affine(new Rotate(getRotation(), getX() + getPivotX(), getY() + getPivotY())));
            if(isFlipped()) {
                gc.drawImage(getTexture().getImage(), 0, 0, getTexture().getImage().getWidth(), getTexture().getImage().getHeight(), getX() + getWidth(),getY(), -getWidth(), getHeight());
            } else {
                gc.drawImage(getTexture().getImage(), getX(), getY(), getWidth(), getHeight());
            }
            gc.restore();
        }
    }

    public void moveAlternative() {
        if(!canClimb) {
            climbing = false;
        }
        getForces().forEach(e -> {
            getAcceleration().add(e);
        });
        getSpeed().add(getAcceleration());
        if(running) {
            setSpeedX(getSpeedX() * 2);
        }
        else if(climbing) {
            setSpeedY(-2);
        }
        getLocation().add(getSpeed());
        setX(getLocation().x);
        setY(getLocation().y);
        getAcceleration().multiply(0);
    }

    public void shoot() {
        Bullet bullet = new Bullet(getX() + getWidth() / 2, getY() + getHeight() / 2);
        if(isFlipped()) {
            bullet.setSpeedX(-20);
            bullet.setX(bullet.getX() - 10);
        } else {
            bullet.setSpeedX(20);
            bullet.setX(bullet.getX() + 10);
        }
        getHost().getBullets().add(bullet);
        laserSound.play();
    }

    public boolean isDown(GameObject object) {
        double y = getMaxY() + Math.abs(getSpeedY());
        return object.contains(getMinX() + 5, y) || object.contains(getMaxX() - 5, y);
    }

    public boolean isLeft(GameObject object) {
        double x = getMinX() - Math.abs(getSpeedX());
        return object.contains(x, getMinY() + 5) || object.contains(x, getMaxY() - 5);
    }

    public boolean isRight(GameObject object) {
        double x = getMaxX() + Math.abs(getSpeedX());
        return object.contains(x, getMinY() + 5) || object.contains(x, getMaxY() - 5);
    }

    public boolean isUp(GameObject object) {
        double y = getMinY() - Math.abs(getSpeedY());
        return object.contains(getMinX(), y) || object.contains(getMaxX(), y);
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingDown() {
        return getSpeedY() > 0;
    }

    public boolean isMovingUp() {
        return getSpeedY() < 0;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isSpaceDOwn() {
        return spaceDOwn;
    }

    public void setSpaceDOwn(boolean spaceDOwn) {
        this.spaceDOwn = spaceDOwn;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    private boolean isMoving() {
        return Math.abs(getSpeedX()) != 0 || Math.abs(getSpeedY()) != 0;
    }

    private void handleJumping() {
        if(grounded && !spaceDOwn) {
            grounded = false;
            jumped = true;
            setSpeedY(-9);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case A:
            case LEFT:  movingLeft = true; break;
            case D:
            case RIGHT: movingRight = true; break;
            case SPACE: handleJumping();
                        spaceDOwn = true;
                        break;
            case W:     if(canClimb) {
                            climbing = true;
                        }
                        break;
            case SHIFT: if(isGrounded()) {
                            running = true;
                        }
                        break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent e) {
        switch (e.getCode()) {
            case A:
            case LEFT:  movingLeft = false; break;
            case D:
            case RIGHT: movingRight = false; break;
            case SPACE: spaceDOwn = false;
                        if(getSpeedY() < -1) {
                            setSpeedY(-1);
                        }
                        break;
            case W:     climbing = false;
                        break;
            case SHIFT: running = false;
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if(e.getButton().equals(MouseButton.PRIMARY)) {
            shooting = true;
            shootTime.play();
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if(e.getButton().equals(MouseButton.PRIMARY)) {
            shoot();
            shootPause.play();
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if(e.getButton().equals(MouseButton.PRIMARY)) {
            shootTime.stop();
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e) { }

    @Override
    public void onMouseDragStart(MouseDragEvent mouseDragEvent) { }

    @Override
    public void onMouseDragEnd(MouseDragEvent mouseDragEvent) { }

    @Override
    public void onMouseDragged(MouseEvent mouseEvent) { }

    public MyScreen getHost() {
        return host;
    }

    public void setHost(MyScreen host) {
        this.host = host;
    }

    public Texture getStandTexture() {
        return standTexture;
    }

    public void setStandTexture(Texture standTexture) {
        this.standTexture = standTexture;
    }

    public Texture getWalkAnim() {
        return walkAnim;
    }

    public void setWalkAnim(Texture walkAnim) {
        this.walkAnim = walkAnim;
    }

    public Texture getJumpTexture() {
        return jumpTexture;
    }

    public void setJumpTexture(Texture jumpTexture) {
        this.jumpTexture = jumpTexture;
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public void setCanClimb(boolean canClimb) {
        this.canClimb = canClimb;
    }
}
