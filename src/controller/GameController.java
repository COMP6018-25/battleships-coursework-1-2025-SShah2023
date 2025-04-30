package controller;
import model.GameModel;
import view.GameView;

public class GameController {
    private GameModel gameModel;
    private final GameView gameView;

    public GameController(GameModel gameModel, GameView gameView) {
        // precondition- neither model nor view should be null
        assert gameModel != null : "GameModel must not be null";
        assert gameView != null : "GameView must not be null";
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    public void handleGuess(int row, int col) {
        // precondition- guess must be within board bounds
        assert row >= 0 && row < 10 : "Row guess out of bounds";
        assert col >= 0 && col < 10 : "Column guess out of bounds";
        if (!gameModel.getBoard()[row][col].isGuessed()) {
            boolean hit = gameModel.makeGuess(row, col);
            gameView.updateBoard(row, col, hit);

            // Check if the specific ship is sunk
            if (gameModel.getBoard()[row][col].hasShip() && gameModel.isShipSunk(gameModel.getBoard()[row][col].getShip())) {
                String shipName = gameModel.getBoard()[row][col].getShip().getName();
                gameView.showSunkShip(shipName);
            }
            // Check if all ships are sunk and the game is over
            // Show total number of guesses
            if (gameModel.isGameOver()) {
                gameView.displayGameOver(gameModel.getTotalGuesses());
                disableBoard();
            }
        } else {
            gameView.displayMessage("You've already guessed this spot!");
        }
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    private void disableBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameView.disableCell(i, j);
            }
        }
    }
}


