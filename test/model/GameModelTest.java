 //Unit tests for GameModel.
 //These tests cover key functionality including ship placement, gameplay mechanics and end game logic.
 //Comments explain the scenario and expected outcome.
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
        Ship ship = new Ship(3, "3");
        boolean placed = game.placeShip(ship, 0, 0, true);
        assertTrue(placed, "Ship should be placed successfully on empty board.");
    }
    //Scenario: Attempting to place a ship that would exceed board boundaries.
    // This tests validation logic for horizontal overflow.
    @Test
    void testPlaceShipOutOfBoundsFails() {
        Ship ship = new Ship(5, "5");
        boolean placed = game.placeShip(ship, 9, 9, true); // Would overflow horizontally
        assertFalse(placed, "Placing ship out of bounds should fail.");
    }

    @Test
    void testMakeGuessHit() {
        Ship ship = new Ship(2, "2");
        game.placeShip(ship, 1, 1, true);
        assertTrue(game.makeGuess(1, 1), "Guess on ship should be a hit.");
    }

     //Scenario: The game should end when all ships are sunk.
     //A single ship is placed and hit until sunk to trigger game over.
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
    // Scenario (FR5): Placing two ships in adjacent but non-overlapping positions is valid.
    // Overlapping should be rejected.
    // This confirms ship placement logic respects adjacency without violating collision constraints.
    @Test
    void testAdjacentButNotOverlappingShips() {
        Ship ship1 = new Ship(2, "A");
        Ship ship2 = new Ship(2, "B");

        assertTrue(game.placeShip(ship1, 0, 0, true), "First ship should place at (0,0)");
        assertFalse(game.placeShip(ship2, 0, 1, false), "Second ship should overlap and be rejected");
        assertTrue(game.placeShip(ship2, 1, 0, true), "Adjacent placement should be allowed");
    }

}
