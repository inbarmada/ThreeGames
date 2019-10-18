package com.example.s243476.threegames;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TwentyActivity extends AppCompatActivity {
    Twenty playTwenty;
    TextView[][] boxes = new TextView[4][4];
    int height;
    int width;
    int borderColor;
    int c0Color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty);

        playTwenty = new Twenty(getApplicationContext());

        borderColor = ContextCompat.getColor(this, R.color.border);
        c0Color = ContextCompat.getColor(this, R.color.c0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        final Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playTwenty.restartMat();
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }
        });


        TextView Board = (TextView) findViewById(R.id.board);

        Board.setOnTouchListener(new OnSwipeTouchListener(TwentyActivity.this) {

            public void onSwipeTop() {
                playTwenty.setDir(Direction.UP);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }
            public void onSwipeRight() {
                playTwenty.setDir(Direction.RIGHT);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }
            public void onSwipeLeft() {
                playTwenty.setDir(Direction.LEFT);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }
            public void onSwipeBottom() {
                playTwenty.setDir(Direction.DOWN);
                playTwenty.move();
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }

        });

        Board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        createBoxes();


        print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
    }

    public void createBoxes(){
        final TableLayout field = (TableLayout) findViewById(R.id.twentyTable);

        int borderStroke = Math.min(height/4/20, width/4*2/30);
        int boxSize = Math.min(height/2, width*9/10) / 4;

        //Border above row
        final TableRow above = new TableRow(this);
        above.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

        //Make the row span all columns
        final TextView upBorder = new TextView(this);

        TableRow.LayoutParams params = (TableRow.LayoutParams) above.getLayoutParams();
        params.span = 2*4 + 1; //amount of columns you will span

        upBorder.setHeight(borderStroke);
        upBorder.setBackgroundColor(borderColor);

        upBorder.setLayoutParams(params);
        //Finished fixing span, now add to tablelayout
        above.addView(upBorder);
        field.addView(above, new TableLayout.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        for(int i = 0; i < 4; i++){
            final TableRow tr = new TableRow(this);
            field.addView(tr);
            for(int j = 0; j < 4; j++){
                final TextView border = new TextView(this);
                border.setHeight(boxSize);
                border.setWidth(borderStroke);
                border.setBackgroundColor(borderColor);
                tr.addView(border);

                final TextView tv = new TextView(this);
                tv.setHeight(boxSize);
                tv.setWidth(boxSize);
                tv.setBackgroundColor(c0Color);
                tr.addView(tv);

                //Add the textview to the tv mat
                boxes[i][j] = tv;
            }

            //Edge border
            final TextView border = new TextView(this);
            border.setHeight(boxSize);
            border.setWidth(borderStroke);
            border.setBackgroundColor(borderColor);
            tr.addView(border);

            //Border below row
            final TableRow below = new TableRow(this);
            below.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

            //Make the row span all columns
            final TextView lowBorder = new TextView(this);

            TableRow.LayoutParams params1 = (TableRow.LayoutParams) below.getLayoutParams();
            params1.span = 2*4 + 1; //amount of columns you will span

            lowBorder.setHeight(borderStroke);
            lowBorder.setBackgroundColor(borderColor);

            lowBorder.setLayoutParams(params1);
            //Finished fixing span, now add to tablelayout
            below.addView(lowBorder);
            field.addView(below, new TableLayout.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public void print(int[][] mat, int score, boolean won, boolean lost, boolean cont){
//        boxes[0] = (TextView) findViewById(R.id.box1);
//        boxes[1] = (TextView) findViewById(R.id.box2);
//        boxes[2] = (TextView) findViewById(R.id.box3);
//        boxes[3] = (TextView) findViewById(R.id.box4);
//        boxes[4] = (TextView) findViewById(R.id.box5);
//        boxes[5] = (TextView) findViewById(R.id.box6);
//        boxes[6] = (TextView) findViewById(R.id.box7);
//        boxes[7] = (TextView) findViewById(R.id.box8);
//        boxes[8] = (TextView) findViewById(R.id.box9);
//        boxes[9] = (TextView) findViewById(R.id.box10);
//        boxes[10] = (TextView) findViewById(R.id.box11);
//        boxes[11] = (TextView) findViewById(R.id.box12);
//        boxes[12] = (TextView) findViewById(R.id.box13);
//        boxes[13] = (TextView) findViewById(R.id.box14);
//        boxes[14] = (TextView) findViewById(R.id.box15);
//        boxes[15] = (TextView) findViewById(R.id.box16);

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++){

                //Write number in box
                if(mat[i][j] != 0)
                    boxes[i][j].setText("" + mat[i][j]);
                else
                    boxes[i][j].setText("");

                //Set background color of box
                boxes[i][j].setBackgroundColor(findColor(mat[i][j]));
            }


        //Write score
        TextView sayScore = (TextView) findViewById(R.id.score);
        sayScore.setText("Score: " + score);

        if((won && !cont) || lost){
            Intent popUp = new Intent(TwentyActivity.this, PopTwenty.class);
            popUp.putExtra("Won", (won && (!cont)));
            startActivityForResult(popUp, 1);
        }
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean res = data.getBooleanExtra("check_restart", false);
                boolean cont = data.getBooleanExtra("check_cont", true);
                if(res) playTwenty.restartMat();

                playTwenty.setCont(cont);
                print(playTwenty.getMat(), playTwenty.getScore(), playTwenty.getWon(), playTwenty.getLost(), playTwenty.getCont());
            }
        }
    }
}


