package tetris.controller;

import javafx.animation.AnimationTimer;

public class GameLogic {

    private long frame = 0;

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
