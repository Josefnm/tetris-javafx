package tetris.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import static tetris.controller.GameLogic.BOARD_HEIGHT;
import static tetris.controller.GameLogic.BOARD_WIDTH;

public class GuiController {

    public static final int TILE_SIZE = 30;

    @FXML
    private Label gameOverLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label clearedLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Canvas gameCanvas;

    @FXML
    public void initialize() {
        gameCanvas.setHeight(TILE_SIZE * BOARD_HEIGHT);
        gameCanvas.setWidth(TILE_SIZE * BOARD_WIDTH);
    }


    public void bindProperties(GameLogic gameLogic) {
        clearedLabel.textProperty().bind(gameLogic.rowsClearedProperty().asString());
        levelLabel.textProperty().bind(gameLogic.levelProperty().asString());
        scoreLabel.textProperty().bind(gameLogic.scoreProperty().asString());
    }

    public GraphicsContext getGraphicsContext() {
        return gameCanvas.getGraphicsContext2D();
    }

}
