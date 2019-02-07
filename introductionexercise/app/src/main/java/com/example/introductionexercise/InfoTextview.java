package com.example.introductionexercise;

import android.content.Context;
import android.graphics.Color;

public class InfoTextview extends android.support.v7.widget.AppCompatTextView {

    public InfoTextview(Context context) {
        super(context);
        setTextColor(Color.WHITE);
    }

    public void setText(String text) {
        super.setText(text);
    }

}
