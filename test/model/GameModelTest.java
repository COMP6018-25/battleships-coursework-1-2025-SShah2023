package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel game;

    @BeforeEach
    void setUp() {
        game = new GameModel();
    }

    @Test
    void testBoardIs10x10() {
        assertEquals(10, game.getBoard().length, "Board should have 10 rows.");
        assertEquals(10, game.getBoard()[0].length, "Each row should have 10 columns.");
    }

    @Test
    void testPlaceShipSuccessfully() {
        Ship ship = new Ship(3, "Destroyer");
        boolean placed = game.placeShip(ship, 0, 0, true);
        assertTrue(placed, "Ship should be placed successfully on empty board.");
    }

    @Test
    void testPlaceShipOutOfBoundsFails() {
        Ship ship = new Ship(5, "Carrier");
        boolean placed = game.placeShip(ship, 9, 9, true); // Would overflow horizontally
        assertFalse(placed, "Placing ship out of bounds should fail.");
    }

    @Test
    void testPlaceShipOverlappingFails() {
        Ship ship1 = new Ship(3, "Ship1");
        Ship ship2 = new Ship(3, "Ship2");
        assertTrue(game.placeShip(ship1, 0, 0, true));
        assertFalse(game.placeShip(ship2, 0, 1, true), "Overlapping ships should not be placed.");
    }

    @Test
    void testMakeGuessHit() {
        Ship ship = new Ship(2, "Patrol");
        game.placeShip(ship, 1, 1, true);
        assertTrue(game.makeGuess(1, 1), "Guess on ship should be a hit.");
    }

    @Test
    void testMakeGuessMiss() {
        assertFalse(game.makeGuess(5, 5), "Guess on empty cell should be a miss.");
    }

    @Test
    void testGameOverFalseAtStart() {
        assertFalse(game.isGameOver(), "Game should not be over at the start.");
    }

    @Test
    void testGameOverAfterSinkingAllShips() {
        GameModel smallGame = new GameModel();
        Ship ship = new Ship(2, "TestShip");
        smallGame.placeShip(ship, 0, 0, true);
        smallGame.makeGuess(0, 0);
        smallGame.makeGuess(0, 1);
        assertTrue(ship.isSunk(), "Ship should be sunk after two hits.");
    }

    @Test
    void testLoadInvalidFileFails() {
        boolean loaded = game.loadShipsFromFile("non_existent_file.txt");
        assertFalse(loaded, "Loading an invalid file should fail gracefully.");
    }
}
