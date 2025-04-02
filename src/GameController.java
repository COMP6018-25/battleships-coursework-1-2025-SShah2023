import model.GameModel;

public class GameController {
    private GameModel gameModel;
    private final GameView gameView;

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    public void handleGuess(int row, int col) {
        assert row >= 0 && row < 10 && col >= 0 && col < 10 : "Guess out bounds";
        if (!gameModel.getBoard()[row][col].isGuessed()) {
            boolean hit = gameModel.makeGuess(row, col);
            gameView.updateBoard(row, col, hit);

            // Check if the specific ship is sunk
            if (gameModel.getBoard()[row][col].hasShip() && gameModel.isShipSunk(gameModel.getBoard()[row][col].getShip())) {
                String shipName = gameModel.getBoard()[row][col].getShip().getName();
                gameView.showSunkShip(shipName);


            }

            // Check if all ships are sunk and the game is over
            if (gameModel.isGameOver()) {
                gameView.displayGameOver();
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

    public void resetGame() {
        gameModel = new GameModel();
        assert gameModel.getBoard() != null : "Game board should be initialized after reset";
        gameView.displayMessage("Game Reset! Select a cell to attack.");
        gameView.resetBoard(); // Reset the UI board
    }

}
