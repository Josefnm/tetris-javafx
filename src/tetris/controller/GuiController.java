package tetris.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class GuiController {
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
        //TODO replace with constants
        gameCanvas.setHeight(600);
        gameCanvas.setWidth(300);
    }

    public GraphicsContext getGraphicsContext() {
        return gameCanvas.getGraphicsContext2D();
    }

}
