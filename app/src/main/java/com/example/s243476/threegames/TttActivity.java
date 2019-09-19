package com.example.s243476.threegames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TttActivity extends AppCompatActivity {

    TextView[] ttt = new TextView[9];
    Tictactoe playTic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt);

        playTic = new Tictactoe(getApplicationContext());

        Log.d("whatapp", "tictac");

        ttt[0] = (TextView) findViewById(R.id.tic1);
        ttt[1] = (TextView) findViewById(R.id.tic2);
        ttt[2] = (TextView) findViewById(R.id.tic3);
        ttt[3] = (TextView) findViewById(R.id.tic4);
        ttt[4] = (TextView) findViewById(R.id.tic5);
        ttt[5] = (TextView) findViewById(R.id.tic6);
        ttt[6] = (TextView) findViewById(R.id.tic7);
        ttt[7] = (TextView) findViewById(R.id.tic8);
        ttt[8] = (TextView) findViewById(R.id.tic9);

        print(playTic.getBoard(), playTic.getTurn());

        for(int i = 0; i < 9; i++){
            final int thisVal = i;
            Log.d("in loop", "" + thisVal);
            ttt[i].setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d("onClick", "" + thisVal);
                    char winner = playTic.makeMove(thisVal);
                    print(playTic.getBoard(), playTic.getTurn());

                    if(winner != ' ') {
                        Intent popUp = new Intent(TttActivity.this, Pop.class);
                        popUp.putExtra("Disp_String", playTic.getWinner());
                        startActivityForResult(popUp, 1);
                    }
                }

            });

        }

        final Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playTic.restart();
                print(playTic.getBoard(), playTic.getTurn());
            }
        });
    }

    public void print(char[][] mat, char turn) {
        Log.d("print", "" + turn);


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {

                //Write number in box
                if (mat[i][j] != 0)
                    ttt[i * 3 + j].setText("" + mat[i][j]);
                else
                    ttt[i * 3 + j].setText("");
            }


        //Write turn
        TextView sayScore = (TextView) findViewById(R.id.turn);
        sayScore.setText("Turn: " + turn);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean res = data.getBooleanExtra("check_restart", false);
                playTic.restart();
                print(playTic.getBoard(), playTic.getTurn());
            }
        }
    }

}
