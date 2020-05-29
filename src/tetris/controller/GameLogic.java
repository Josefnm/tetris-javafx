package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class GameLogic {

    private long frame = 0;

    private Color[][] gameGrid;

    public GameLogic() {
        this.gameGrid = new Color[10][20];
    }

    public void runTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if(frame%48==0){
                    //TODO drop pieces
                }

            }
        };
        animationTimer.start();
    }
}
