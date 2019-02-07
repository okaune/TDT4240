package com.example.introductionexercise;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Helicopter extends GameObject {
    private String TAG = "Helicopter";
    private GameSurface gameSurface;
    private int vecX = 10;
    private int vecY = 5;
    private long drawNanoTime =-1;
    private float VELOCITY = 0.6f;
    private int currentImage = 0;
    public Helicopter(GameSurface gameSurface, final List<Bitmap> images, int x, int y) {
        super(images, x, y);
        this.gameSurface = gameSurface;
        Random random = new Random();
        vecX = (int) Math.round(mapRange(0, 1, -10, 10, random.nextDouble()));
        vecY = (int) Math.round(mapRange(0, 1, -10, 10, random.nextDouble()));

        setDirection(vecX);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentImage = (currentImage + 1) % images.size();
            }
        }, 0, 100);
    }

    public void update() {
        // Current time in nanoseconds
        long now = System.nanoTime();


        // Never once did draw.
        if(drawNanoTime ==-1) {
            drawNanoTime = now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - drawNanoTime) / 1000000 );
        double vecLen = Math.sqrt(vecX * vecX + vecY * vecY);
        float distance = VELOCITY * deltaTime;

        // Calculate the new position of the game character.
        this.x = x + (int)(distance * vecX / vecLen);
        this.y = y + (int)(distance * vecY / vecLen);

        checkXBorders();
        checkYBorders();
    }

    public void setVectorTowardsPoint(float eventX, float eventY) {
        vecX = (int)(eventX - x);
        vecY = (int)(eventY - y);
        setDirection(vecX);
    }

    public int getVecX() {
        return vecX;
    }

    public int getVecY() {
        return vecY;
    }

    public void setVecX(int vecX) {
        this.vecX = vecX;
    }

    public void setVecY(int vecY) {
        this.vecY = vecY;
    }

    public void checkXBorders() {
        if(this.x < 0) {
            this.x = 0;
            vecX = -vecX;
            setDirection(vecX);
        } else if(this.x + WIDTH > gameSurface.getWidth()) {
            this.x = gameSurface.getWidth() - WIDTH;
            vecX = -vecX;
            setDirection(vecX);
        }
    }

    public void checkYBorders() {
        if(this.y < 0) {
            this.y = 0;
            vecY = -vecY;
        } else if(this.y + HEIGHT > gameSurface.getHeight()) {
            this.y = gameSurface.getHeight() - HEIGHT;
            vecY = -vecY;
        }
    }

    public void draw(Canvas canvas)  {
        canvas.drawBitmap(this.images.get(currentImage), x, y, null);
        this.drawNanoTime = System.nanoTime();
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
