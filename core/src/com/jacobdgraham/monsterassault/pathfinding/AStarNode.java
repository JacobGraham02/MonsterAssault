package com.jacobdgraham.monsterassault.pathfinding;

public class AStarNode {
    private final int x, y;
    private final AStarNode parent;
    private final float cost;
    private final float heuristic;

    public AStarNode(final int x, final int y, final AStarNode parent, final float cost, final float heuristic) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public AStarNode getParent() {
        return parent;
    }

    public float getCost() {
        return cost;
    }

    public float getTotalCost() {
        return cost + heuristic;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AStarNode aStarNode = (AStarNode) obj;
        return x == aStarNode.x && y == aStarNode.y;
    }

    @Override
    public int hashCode() {
        return 32 * x + y;
    }
}
