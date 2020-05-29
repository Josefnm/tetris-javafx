package tetris.model;


import javafx.scene.paint.Color;

public class Tetromino {

    // center position of the tetromino
    private Point position;
    //positions relative to base position of the tetromino
    private Point[] piecePositions;
    private Color color;

    public Tetromino(Point position, Point[] piecePositions, Color color) {
        this.position = position;
        this.piecePositions = piecePositions;
        this.color = color;
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


