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
            int row = getValidInput("Enter row (0-9): ");
            int col = getValidInput("Enter column (0-9): ");

            boolean hit = game.makeGuess(row, col);
            System.out.println(hit ? "Hit!" : "Miss!");

            if (game.isGameOver()) {
                System.out.println("Game Over! You sunk all the ships!");
                break;
            }
        }

        scanner.close();
    }

    private int getValidInput(String prompt) {
        int input = -1;
        while (input < 0 || input >= 10) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
            } else {
                System.out.println("Invalid input! Enter a number between 0-9.");
                scanner.next();
            }
        }
        return input;
    }

    private void displayBoard() {
        System.out.println("\n Battleship Board");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (game.getBoard()[i][j].isGuessed()) {
                    if (game.getBoard()[i][j].hasShip()) {
                        System.out.print(" H ");
                    } else {
                        System.out.print(" M ");
                    }
                } else {
                    System.out.print(" ~ ");
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
