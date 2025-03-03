package model;
public class Ship {
    private final int length;
    private int hitCount = 0;

    public Ship(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Ship length must be greater than zero.");
        }
        this.length = length;
    }

    public boolean hit() {
        if (hitCount < length) {
            hitCount++;
            return true;
        }
        return false;
    }

    public boolean isSunk() {
        return hitCount == length;
    }

    public int getLength() {
        return length;
    }
}
