package com.example.pong;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GameObject {
    protected Bitmap sprite;
    protected long now;
    protected int deltaTime;
    protected long drawNanoTime = -1;

    protected final int WIDTH;
    protected final int HEIGHT;

    protected int x;
    protected int y;

    protected int vecX;
    protected int vecY;

    public GameObject(Bitmap sprite, int x, int y, int vecX, int vecY)  {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.vecX = vecX;
        this.vecY = vecY;
        this.WIDTH = sprite.getWidth();
        this.HEIGHT = sprite.getHeight();
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVecX() {
        return vecX;
    }

    public void setVecX(int vecX) {
        this.vecX = vecX;
    }

    public int getVecY() {
        return vecY;
    }

    public void setVecY(int vecY) {
        this.vecY = vecY;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public void update() {
        // Current time in nanoseconds
        now = System.nanoTime();


        // Never once did draw.
        if(drawNanoTime == -1) {
            drawNanoTime = now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        deltaTime = (int) ((now - drawNanoTime) / 1000000 );

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.sprite, x, y, null);
        this.drawNanoTime = System.nanoTime();
    }

}
