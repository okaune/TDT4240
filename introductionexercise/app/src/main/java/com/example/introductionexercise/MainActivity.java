package com.example.introductionexercise;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getSupportActionBar().hide();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FrameLayout layout = new FrameLayout(this);
        InfoTextview infoTextview = new InfoTextview(this);
        GameSurface gameSurface = new GameSurface(this);
        gameSurface.setInfoTextview(infoTextview);
        infoTextview.setText("Position");
        infoTextview.setX(0);
        infoTextview.setY(0);
        layout.addView(gameSurface);
        layout.addView(infoTextview);
        setContentView(layout);

    }
}
