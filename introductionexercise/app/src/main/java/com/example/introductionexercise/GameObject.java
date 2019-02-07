package com.example.introductionexercise;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.List;
import java.util.stream.Collectors;

public class GameObject {
    protected List<Bitmap> images;

    protected final int WIDTH;
    protected final int HEIGHT;

    protected int x;
    protected int y;

    protected int directionX;

    public GameObject(List<Bitmap> images, int x, int y)  {
        this.images = images;
        this.x = x;
        this.y = y;
        this.WIDTH = images.get(0).getWidth();
        this.HEIGHT = images.get(0).getHeight();
    }

    public List<Bitmap> getImages() {
        return images;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public void setDirection(int vecX) {
        if(vecX >= 0) {
            vecX = 1;
        } else {
            vecX = -1;
        }

        if(directionX != vecX) {
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            images = images.stream()
                    .map(image -> Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true))
                    .collect(Collectors.toList());
            directionX = vecX;
        }
    }


}
