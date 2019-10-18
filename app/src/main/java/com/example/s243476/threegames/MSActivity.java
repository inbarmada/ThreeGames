package com.example.s243476.threegames;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MSActivity extends AppCompatActivity {
    int height;
    int width;
    boolean flagOn;
    int borderColor;
    int hiddenSquareColor;
    int openSquareColor;
    int bombSquareColor;

    int l = 10;
    int w = 10;
    final TextView[][] boxes = new TextView[l][w];
    final MS minefield = new MS(l, w);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        flagOn = false;

        borderColor = ContextCompat.getColor(this, R.color.msborder);
        hiddenSquareColor = ContextCompat.getColor(this, R.color.mshiddensquare);
        openSquareColor = ContextCompat.getColor(this, R.color.msopensquare);
        bombSquareColor = ContextCompat.getColor(this, R.color.bombsquare);

        //Create the grid image and add boxes to tv mat
        createBoxes(l, w);

        //Add onClick function to boxes
        addOnClick(l, w);

    }

    public void flagFunction(View view){
        flagOn = !flagOn;
        if(flagOn)
            view.setBackgroundColor(Color.DKGRAY);
        else
            view.setBackgroundColor(Color.LTGRAY);
    }
    public void createBoxes(int l, int w){
        final TableLayout field = (TableLayout) findViewById(R.id.field);

        int borderStroke = Math.min(height/l/20, width/w*2/30);
        int rowHeight = height/l/2;
        int rowWidth = width/w*2/3;

        //Border above row
        final TableRow above = new TableRow(this);
        above.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

        //Make the row span all columns
        final TextView upBorder = new TextView(this);

        TableRow.LayoutParams params = (TableRow.LayoutParams) above.getLayoutParams();
        params.span = 2*w + 1; //amount of columns you will span

        upBorder.setHeight(borderStroke);
        upBorder.setBackgroundColor(borderColor);

        upBorder.setLayoutParams(params);
        //Finished fixing span, now add to tablelayout
        above.addView(upBorder);
        field.addView(above, new TableLayout.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        for(int i = 0; i < l; i++){
            final TableRow tr = new TableRow(this);
            field.addView(tr);
            for(int j = 0; j < w; j++){
                final TextView border = new TextView(this);
                border.setHeight(rowHeight);
                border.setWidth(borderStroke);
                border.setBackgroundColor(borderColor);
                tr.addView(border);

                final TextView tv = new TextView(this);
                tv.setHeight(rowHeight);
                tv.setWidth(rowWidth);
                tv.setBackgroundColor(hiddenSquareColor);
                tr.addView(tv);

                //Add the textview to the tv mat
                boxes[i][j] = tv;
            }

            //Edge border
            final TextView border = new TextView(this);
            border.setHeight(rowHeight);
            border.setWidth(borderStroke);
            border.setBackgroundColor(borderColor);
            tr.addView(border);

            //Border below row
            final TableRow below = new TableRow(this);
            below.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

            //Make the row span all columns
            final TextView lowBorder = new TextView(this);

            TableRow.LayoutParams params1 = (TableRow.LayoutParams) below.getLayoutParams();
            params1.span = 2*w + 1; //amount of columns you will span

            lowBorder.setHeight(borderStroke);
            lowBorder.setBackgroundColor(borderColor);

            lowBorder.setLayoutParams(params1);
            //Finished fixing span, now add to tablelayout
            below.addView(lowBorder);
            field.addView(below, new TableLayout.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public void addOnClick(final int l, final int w){
        for(int i = 0; i < l*w; i++){
            final int r = i/w;
            final int c = i%w;
            boxes[i/w][i%w].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean won = false;
                    boolean died = false;
                    if(flagOn)
                        minefield.flag(r, c);
                    else {
                        minefield.open(r, c);
                        won = minefield.won();
                        died = minefield.dead();
                    }
                    reprintBoxes();

                    if(died)
                        dead();
                    else if(won)
                        won();
                }
            });
        }
    }

    public void dead(){
        Intent popUp = new Intent(MSActivity.this, Pop.class);
        popUp.putExtra("Disp_String", "You lost!");
        startActivityForResult(popUp, 1);
    }

    public void won(){
        Intent popUp = new Intent(MSActivity.this, Pop.class);
        popUp.putExtra("Disp_String", "Great job! You won!");
        startActivityForResult(popUp, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean res = data.getBooleanExtra("check_restart", false);
                restart();
            }
        }
    }

    public void restart(){
        Log.d("resetting", "happenin'");
        minefield.reset(l, w);
        minefield.printWholeGrid();
        minefield.printGrid();
        for(int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                boxes[i][j].setText("");
                boxes[i][j].setBackgroundColor(hiddenSquareColor);
            }
        }
    }

    public void restartButton(View view){
        Log.d("resetting", "whattup'");
        restart();
    }

    public void reprintBoxes(){
        int[][] grid = minefield.retGrid();
        int[][] playerView = minefield.retPlayerView();

        for(int i = 0; i < l; i++){
            for(int j = 0; j < w; j++){

                if(playerView[i][j] == 1){
                    boxes[i][j].setText("");
                    boxes[i][j].setBackgroundColor(openSquareColor);

                    if(grid[i][j] != 0) {
                        if(grid[i][j] != -1)
                            boxes[i][j].setText("" + grid[i][j]);
                        else {
                            boxes[i][j].setText("*");
                            boxes[i][j].setBackgroundColor(bombSquareColor);
                        }
                    }
                }else if(playerView[i][j] == 2){
                    boxes[i][j].setText("X");
                }else if(playerView[i][j] == 0){
                    boxes[i][j].setText("");
                }

            }
        }
    }


}
