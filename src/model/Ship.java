package model;

//Represents a single ship in the Battleship game.
//Tracks its length, name, and which segments have been hit.

public class Ship {
    private final int length;
    private final boolean[] hits;
    private final String name;
    //Constructs a named ship with a given length.
    public Ship(int length, String name) {
        assert length > 0 : "Ship length must be positive"; // Precondition
        this.length = length;
        this.name = name;
        this.hits = new boolean[length];
        assert hits.length == length : "Hit array must match ship length"; // Post condition
    }
    //Constructs a default unnamed ship with the given length.
    public Ship(int length) {
        this(length, "Unnamed Ship");
    }
    //Registers a hit on the next unhit segment of the ship.
    public boolean hit() {
        // a ship must have at least one un-hit segment before this is called
        int unhitCount = 0;
        for (boolean h : hits) if (!h) unhitCount++;
        assert unhitCount > 0 : "hit() called on already sunk ship";

        for (int i = 0; i < hits.length; i++) {
            if (!hits[i]) {
                hits[i] = true;

                // Post condition. hit[i] must now be true
                assert hits[i] : "Failed to mark hit on ship segment";
                return true;
            }
        }

        return false;
    }
    //Checks if all segments of the ship have been hit.
    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }

        // Post condition. all hits must be true
        for (boolean hit : hits) {
            assert hit : "isSunk() returned true but not all segments are hit";
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
