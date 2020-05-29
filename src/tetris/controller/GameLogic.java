package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import tetris.model.Tetromino;
import tetris.model.TetrominoGenerator;

public class GameLogic {

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int DROP_SPEED = 48;

    private int frame = 0;

    private TetrominoGenerator tetrominoGenerator;
    private Color[][] gameGrid;
    private Tetromino tetromino;

    public GameLogic() {
        this.gameGrid = new Color[BOARD_WIDTH][BOARD_HEIGHT];
        this.tetrominoGenerator = new TetrominoGenerator();
        this.tetromino = tetrominoGenerator.getRandomTetromino();
    }

    public void runTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                // javafx runs at 60 frames by default. Pieces drop every DROP_SPEED frames.
                if (frame % DROP_SPEED == 0) {
                    tetromino.moveDown();
                }
            }
        };
        animationTimer.start();
    }
}
