package org.example;

public enum Distance {
    LONG(300),
    MIDDLE(200),
    SHORT(100);
    private int dist;
    public int getDist() {
        return dist;
    }
    Distance(int dist) {
        this.dist = dist;
    }
}