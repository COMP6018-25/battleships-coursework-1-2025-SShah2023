import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.GameModel;
import model.GridCell;

public class GameView extends JFrame {
    private JButton[][] buttons;
    private GameModel game;
    private JLabel statusLabel;

    public GameView() {
        game = new GameModel();
        buttons = new JButton[10][10];

        setTitle("Battleship Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton("~");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Select a cell to attack!", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);
    }

    private void resetGame() {
        game = new GameModel();
        statusLabel.setText("Game Reset! Select a cell to attack.");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setText("~");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.getBoard()[row][col].isGuessed()) {
                boolean hit = game.makeGuess(row, col);
                buttons[row][col].setText(hit ? "H" : "M");
                buttons[row][col].setEnabled(false);
                statusLabel.setText(hit ? "Hit!" : "Miss!");

                if (game.isGameOver()) {
                    statusLabel.setText("Game Over! You won!");
                    disableBoard();
                }
            } else {
                statusLabel.setText("You've already guessed this spot!");
            }
        }
    }

    private void disableBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameView gameView = new GameView();
            gameView.setVisible(true);
        });
    }
}
