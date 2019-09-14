package com.example.s243476.threegames;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TwentyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty);

        final Twenty playTwenty = new Twenty(getApplicationContext());


        final Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playTwenty.restartMat(new int[4][4]);
                print(playTwenty.getMat(), playTwenty.getScore());
            }
        });

        TextView Board = (TextView) findViewById(R.id.board);

        Board.setOnTouchListener(new OnSwipeTouchListener(TwentyActivity.this) {
            public void onSwipeTop() {
                playTwenty.setDir(Direction.UP);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore());
            }
            public void onSwipeRight() {
                playTwenty.setDir(Direction.RIGHT);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore());
            }
            public void onSwipeLeft() {
                playTwenty.setDir(Direction.LEFT);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore());
            }
            public void onSwipeBottom() {
                playTwenty.setDir(Direction.DOWN);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore());
            }

        });

        Board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



        print(playTwenty.getMat(), playTwenty.getScore());
    }



    public void print(int[][] mat, int score){
        TextView[] boxes = new TextView[16];
        boxes[0] = (TextView) findViewById(R.id.box1);
        boxes[1] = (TextView) findViewById(R.id.box2);
        boxes[2] = (TextView) findViewById(R.id.box3);
        boxes[3] = (TextView) findViewById(R.id.box4);
        boxes[4] = (TextView) findViewById(R.id.box5);
        boxes[5] = (TextView) findViewById(R.id.box6);
        boxes[6] = (TextView) findViewById(R.id.box7);
        boxes[7] = (TextView) findViewById(R.id.box8);
        boxes[8] = (TextView) findViewById(R.id.box9);
        boxes[9] = (TextView) findViewById(R.id.box10);
        boxes[10] = (TextView) findViewById(R.id.box11);
        boxes[11] = (TextView) findViewById(R.id.box12);
        boxes[12] = (TextView) findViewById(R.id.box13);
        boxes[13] = (TextView) findViewById(R.id.box14);
        boxes[14] = (TextView) findViewById(R.id.box15);
        boxes[15] = (TextView) findViewById(R.id.box16);

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++){

                //Write number in box
                if(mat[i][j] != 0)
                    boxes[i*4 + j].setText("" + mat[i][j]);
                else
                    boxes[i*4+j].setText("");

                //Set background color of box
                boxes[i*4 + j].setBackgroundColor(findColor(mat[i][j]));
            }


        //Write score
        TextView sayScore = (TextView) findViewById(R.id.score);
        sayScore.setText("Score: " + score);
    }

    public int findColor(int num){
        Class res = R.color.class;
        //Field field = res.getField(R.color.c256);
        switch(num){
            case 0: return ContextCompat.getColor(this, R.color.c0);
            case 2: return ContextCompat.getColor(this, R.color.c2);
            case 4: return ContextCompat.getColor(this, R.color.c4);
            case 8: return ContextCompat.getColor(this, R.color.c8);
            case 16: return ContextCompat.getColor(this, R.color.c16);
            case 32: return ContextCompat.getColor(this, R.color.c32);
            case 64: return ContextCompat.getColor(this, R.color.c64);
            case 128: return ContextCompat.getColor(this, R.color.c128);
            case 256: return ContextCompat.getColor(this, R.color.c256);
            case 512: return ContextCompat.getColor(this, R.color.c512);
            case 1024: return ContextCompat.getColor(this, R.color.c1024);
            case 2048: return ContextCompat.getColor(this, R.color.c2048);
            default: return ContextCompat.getColor(this, R.color.other);
        }
    }
}


