package com.example.pong;

import android.graphics.Bitmap;

public abstract class Paddle extends GameObject {

    protected GameSurface gameSurface;
    protected long drawNanoTime = -1;
    protected final float VELOCITY = 0.9f;

    public Paddle(GameSurface gameSurface, Bitmap sprite, int x) {
        super(sprite, x, gameSurface.getCenterY(sprite.getHeight()), 0, 0);
    }

    public void checkYBorders() {
        if(getY() < 0) {
            setY(0);
            setVecY(0);
        } else if(getY() + getHeight() > gameSurface.getHeight()) {
            setY(gameSurface.getHeight() - getHeight());
            setVecY(0);
        }
    }

}
