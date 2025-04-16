import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;
import model.GridCell;

public class GameView extends JFrame implements Observer {
    private JButton[][] buttons;
    private JLabel statusLabel;
    private GameController controller;
    private JButton[][] gridButtons = new JButton[10][10];
    private JLabel eventLabel;

    public GameView() {
        buttons = new JButton[10][10];

        setTitle("Battleship Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(11, 11));
        boardPanel.add(new JLabel(""));
        for (int i = 0; i < 10; i++) {
            JLabel colLabel = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            boardPanel.add(colLabel);
        }

        for (int row = 0; row < 10; row++) {
            JLabel rowLabel = new JLabel(String.valueOf((char) ('A' + row)), SwingConstants.CENTER);
            boardPanel.add(rowLabel);

            for (int col = 0; col < 10; col++) {
                buttons[row][col] = new JButton("~");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 16));
                final int r = row, c = col;
                buttons[row][col].addActionListener(e -> controller.handleGuess(r, c));
                boardPanel.add(buttons[row][col]);
            }
        }


        statusLabel = new JLabel("Select a cell to attack!", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        eventLabel = new JLabel(" ");
        eventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventLabel.setFont(new Font("Arial", Font.BOLD, 14));
        eventLabel.setForeground(Color.MAGENTA);
        add(eventLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> controller.resetGame());
        add(resetButton, BorderLayout.SOUTH);
        JButton loadButton = new JButton("Load Ships");
        loadButton.addActionListener(e -> controller.loadShipsFromFile());
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(resetButton);
        bottomPanel.add(loadButton);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void updateBoard(int row, int col, boolean hit) {
        assert row >= 0 && row < 10 && col >= 0 && col < 10 : "updateBoard: out of bounds";
        buttons[row][col].setText(hit ? "H" : "M");
        buttons[row][col].setEnabled(false);
    }

    public void displayMessage(String message) {
        statusLabel.setText(message);
    }

    public void displayGameOver(int guesses) {
        statusLabel.setText("Game Over! You won!"+ guesses +"guesses");
    }
    public void showSunkShip(String shipName) {
        eventLabel.setText("You sunk the " + shipName + "!");
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
        if (arg instanceof String) {
            displayMessage((String) arg);
        }
    }

    public void refreshBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                GridCell cell = controller.getGameModel().getBoard()[row][col];
                JButton button = gridButtons[row][col];
                if (cell.isGuessed()) {
                    if (cell.hasShip()) {
                        button.setBackground(Color.RED);
                    } else {
                        button.setBackground(Color.BLUE);
                    }
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
    }