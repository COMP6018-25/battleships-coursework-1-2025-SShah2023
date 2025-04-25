import model.GameModel;
import model.GridCell;
import model.Ship;

public class AssertionTests {
    public static void main(String[] args) {
        System.out.println("Running assertions...");

        // Ship Assertions
        Ship s1 = new Ship(3, "Test Ship");
        assert s1.getLength() == 3 : "Ship length should be 3";
        assert !s1.isSunk() : "New ship should not be sunk";
        s1.hit();
        s1.hit();
        s1.hit();
        assert s1.isSunk() : "Ship should be sunk after 3 hits";

        // GridCell Assertions
        GridCell cell = new GridCell();
        assert !cell.isGuessed() : "Cell should not be guessed by default";
        assert !cell.hasShip() : "Cell should not have a ship by default";
        cell.setShip(new Ship(1, "Tiny"));
        assert cell.hasShip() : "Cell should have a ship after setting one";

        // GameModel Assertions
        GameModel game = new GameModel();
        assert game.getBoard().length == 10 : "Board should have 10 rows";
        assert game.getBoard()[0].length == 10 : "Each row should have 10 columns";
        assert game.getTotalGuesses() == 0 : "Initial guesses should be 0";

        System.out.println("All assertions passed!");
    }
}
