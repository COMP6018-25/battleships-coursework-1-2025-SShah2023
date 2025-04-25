package model;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameModel extends Observable {
    private static final int BOARD_SIZE = 10;
    private final GridCell[][] board = new GridCell[BOARD_SIZE][BOARD_SIZE];
    private final List<Ship> ships = new ArrayList<>();
    private final List<Ship> sunkShips = new ArrayList<>();

    private int totalGuesses = 0;

    public GameModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new GridCell();
            }
        }
        initializeShips();
        placeShipsRandomly();
    }

    private void initializeShips() {
        ships.clear();
        ships.add(new Ship(5, "5"));
        ships.add(new Ship(4, "4"));
        ships.add(new Ship(3, "3"));
        ships.add(new Ship(2, "2"));
        ships.add(new Ship(2, "2"));
    }

    private void placeShipsRandomly() {
        for (Ship ship : ships) {
            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < 100) {
                int row = (int) (Math.random() * BOARD_SIZE);
                int col = (int) (Math.random() * BOARD_SIZE);
                boolean horizontal = Math.random() < 0.5;
                placed = placeShip(ship, row, col, horizontal);
                attempts++;
            }
        }
    }

    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) return false;

        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            if (x >= BOARD_SIZE || y >= BOARD_SIZE || board[x][y].hasShip()) {
                return false;
            }
        }

        for (int i = 0; i < ship.getLength(); i++) {
            int x = row + (horizontal ? 0 : i);
            int y = col + (horizontal ? i : 0);
            board[x][y].setShip(ship);
        }
        return true;
    }

    public boolean loadShipsFromFile(String filePath) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new GridCell();
            }
        }
        ships.clear();
        sunkShips.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != 5) {
                    System.err.println("Invalid format: " + line);
                    return false;
                }

                String name = parts[0];
                int length = Integer.parseInt(parts[1]);
                int row = Integer.parseInt(parts[2]);
                int col = Integer.parseInt(parts[3]);
                boolean horizontal = parts[4].equalsIgnoreCase("H");

                Ship ship = new Ship(length, name);
                boolean placed = placeShip(ship, row, col, horizontal);
                if (!placed) {
                    System.err.println("Failed to place ship: " + name + " at " + row + "," + col);
                    return false;
                }
                ships.add(ship);
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading file: " + e.getMessage());
            return false;
        }
    }

    public boolean makeGuess(int row, int col) {
        if (!board[row][col].isGuessed()) {
            boolean hit = board[row][col].attemptHit();
            totalGuesses++;

            if (hit) {
                Ship ship = board[row][col].getShip();
                if (ship.isSunk() && !sunkShips.contains(ship)) {
                    sunkShips.add(ship);
                    if (isGameOver()) {
                        setChanged();
                        notifyObservers("GAME_OVER:" + totalGuesses);
                    } else {
                        setChanged();
                        notifyObservers("You sunk ship " + ship.getName() + "!");
                    }
                } else {
                    setChanged();
                    notifyObservers("Hit!");
                }
            } else {
                setChanged();
                notifyObservers("Miss!");
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
