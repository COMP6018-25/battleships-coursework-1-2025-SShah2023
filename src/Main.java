import model.Ship;
import model.GameModel;
public class Main {
    public static void main(String[] args) {
        // Test Ship Class
        Ship ship = new Ship(3);
        System.out.println("Ship created with length: " + ship.getLength());

        // Test GameModel Class
        GameModel game = new GameModel();
        System.out.println("GameModel initialized.");

        // Test ship placement
        System.out.println("Placing a ship at (2,3)...");
        boolean placed = game.placeShip(new Ship(3), 2, 3, true);
        System.out.println(placed ? "Ship placed successfully!" : "Failed to place ship.");

        // Test making a guess
        System.out.println("Making a guess at (2,3)...");
        boolean hit = game.makeGuess(2, 3);
        System.out.println(hit ? "Hit!" : "Miss!");

        System.out.println("Making a guess at (5,5)...");
        hit = game.makeGuess(5, 5);
        System.out.println(hit ? "Hit!" : "Miss!");
    }
}
