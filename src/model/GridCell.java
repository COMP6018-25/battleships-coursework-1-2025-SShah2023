package model;
public class GridCell {
    private Ship ship;
    private boolean isGuessed;

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean attemptHit() {
        if (isGuessed) {
            return false;
        }
        isGuessed = true;
        return ship != null && ship.hit();
    }


    public boolean hasShip() {
        return ship != null;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public Ship getShip() {
        return ship;
    }


    @Override
    public String toString() {
        return isGuessed ? (hasShip() ? "H" : "M") : "~";
    }
}
