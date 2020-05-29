package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tetris.model.Point;
import tetris.model.Tetromino;
import tetris.model.TetrominoGenerator;

import java.util.List;
import java.util.function.Consumer;

import static tetris.controller.GuiController.TILE_SIZE;

public class GameLogic {

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int DROP_SPEED = 48;

    private int frame = 0;

    private TetrominoGenerator tetrominoGenerator;
    private Color[][] gameGrid;
    private Tetromino tetromino;
    private GraphicsContext graphicsContext;

    public GameLogic(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.gameGrid = new Color[BOARD_WIDTH][BOARD_HEIGHT];
        this.tetrominoGenerator = new TetrominoGenerator();
        this.tetromino = tetrominoGenerator.getRandomTetromino();
        graphicsContext.setStroke(Color.WHITE);
    }

    public void runTimer() {
        render();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                // javafx runs at 60 frames by default. Pieces drop every DROP_SPEED frames.
                if (frame % DROP_SPEED == 0) {
                    tryMove(Tetromino::moveDown, Tetromino::moveUp);
                    render();
                }
            }
        };
        animationTimer.start();
    }

    public void render() {
        graphicsContext.clearRect(0, 0, TILE_SIZE * BOARD_WIDTH, TILE_SIZE * BOARD_HEIGHT);
        graphicsContext.setFill(tetromino.getColor());
        for (Point p : tetromino.getAbsolutePositions()) {
            paintPiece(p.getX(), p.getY());
        }
    }

    public void paintPiece(double x, double y) {
        graphicsContext.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        graphicsContext.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


    public void tryMove(Consumer<Tetromino> move, Consumer<Tetromino> revertMove) {
        move.accept(tetromino);
        List<Point> pieces = tetromino.getAbsolutePositions();
        if (pieces.stream().anyMatch(this::isInvalidMove)) {
            revertMove.accept(tetromino);
        }
    }

    private boolean isInvalidMove(Point point) {
        return isOutside(point) || isBottom(point) || isOverlap(point);
    }

    private boolean isOutside(Point p) {
        return p.getX() < 0 || p.getX() >= BOARD_WIDTH;
    }

    private boolean isBottom(Point p) {
        return p.getY() == BOARD_HEIGHT;
    }

    private boolean isOverlap(Point p) {
        return p.getY() >= 0 && gameGrid[p.getX()][p.getY()] != null;
    }
}
