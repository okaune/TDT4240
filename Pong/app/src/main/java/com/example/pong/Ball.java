package com.example.pong;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;


public class Ball extends GameObject {
    private GameSurface gameSurface;
    private float VELOCITY = 1.4f;
    private InfoTextview infoTextview;

    public Ball(GameSurface gameSurface, Bitmap image, int x, int y, int vecX, int vecY) {
        super(image, x, y, vecX, vecY);
        this.gameSurface = gameSurface;
    }

    public void update() {
        super.update();
        double vecLen = Math.sqrt(Math.pow(getVecX(), 2) + Math.pow(getVecY(), 2));
        float distance = VELOCITY * deltaTime;

        // Calculate the new position of the game character.
        setX(getX() + (int)(distance * getVecX() / vecLen));
        setY(getY() + (int)(distance * getVecY() / vecLen));

        checkXBorders();
        checkYBorders();
    }

    public void checkXBorders() {
        if(getX() < 0) {
            infoTextview.incrementBotScore();
            reset();
        } else if(getX() + getWidth() > gameSurface.getWidth()) {
            infoTextview.incrementPlayerScore();
            reset();
        }
    }

    public void reset() {
        Random random = new Random();

        vecX =  vecX >= 0 ?
                (int)Math.round(mapRange(0, 1, -10, -3, random.nextDouble())) :
                (int)Math.round(mapRange(0, 1, 3, 10, random.nextDouble()));
        vecY = (int) Math.round(mapRange(0, 1, -4, 4, random.nextDouble()));

        x = gameSurface.getCenterX(WIDTH);
        y = gameSurface.getCenterY(HEIGHT);
    }

    public void checkYBorders() {
        if(getY() < 0) {
            setY(0);
            setVecY(-getVecY());
        } else if(getY() + getHeight() > gameSurface.getHeight()) {
            setY(gameSurface.getHeight() - getHeight());
            setVecY(-getVecY());
        }
    }

    public void setInfoTextview(InfoTextview infoTextview) {
        this.infoTextview = infoTextview;
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
