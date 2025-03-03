package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private static final int BOARD_SIZE = 10;
    private final GridCell[][] board = new GridCell[BOARD_SIZE][BOARD_SIZE];
    private final List<Ship> ships = new ArrayList<>();
    private int totalGuesses = 0;

    public GameModel() {
        // Initialize board with empty GridCells
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new GridCell();
            }
        }
        initializeShips();
        placeShipsRandomly();
    }

    // Create ships and add to list
    private void initializeShips() {
        ships.add(new Ship(5));
        ships.add(new Ship(4));
        ships.add(new Ship(3));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
    }

    // Randomly place ships on the board
    private void placeShipsRandomly() {
        Random random = new Random();
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);
                boolean horizontal = random.nextBoolean();
                placed = placeShip(ship, row, col, horizontal);
            }
        }
    }

    // Place a ship at a specific location
    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        if (ship == null || row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
            return false; // Validate inputs
        }

        // Check if placement is valid (no overlaps)
        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            if (x >= BOARD_SIZE || y >= BOARD_SIZE || board[x][y].hasShip()) {
                return false;
            }
        }

        // Place ship
        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            board[x][y].setShip(ship);
        }
        return true;
    }

    // Handle a player's guess
    public boolean makeGuess(int row, int col) {
        if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
            System.out.println("Invalid move: Out of bounds.");
            return false;
        }
        if (!board[row][col].isGuessed()) {
            boolean hit = board[row][col].attemptHit();
            totalGuesses++;
            System.out.println(hit ? "Hit!" : "Miss!");

            // Check if game is over
            if (isGameOver()) {
                System.out.println("Game Over! All ships have been sunk in " + totalGuesses + " guesses.");
            }

            return hit;
        }
        System.out.println("Already guessed!");
        return false;
    }

    // Check if all ships are sunk
    public boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false; // Game is not over if any ship is still floating
            }
        }
        return true;
    }

    // Get the board (for debugging or GUI purposes)
    public GridCell[][] getBoard() {
        return board;
    }

    // Get total number of guesses made
    public int getTotalGuesses() {
        return totalGuesses;
    }
}
