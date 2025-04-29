package view;

import controller.GameController;
import model.GameModel;
import javax.swing.*;
import java.awt.*;

public class BattleShipGUI {
    public static void showStartupScreen() {
        SwingUtilities.invokeLater(() -> {
            JFrame startFrame = new JFrame("Battleship");
            startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startFrame.setSize(300, 150);
            startFrame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton randomBtn = new JButton("Play Game");
            JButton loadBtn = new JButton("Load from ships.txt");

            panel.add(randomBtn);
            panel.add(loadBtn);
            startFrame.add(panel);
            startFrame.setVisible(true);

            randomBtn.addActionListener(e -> {
                startFrame.dispose();
                launchGame(new GameModel()); // Random placement
            });

            loadBtn.addActionListener(e -> {
                GameModel model = new GameModel();
                boolean loaded = model.loadShipsFromFile("ships.txt");
                startFrame.dispose();
                if (!loaded) {
                    JOptionPane.showMessageDialog(null, "Failed to load ships. Starting random game.");
                    model = new GameModel();
                }
                launchGame(model);
            });
        });
    }

    private static void launchGame(GameModel model) {
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
        view.setController(controller);
        view.setVisible(true);
    }
}
