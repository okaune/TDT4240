package com.example.pong;

import android.content.Context;
import android.graphics.Color;

public class InfoTextview extends android.support.v7.widget.AppCompatTextView {

    private int playerScore;
    private int botScore;

    public InfoTextview(Context context) {
        super(context);
        setTextColor(Color.WHITE);
        playerScore = 0;
        botScore = 0;
        setText();
    }

    public void setText() {
        String text = "Your score: "+ playerScore + "                          Bot score: " + botScore;
        super.setText(text);
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void incrementPlayerScore() {
        playerScore++;
        setText();
    }

    public void incrementBotScore() {
        botScore++;
        setText();
    }

    public int getBotScore() {
        return botScore;
    }

    public void setBotScore(int botScore) {
        this.botScore = botScore;
    }


}
