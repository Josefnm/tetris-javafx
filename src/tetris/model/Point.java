package tetris.model;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * translation rotation clockwise. (1,1) becomes (1,-1) and so on
     */
    public void rotateClockwise() {
        int oldX = this.x;
        this.x = this.y;
        this.y = -oldX;
    }
    public void rotateCounterClockwise() {
        int oldY = this.y;
        this.y = this.x;
        this.x = -oldY;
    }
}
