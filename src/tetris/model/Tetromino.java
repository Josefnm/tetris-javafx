package tetris.model;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tetromino {

    // center position of the tetromino
    private Point position;
    //positions relative to center position of the tetromino
    private Point[] piecePositions;
    private Color color;

    public Tetromino(Point position, Point[] piecePositions, Color color) {
        this.position = position;
        this.piecePositions = piecePositions;
        this.color = color;
    }

    public void moveDown() {
        position.setY(position.getY() + 1);
    }

    public void moveUp() {
        position.setY(position.getY() - 1);
    }

    public void moveLeft() {
        position.setX(position.getX() - 1);
    }

    public void moveRight() {
        position.setX(position.getX() + 1);
    }

    public void rotateClockwise() {
        for (Point piecePosition : piecePositions) {
            piecePosition.rotateClockwise();
        }
    }

    public void rotateCounterClockwise() {
        for (Point piecePosition : piecePositions) {
            piecePosition.rotateCounterClockwise();
        }
    }

    public List<Point> getAbsolutePositions() {
        return Arrays
                .stream(piecePositions)
                .map(p -> new Point(
                        position.getX() + p.getX(),
                        position.getY() + p.getY()))
                .collect(Collectors.toList());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point[] getPiecePositions() {
        return piecePositions;
    }

    public void setPiecePositions(Point[] piecePositions) {
        this.piecePositions = piecePositions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}


