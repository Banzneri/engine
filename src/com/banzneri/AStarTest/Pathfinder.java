package com.banzneri.AStarTest;

import com.banzneri.geometry.Vector2d;

import java.util.*;

public class Pathfinder {

    private Map<Vector2d, Node> nodes = new HashMap<>();

    @SuppressWarnings("rawtypes")
    private final Comparator fComparator = new Comparator<Node>() {
        public int compare(Node a, Node b) {
            return Double.compare(a.getFValue(), b.getFValue()); //ascending to get the lowest
        }
    };

    public Pathfinder(List<Node> allNodes) {

        for(Node node : allNodes) {
            nodes.put(node.location, node);
        }
        System.out.println(nodes);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Node> getPath(Node current, Node dest) {

        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        Node destNode = dest;

        Node currentNode = current;
        currentNode.parent = null;
        currentNode.setGValue(0);
        openList.add(currentNode);

        while(!openList.isEmpty()) {

            Collections.sort(openList, this.fComparator);
            currentNode = openList.get(0);

            if (currentNode.location.equals(destNode.location)) {
                System.out.println("LOCATIONFOUND: " + this.calculatePath(destNode));
                return this.calculatePath(destNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            for (MapDirection direction : MapDirection.values()) {
                Vector2d adjPoint = direction.getVectorForDirection(currentNode.location);

                if (!isInsideBounds(adjPoint)) {
                    continue;
                }

                Node adjNode = getNodeByLocation(adjPoint.x, adjPoint.y);
                /*System.out.println("NODEMAP: " + nodes);
                System.out.println("ADJ: " + adjNode);*/
                System.out.println("ADJNODE: " + adjNode);
                if (adjNode.isWall()) {
                    continue;
                }

                System.out.println(closedList);
                System.out.println(!closedList.contains(adjNode));
                if (!closedList.contains(adjNode)) {
                    if (!openList.contains(adjNode)) {
                        adjNode.parent = currentNode;
                        adjNode.calculateGValue(currentNode);
                        adjNode.calculateHValue(destNode);
                        openList.add(adjNode);
                        System.out.println("Openlist add");
                    } else {
                        if (adjNode.gValue < currentNode.gValue) {
                            adjNode.calculateGValue(currentNode);
                            currentNode = adjNode;
                        }
                    }
                }
            }
        }

        return null;
    }

    private ArrayList<Node> calculatePath(Node destinationNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node node = destinationNode;
        while (node.parent != null) {
            path.add(node);
            node = node.parent;
        }
        return path;
    }

    private boolean isInsideBounds(Vector2d point) {
        return point.x >= 0 &&
                point.x < 9600 &&
                point.y >= 0 &&
                point.y < 480;
    }

    private Node getNodeByLocation(double x, double y) {
        Iterator it = nodes.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Vector2d v = (Vector2d) pair.getKey();
            Node node = (Node) pair.getValue();
            if(v.x == x && v.y == y) {
                System.out.println("found node");
                System.out.println(node);
                return node;
            }
            it.remove();
        }

        return null;
    }

}
