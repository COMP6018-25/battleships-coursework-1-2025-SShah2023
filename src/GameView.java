import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

public class GameView extends JFrame implements Observer {
    private JButton[][] buttons;
    private JLabel statusLabel;
    private GameController controller;

    public GameView() {
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
                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> controller.handleGuess(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Select a cell to attack!", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> controller.resetGame());
        add(resetButton, BorderLayout.SOUTH);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void updateBoard(int row, int col, boolean hit) {
        buttons[row][col].setText(hit ? "H" : "M");
        buttons[row][col].setEnabled(false);
    }

    public void displayMessage(String message) {
        statusLabel.setText(message);
    }

    public void displayGameOver() {
        statusLabel.setText("Game Over! You won!");
    }
    public void resetBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setText("~");
                buttons[i][j].setEnabled(true);
            }
        }
    }
    public void disableCell(int row, int col) {
        buttons[row][col].setEnabled(false);
    }


    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
