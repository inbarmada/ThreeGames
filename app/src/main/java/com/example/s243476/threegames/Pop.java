package com.example.s243476.threegames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("In pop", "so far so good1");

        setContentView(R.layout.popwindow);


        Log.d("In pop", "so far so good");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.3));

        String msg = getIntent().getStringExtra("Disp_String");

        TextView showMsg = (TextView) findViewById(R.id.popUpMsg);
        showMsg.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        showMsg.setText(msg);

        final Button restartButton = findViewById(R.id.restart);
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
