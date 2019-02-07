package com.example.pong;

import android.graphics.Bitmap;

public class Player extends Paddle {
    public Player(GameSurface gameSurface, Bitmap sprite) {
        super(gameSurface, sprite,10);
    }

    public void moveTowards(float eventY) {
        int centerY = getY() + getHeight()/2;
        int delta = 15;
        if(Math.abs(eventY - centerY) > delta) {
            setY(Math.round((y + Math.signum(eventY - centerY) * this.VELOCITY*15)));
        }
    }
}
