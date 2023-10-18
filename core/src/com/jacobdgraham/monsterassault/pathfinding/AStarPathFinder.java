package com.jacobdgraham.monsterassault.pathfinding;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jacobdgraham.monsterassault.pathfinding.AStarNode;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import sun.awt.image.ImageWatched;

public class AStarPathFinder {
    private final TiledMapTileLayer nonCollisionLayer;
    private final int tileWidth, tileHeight;

    private PriorityQueue<AStarNode> openList;
    private LinkedList<AStarNode> path;

    public AStarPathFinder(TiledMap tiledMap) {
        this.nonCollisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("NonCollisionLayer");
        this.tileWidth = nonCollisionLayer.getWidth();
        this.tileHeight = nonCollisionLayer.getHeight();

        openList = new PriorityQueue<>(Comparator.comparing(AStarNode::getTotalCost));
        path = new LinkedList<>();
    }

    public LinkedList<AStarNode> findShortestPath(final int startX, final int startY, final int endX, final int endY) {
        openList.clear();
        path.clear();

        AStarNode startNode = new AStarNode(startX, startY, null, 0, calculateHeuristic(startX, startY, endX, endY));

        openList.add(startNode);

        boolean[][] visited = new boolean[tileWidth][tileHeight];

        while (!openList.isEmpty()) {
            AStarNode current = openList.poll();

            if (current.getX() == endX && current.getY() == endY) {

                while (current.getParent() != null) {
                    path.addFirst(current);
                    current = current.getParent();
                }
                return path;
            }

            visited[current.getX()][current.getY()] = true;

            for (int[] d : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int nx = current.getX() + d[0];
                int ny = current.getY() + d[1];

                if (isValid(nx, ny) && !visited[nx][ny]) {
                    float newCost = current.getCost() + 1;
                    float heuristic = calculateHeuristic(nx, ny, endX, endY);
                    AStarNode neighbor = new AStarNode(nx, ny, current, newCost, heuristic);

                    boolean inOpenList = openList.contains(neighbor);

                    if (!inOpenList || newCost < neighbor.getCost()) {
                        if (inOpenList) {
                            openList.remove(neighbor);
                        }
                        openList.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    private boolean isValid(final int x, final int y) {
        return x >= 0 && y >= 0 && x < tileWidth && y < tileHeight && nonCollisionLayer.getCell(x, y) != null;
    }

    private float calculateHeuristic(int x1, int y1, int x2, int y2) {
        final int dx = Math.abs(x1 - x2);
        final int dy = Math.abs(y1 - y2);

        if (!isValid(x2, y2)) {
            return Float.POSITIVE_INFINITY;
        }

        return dx + dy;
    }
}
