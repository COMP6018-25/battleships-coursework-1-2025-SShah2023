import model.GameModel;

public class GUIMain {
    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameModel, gameView);

        gameView.setController(gameController);
        gameView.setVisible(true);
    }
}
