import model.GameModel;
import java.util.Scanner;

public class BattleShipCLI {
    private GameModel game;
    private Scanner scanner;

    public BattleShipCLI() {
        game = new GameModel();
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Welcome to Battleship!");
        System.out.println("Enter your guesses in the format: row col (e.g., 2 3)");

        while (!game.isGameOver()) {
            displayBoard();
            int[] coordinates = getValidInput();
            int row = coordinates[0];
            int col = coordinates[1];


            if (game.getBoard()[row][col].isGuessed()) {
                System.out.println("You've already guessed this spot! Try again.");
                continue;
            }

            boolean hit = game.makeGuess(row, col);
            System.out.println(hit ? "Hit!" : "Miss!");

            if (game.isGameOver()) {
                System.out.println("Game Over! You sunk all the ships!");
                break;
            }

        }

        scanner.close();
    }

    private int[] getValidInput() {
        String input;
        int row = -1, col = -1;

        while (true) {
            System.out.print("Enter coordinate (A1 - J10): ");
            input = scanner.next().toUpperCase();

            if (input.length() < 2 || input.length() > 3) {
                System.out.println("Invalid format! Use A1 - J10.");
                continue;
            }

            char rowChar = input.charAt(0);
            if (rowChar < 'A' || rowChar > 'J') {
                System.out.println("Invalid row! Use A-J.");
                continue;
            }
            row = rowChar - 'A';

            try {
                col = Integer.parseInt(input.substring(1)) - 1;
                if (col < 0 || col > 9) {
                    System.out.println("Invalid column! Use 1-10.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid column! Use 1-10.");
                continue;
            }

            break;
        }

        return new int[]{row, col};
    }

    private void displayBoard() {
        System.out.println("Battleship Board ");
        System.out.println("   1 2 3 4 5 6 7 8 9 10");

        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + "  ");
            for (int j = 0; j < 10; j++) {
                if (game.getBoard()[i][j].isGuessed()) {
                    System.out.print(game.getBoard()[i][j].hasShip() ? "H " : "M ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        BattleShipCLI game = new BattleShipCLI();
        game.startGame();
    }
}
