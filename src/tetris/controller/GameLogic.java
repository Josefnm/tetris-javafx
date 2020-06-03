package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    public static final int LEVEL_DIVIDER = 10;
    private static final int[] POINTS = new int[]{0, 40, 100, 300, 1200};

    private int frame = 0;

    private IntegerProperty rowsCleared;
    private IntegerProperty level;
    private IntegerProperty score;

    private TetrominoGenerator tetrominoGenerator;
    private Color[][] gameGrid;
    private Tetromino tetromino;
    private GraphicsContext graphicsContext;

    public GameLogic(GraphicsContext graphicsContext) {
        this.rowsCleared = new SimpleIntegerProperty(0);
        this.level = new SimpleIntegerProperty(0);
        this.score = new SimpleIntegerProperty(0);
        this.level.bind(Bindings.createIntegerBinding(
                () -> rowsCleared.get() / LEVEL_DIVIDER, rowsCleared
        ));

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
                    tryMoveDown();
                    render();
                }
            }
        };
        animationTimer.start();
    }

    public void render() {
        graphicsContext.clearRect(0, 0, TILE_SIZE * BOARD_WIDTH, TILE_SIZE * BOARD_HEIGHT);
        renderTetromino();
        renderBoard();
    }

    private void renderTetromino() {
        graphicsContext.setFill(tetromino.getColor());
        for (Point p : tetromino.getAbsolutePositions()) {
            paintPiece(p.getX(), p.getY());
        }
    }

    private void renderBoard() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                Color color = gameGrid[x][y];
                if (color != null) {
                    graphicsContext.setFill(color);
                    paintPiece(x, y);
                }
            }
        }
    }

    public void paintPiece(double x, double y) {
        graphicsContext.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        graphicsContext.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public boolean tryMove(Consumer<Tetromino> move, Consumer<Tetromino> revertMove) {
        move.accept(tetromino);
        List<Point> pieces = tetromino.getAbsolutePositions();
        if (pieces.stream().anyMatch(this::isInvalidMove)) {
            revertMove.accept(tetromino);
            return false;
        }
        return true;
    }

    public void tryMoveDown() {
        if (!tryMove(Tetromino::moveDown, Tetromino::moveUp)) {
            endTetromino();
        }
    }

    private void endTetromino() {
        var pieces = tetromino.getAbsolutePositions();
        for (Point piece : pieces) {
            gameGrid[piece.getX()][piece.getY()] = tetromino.getColor();
        }
        int rowsCleared=removeFullRows();
        this.rowsCleared.set(this.rowsCleared.get() + rowsCleared);
        addScore(rowsCleared);
        trySpawnTetromino();
    }

    private void trySpawnTetromino() {
        Tetromino tetromino = tetrominoGenerator.getRandomTetromino();
        boolean isOverlap = tetromino.getAbsolutePositions().stream().anyMatch(this::isOverlap);
        if (!isOverlap) {
            this.tetromino = tetromino;
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

    private int removeFullRows() {
        int rowsCleared = 0;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            if (isFullRow(i)) {
                removeRow(i);
                rowsCleared++;
            }
        }
        return rowsCleared;
    }

    private boolean isFullRow(int row) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            if (gameGrid[x][row] == null) {
                return false;
            }
        }
        return true;
    }

    private void removeRow(int row) {
        for (int y = row; y > 0; y--) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                gameGrid[x][y] = gameGrid[x][y - 1];
            }
        }
    }

    private void addScore(int rowsCleared) {
        score.set(POINTS[rowsCleared] * (level.get() + 1) + score.get());
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public IntegerProperty rowsClearedProperty() {
        return rowsCleared;
    }

}
