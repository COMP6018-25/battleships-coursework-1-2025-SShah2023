package model;
public class Ship {
    private final int length;
    private final boolean[] hits;
    private final String name;

    public Ship(int length, String name) {
        assert length > 0 : "Ship length must be positive";
        this.length = length;
        this.name = name;
        this.hits = new boolean[length];
    }
    public Ship(int length) {
        this(length, "Unnamed Ship");
    }
    public boolean hit() {
        for (int i = 0; i < hits.length; i++) {
            if (!hits[i]) {
                hits[i] = true;
                return true;
            }
        }
        assert isSunk() : "Trying to hit an already sunk ship!";
        return false;
    }
    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }

    public int getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name + " (" + length + ")";
    }
}
