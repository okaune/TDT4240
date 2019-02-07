package com.example.pong;

import android.graphics.Bitmap;


public class Bot extends Paddle {
    private Ball ball;
    public Bot(GameSurface gameSurface, Bitmap sprite, Ball ball) {
        super(gameSurface, sprite,gameSurface.getWidth() - sprite.getWidth() - 10);
        this.ball = ball;
    }

    @Override
    public void update() {
        super.update();
        int centerBallY = ball.getY() + ball.getHeight()/2;
        int centerY = getY() + getHeight()/2;
        int delta = 15;
        if(Math.abs(centerBallY - centerY) > delta) {
            setY(Math.round((y + Math.signum(centerBallY - centerY) * this.VELOCITY*15)));
        }
    }

}
