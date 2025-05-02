package model;

//Represents a single cell on the Battleship board.
//Stores whether a ship is present, whether it's been guessed.
//handles the hit logic tied to the model state.

public class GridCell {
    private Ship ship;
    private boolean isGuessed;
    //Assigns a ship to this cell
    public void setShip(Ship ship) {this.ship = ship;}
    //Attempts to hit this cell. If already guessed, returns false.
    //If a ship is present and not guessed, marks as guessed and hits the ship.
    public boolean attemptHit() {
        if (isGuessed) {
            return false;
        }
        isGuessed = true;
        return ship != null && ship.hit();
    }
    //Checks if a ship is present in this cell
    public boolean hasShip() {
        return ship != null;
    }
    //Checks if the cell has already been guessed
    public boolean isGuessed() {
        return isGuessed;
    }
    //Returns the ship in the cell (if any)
    public Ship getShip() {
        return ship;
    }

    //H = hit, M = miss, ~ = unknown
    @Override
    public String toString() {
        return isGuessed ? (hasShip() ? "H" : "M") : "~";
    }
}
