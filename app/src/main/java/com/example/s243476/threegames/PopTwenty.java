package com.example.s243476.threegames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopTwenty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.3));

        boolean won = getIntent().getBooleanExtra("Won", false);

        TextView showMsg = (TextView) findViewById(R.id.popUpMsg);

        final Button restartButton = findViewById(R.id.restart);
        final Button contButton = findViewById(R.id.cont);


        if(won) {
            showMsg.setText("Great job! You won!\nWould you like to continue?");
            contButton.setVisibility(View.VISIBLE);
            contButton.setText("Yes!");
            contButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("check_cont", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

//            restartButton.setVisibility(View.GONE);
            restartButton.setText("No");
            restartButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("check_restart", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }

        else {
            showMsg.setText("Oh no! You lost!");
            restartButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("check_restart", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}
