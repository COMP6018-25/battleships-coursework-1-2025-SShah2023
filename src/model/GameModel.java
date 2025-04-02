package model;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

public class GameModel extends Observable {
    private static final int BOARD_SIZE = 10;
    private final GridCell[][] board = new GridCell[BOARD_SIZE][BOARD_SIZE];
    private final List<Ship> ships = new ArrayList<>();
    private final List<Ship> sunkShips = new ArrayList<>();

    private int totalGuesses = 0;

    public GameModel() {
        // Initialize the board with empty GridCells
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new GridCell();
            }
        }
        initializeShips();
        placeShipsRandomly();
    }

    private void initializeShips() {
        ships.add(new Ship(5, "5"));
        ships.add(new Ship(4, "4"));
        ships.add(new Ship(3, "3"));
        ships.add(new Ship(2, "2"));
        ships.add(new Ship(2, "2"));
    }

    private void placeShipsRandomly() {
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = (int) (Math.random() * BOARD_SIZE);
                int col = (int) (Math.random() * BOARD_SIZE);
                boolean horizontal = Math.random() < 0.5;
                placed = placeShip(ship, row, col, horizontal);
            }
        }
    }

    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        assert ship != null : "Ship cannot be null";
        if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) {
            return false;
        }

        // Ensure ship placement does not overlap
        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            if (x >= BOARD_SIZE || y >= BOARD_SIZE || board[x][y].hasShip()) {
                return false;
            }
        }

        // Place ship on the board
        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            board[x][y].setShip(ship);
        }
        return true;
    }

    public boolean makeGuess(int row, int col) {
        assert row >= 0 && row < BOARD_SIZE : "Row out of bounds";
        assert col >= 0 && col < BOARD_SIZE : "Column out of bounds";
        if (!board[row][col].isGuessed()) {
            boolean hit = board[row][col].attemptHit();
            totalGuesses++;

            if (hit) {
                Ship ship = board[row][col].getShip();
                if (ship.isSunk() && !sunkShips.contains(ship)) {
                    sunkShips.add(ship);
                    setChanged();
                    notifyObservers("You sunk the " + ship.getName() + "!");
                } else {
                    setChanged();
                    notifyObservers();
                }
            } else {
                setChanged();
                notifyObservers();
            }

            return hit;
        }
        return false;
    }

    public boolean isGameOver() {
        return ships.stream().allMatch(Ship::isSunk);
    }

    public boolean isShipSunk(Ship ship) {
        return ship.isSunk();
    }

    public GridCell[][] getBoard() {
        return board;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }
}
