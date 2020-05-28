package tetris.model;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class TetrominoGenerator {

    private List<Supplier<Tetromino>> tetrominos;
    private Random random;

    public TetrominoGenerator() {
        this.random = new Random();
        this.tetrominos = Arrays.asList(IShape, JShape, LShape, OShape, SShape, TShape, ZShape);
    }

    public Tetromino getRandomTetromino() {
        return tetrominos.get(random.nextInt(tetrominos.size())).get();
    }

    private Supplier<Tetromino> LShape = () -> {
        var piecePositions = new Point[]{
                new Point(-1, 0),
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, -1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.DARKGREEN);
    };

    private Supplier<Tetromino> JShape = () -> {
        var piecePositions = new Point[]{
                new Point(0, 0),
                new Point(0, -1),
                new Point(0, 1),
                new Point(-1, 1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.CORAL);
    };

    private Supplier<Tetromino> OShape = () -> {
        var piecePositions = new Point[]{
                new Point(-1, 0),
                new Point(0, 0),
                new Point(-0, -1),
                new Point(1, -1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.DODGERBLUE);
    };

    private Supplier<Tetromino> IShape = () -> {
        var piecePositions = new Point[]{
                new Point(-2, 0),
                new Point(-1, 0),
                new Point(0, 0),
                new Point(1, 0),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.MEDIUMPURPLE);
    };

    private Supplier<Tetromino> ZShape = () -> {
        var piecePositions = new Point[]{
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, -1),
                new Point(-1, -1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.ORANGE);
    };

    private Supplier<Tetromino> SShape = () -> {
        var piecePositions = new Point[]{
                new Point(0, 0),
                new Point(-1, 0),
                new Point(0, -1),
                new Point(1, -1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.BLUE);
    };

    private Supplier<Tetromino> TShape = () -> {
        var piecePositions = new Point[]{
                new Point(0, 0),
                new Point(1, 0),
                new Point(-1, 0),
                new Point(0, -1),
        };
        return new Tetromino(new Point(5, 0), piecePositions, Color.BROWN);
    };

}
