package view;
import controller.GameController;
import model.GridCell;
import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

//GUI-based View for the Battleship game (FR3).
//Renders a 10x10 grid with labels, displays hits/misses and game messages.
//listens to model updates via the Observer pattern.

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
        // Create and render the game board with coordinate labels (A–J and 1–10)
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
        // Message display area (top)
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel("Select a cell to attack!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(statusLabel);

        eventLabel = new JLabel(" ");
        eventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eventLabel.setFont(new Font("Arial", Font.BOLD, 14));
        eventLabel.setForeground(Color.MAGENTA);
        topPanel.add(eventLabel);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        // Reset/load buttons (bottom)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> {
            this.dispose();
            BattleShipGUI.showStartupScreen();
        });
        JButton loadButton = new JButton("Load Ships");
        loadButton.addActionListener(e -> {
            this.dispose();
            BattleShipGUI.showStartupScreen();
        });


        bottomPanel.add(resetButton);
        bottomPanel.add(loadButton);
        add(bottomPanel, BorderLayout.SOUTH);

    }
    //Sets the controller and registers this view as an observer to the model.
    public void setController(GameController controller) {
        this.controller = controller;
        controller.getGameModel().addObserver(this);
    }
   // Updates a single cell on the board with a hit (H) or miss (M).
    public void updateBoard(int row, int col, boolean hit) {
        assert row >= 0 && row < 10 && col >= 0 && col < 10 : "updateBoard: out of bounds";
        buttons[row][col].setText(hit ? "H" : "M");
        buttons[row][col].setEnabled(false);
    }

    public void displayMessage(String message) {
        statusLabel.setText(message);
    }

    public void displayGameOver(int guesses) {
        displayMessage("Game Over! You won in " + guesses + " guesses!");
    }

    public void showSunkShip(String shipName) {
        eventLabel.setText("You sunk the " + shipName + "!");
    }
    //Resets the entire board UI to the default state.
    public void resetBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setText("~");
                buttons[i][j].setEnabled(true);
            }
        }
        eventLabel.setText(" ");
        statusLabel.setText("Select a cell to attack!");
    }

    public void disableCell(int row, int col) {
        buttons[row][col].setEnabled(false);
    }
    //Refreshes the color of all cells based on hit/miss state.
    //Color code: RED = hit, BLUE = miss, null = untouched.
    public void refreshBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                GridCell cell = controller.getGameModel().getBoard()[row][col];
                JButton button = buttons[row][col];
                if (cell.isGuessed()) {
                    if (cell.hasShip()) {
                        button.setBackground(Color.RED);
                    } else {
                        button.setBackground(Color.BLUE);
                    }
                } else {
                    button.setBackground(null);
                }
            }
        }
    }
    //Receive messages from the model and updates the view accordingly.
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String message) {
            System.out.println("Observer message: " + message);
            if (message.startsWith("Game Over! All ships sunk in")) {
                try {
                    String[] parts = message.split(" ");
                    int guesses = Integer.parseInt(parts[6]);
                    displayGameOver(guesses);
                } catch (Exception e) {
                    displayMessage(message);
                }
            } else if (message.startsWith("You sunk ship")) {
                showSunkShip(message.substring("You sunk ship ".length()));
            } else {
                displayMessage(message);
            }
            refreshBoard();
        }
    }
}
