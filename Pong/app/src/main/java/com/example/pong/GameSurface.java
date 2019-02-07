package com.example.pong;

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
    private Ball ball;
    private Paddle paddle;
    private Paddle botPaddle;
    private InfoTextview infoTextview;

    public GameSurface(Context context, InfoTextview infoTextview) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        this.infoTextview = infoTextview;
    }

    public void update()  {
        checkCollision(ball, paddle);
        checkCollision(ball, botPaddle);
        this.ball.update();
        this.paddle.update();
        this.botPaddle.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        ((Player)paddle).moveTowards(e.getY());
        return true;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        this.ball.draw(canvas);
        paddle.draw(canvas);
        botPaddle.draw(canvas);
    }

    public void checkCollision(GameObject g1, GameObject g2) {
        int left1 = g1.x,
            left2 = g2.x;
        int right1 = g1.x + g1.getWidth(),
            right2 = g2.x + g2.getWidth();
        int top1 = g1.y,
            top2 = g2.y;
        int bot1 = g1.y + g1.getHeight(),
            bot2 = g2.y + g2.getHeight();
        Rect rect1 = new Rect(left1, top1, right1, bot1);
        Rect rect2 = new Rect(left2, top2, right2, bot2);

        if(Rect.intersects(rect1, rect2)) {
            float w = 0.5f * (rect1.width() + rect2.width());
            float h = 0.5f * (rect1.height() + rect2.height());
            float dx = rect1.centerX() - rect2.centerX();
            float dy = rect1.centerY() - rect2.centerY();

            if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
                // TODO: referer eller skriv om
                /* collision! */
                float wy = w * dy;
                float hx = h * dx;

                if (wy > hx) {
                    if (wy > -hx) {
                        // Vertical collision
                        // Gameobject 1 on bottom, GO 2 top object
                        g1.setVecY(Math.abs(g1.getVecY()));
                        g2.setVecY(-Math.abs(g2.getVecY()));

                    } else {
                        // Horizontal collision
                        // Gameobject 1 on right, 2 on the left
                        g1.setVecX(-Math.abs(g1.getVecX()));
                        g2.setVecX(Math.abs(g2.getVecX()));
                    }
                } else {
                    if (wy > -hx) {
                        // Horizontal collision
                        g1.setVecX(Math.abs(g1.getVecX()));
                        g2.setVecX(-Math.abs(g2.getVecX()));
                    } else {
                        // Vertical collision
                        g1.setVecY(-Math.abs(g1.getVecY()));
                        g1.setVecY(Math.abs(g2.getVecY()));
                    }
                }
            }

        }
    }


    public int getCenterX(int objectWidth) {
        return (this.getWidth() - objectWidth) / 2;
    }

    public int getCenterY(int objectHeight) {
        return (this.getHeight() - objectHeight) / 2;
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap ballSprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
        Bitmap paddleSprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.paddle);
        this.ball = new Ball(this, ballSprite, getCenterX(ballSprite.getWidth()),getCenterY(ballSprite.getHeight()), 10, 5);
        this.ball.setInfoTextview(infoTextview);
        this.paddle = new Player(this, paddleSprite);
        this.botPaddle = new Bot(this, paddleSprite, ball);
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
