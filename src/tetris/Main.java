package tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tetris.controller.GameLogic;
import tetris.controller.GuiController;
import tetris.model.Tetromino;

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
        guiController.bindProperties(gameLogic);
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
                gameLogic.tryMove(Tetromino::rotateCounterClockwise, Tetromino::rotateClockwise);
                break;
            case X:
                gameLogic.tryMove(Tetromino::rotateClockwise, Tetromino::rotateCounterClockwise);
                break;
            case DOWN:
                gameLogic.tryMoveDown();
                break;
            case LEFT:
                gameLogic.tryMove(Tetromino::moveLeft, Tetromino::moveRight);
                break;
            case RIGHT:
                gameLogic.tryMove(Tetromino::moveRight, Tetromino::moveLeft);
                break;
        }
        gameLogic.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
