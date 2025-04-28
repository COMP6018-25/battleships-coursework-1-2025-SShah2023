import controller.BattleShipCLI;
import view.BattleShipGUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        System.out.println("Select Mode:");
        System.out.println("1. Play CLI version");
        System.out.println("2. Play GUI version");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            BattleShipCLI cli = new BattleShipCLI();
            cli.startGame();
        } else if (choice.equals("2")) {
            // ✅ Corrected: call the main method of BattleShipGUI
            BattleShipGUI.main(new String[]{});
        } else {
            System.out.println("Invalid choice. Exiting...");
        }
    }
}
