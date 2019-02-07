package com.example.introductionexercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Helicopter helicopter;
    private Helicopter chopper;
    private InfoTextview infoTextview;

    public GameSurface(Context context) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
    }

    public void update()  {
        checkCollision(helicopter, chopper);
        this.helicopter.update();
        this.chopper.update();
        infoTextview.setText("Position(x: " + helicopter.x + ", y: " + helicopter.y + ")");
    }


    public void setInfoTextview(InfoTextview infoTextview) {
        this.infoTextview = infoTextview;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        helicopter.setVectorTowardsPoint(e.getX(), e.getY());
        return true;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        this.helicopter.draw(canvas);
        this.chopper.draw(canvas);
    }

    public Rect collisionOverlap(Rect rect1, Rect rect2) {
        int left = (int) Math.max(rect1.left, rect2.left);
        int top = (int) Math.max(rect1.top, rect2.top);
        int right = (int) Math.min(rect1.right, rect2.right);
        int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    public void checkCollision(Helicopter gameObject1, Helicopter gameObject2) {
        Bitmap bitmap1 = gameObject1.getImages().get(0);
        Bitmap bitmap2 = gameObject2.getImages().get(0);

        int left1 = gameObject1.x,
            left2 = gameObject2.x;
        int right1 = gameObject1.x + gameObject1.WIDTH,
            right2 = gameObject2.x + gameObject2.WIDTH;
        int top1 = gameObject1.y,
            top2 = gameObject2.y;
        int bot1 = gameObject1.y + gameObject1.HEIGHT,
            bot2 = gameObject2.y + gameObject2.HEIGHT;
        Rect rect1 = new Rect(left1, top1, right1, bot1);
        Rect rect2 = new Rect(left2, top2, right2, bot2);

        if(Rect.intersects(rect1, rect2)) {
            float w = 0.5f * (rect1.width() + rect2.width());
            float h = 0.5f * (rect1.height() + rect2.height());
            float dx = rect1.centerX() - rect2.centerX();
            float dy = rect1.centerY() - rect2.centerY();

            if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
                /* collision! */
                float wy = w * dy;
                float hx = h * dx;

                if (wy > hx) {
                    if (wy > -hx) {
                        // Vertical collision
                        // Gameobject 1 on bottom, GO 2 top object
                        gameObject1.setVecY(Math.abs(gameObject1.getVecY()));
                        gameObject2.setVecY(-Math.abs(gameObject2.getVecY()));

                    } else {
                        // Horizontal collision
                        // Gameobject 1 on right, 2 on the left
                        gameObject1.setVecX(-Math.abs(gameObject1.getVecX()));
                        gameObject2.setVecX(Math.abs(gameObject2.getVecX()));
                    }
                } else {
                    if (wy > -hx) {
                        // Horizontal collision
                        gameObject1.setVecX(Math.abs(gameObject1.getVecX()));
                        gameObject2.setVecX(-Math.abs(gameObject2.getVecX()));
                    } else {
                        // Vertical collision
                        gameObject1.setVecY(-Math.abs(gameObject1.getVecY()));
                        gameObject2.setVecY(Math.abs(gameObject2.getVecY()));
                    }
                }
            }

        }
    }




    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        List<Bitmap> helicopterBitmaps = new ArrayList<>();
        helicopterBitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.heli1));
        helicopterBitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.heli2));
        helicopterBitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.heli3));
        helicopterBitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.heli4));
        this.helicopter = new Helicopter(this, helicopterBitmaps,100,50);
        this.chopper = new Helicopter(this, helicopterBitmaps, 200, 600);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
