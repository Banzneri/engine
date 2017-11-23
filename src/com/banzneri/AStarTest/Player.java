package com.banzneri.AStarTest;

import com.banzneri.geometry.Rect;
import com.banzneri.geometry.Vector2d;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Player extends Rect {
    private ArrayList<Node> path = new ArrayList<>();
    private Node currentTarget;
    private Node destination;

    public Player(double x, double y) {
        super(x, y, 32 ,32);
    }

    @Override
    public void moveAlternative() {
        super.moveAlternative();
        if (currentTarget != null) {
            if(getX() == currentTarget.location.x && getY() == currentTarget.location.y) {
                path.remove(currentTarget);
                if(!path.isEmpty()) {
                    currentTarget = path.get(0);
                    approachNode(currentTarget);
                } else if (!currentTarget.equals(destination)){
                    currentTarget = destination;
                    approachNode(currentTarget);
                }

                if(getX() == destination.location.x && getY() == destination.location.y) {
                    setSpeedX(0);
                    setSpeedY(0);
                    currentTarget = null;
                }
            }
        }
    }

    public void approachNode(Node node) {
        Point2D destination = new Point2D(node.location.x, node.location.y);
        Point2D playerPos = new Point2D(getX(), getY());
        Point2D difference = destination.subtract(playerPos);
        Point2D direction = difference.normalize();

        setSpeedX(direction.getX());
        setSpeedY(direction.getY());
    }

    public void startPathfinding(ArrayList<Node> path, Node destination) {
        this.path = path;
        this.destination = destination;
        currentTarget = path.get(0);
        approachNode(currentTarget);
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }

    public Node getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Node currentTarget) {
        this.currentTarget = currentTarget;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }
}
