package tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tetris.controller.GameLogic;
import tetris.controller.GuiController;

public class Main extends Application {

    private final static String TITLE = "Tetris";

    GameLogic gameLogic;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/gui.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        GuiController guiController = loader.getController();
        gameLogic = new GameLogic(guiController.getGraphicsContext());
        gameLogic.runTimer();

        scene.setOnKeyPressed(this::keyEventHandler);

        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void keyEventHandler(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                gameLogic.getTetromino().rotateCounterClockwise();
                break;
            case X:
                gameLogic.getTetromino().rotateClockwise();
                break;
            case DOWN:
                gameLogic.getTetromino().moveDown();
                break;
            case LEFT:
                gameLogic.getTetromino().moveLeft();
                break;
            case RIGHT:
                gameLogic.getTetromino().moveRight();
                break;
        }
        gameLogic.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
