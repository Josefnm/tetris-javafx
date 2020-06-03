package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
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

    private IntegerProperty gameSpeed;
    private IntegerProperty level;

    private int frame = 0;

    private int rowsCleared = 0;

    private TetrominoGenerator tetrominoGenerator;
    private Color[][] gameGrid;
    private Tetromino tetromino;
    private GraphicsContext graphicsContext;


    public GameLogic(GraphicsContext graphicsContext) {
        this.gameSpeed = new SimpleIntegerProperty();
        this.level = new SimpleIntegerProperty(0);
        this.graphicsContext = graphicsContext;
        this.gameGrid = new Color[BOARD_WIDTH][BOARD_HEIGHT];
        this.tetrominoGenerator = new TetrominoGenerator();
        this.tetromino = tetrominoGenerator.getRandomTetromino();
        graphicsContext.setStroke(Color.WHITE);
    }

    public void runTimer() {
        render();
        gameSpeed.bind(Bindings.createIntegerBinding(
                () -> getSpeedForLevel(level.get()), level
        ));
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if (frame % gameSpeed.get() == 0) {
                    tryMoveDown();
                    render();
                }
            }
        };
        animationTimer.start();
    }

    public static int getSpeedForLevel(int level) {
        if (level == 0) return 48;
        if (level == 1) return 43;
        if (level == 2) return 38;
        if (level == 3) return 33;
        if (level == 4) return 28;
        if (level == 5) return 23;
        if (level == 6) return 18;
        if (level == 7) return 13;
        if (level == 8) return 8;
        if (level == 9) return 6;
        if (level <= 12) return 5;
        if (level <= 16) return 4;
        if (level <= 19) return 3;
        if (level <= 29) return 2;
        return 1;
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
        rowsCleared += removeFullRows();
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
}
