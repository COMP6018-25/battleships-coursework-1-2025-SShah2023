package model;
public class GridCell {
    private Ship ship;
    private boolean isGuessed;

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean attemptHit() {
        if (!isGuessed) {
            isGuessed = true;
            return ship != null && ship.hit();
        }
        return false;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public Ship getShip() {
        return ship != null ? ship : new Ship(0); // Return a placeholder ship instead of null
    }

    @Override
    public String toString() {
        return isGuessed ? (hasShip() ? "H" : "M") : "~";
    }
}
