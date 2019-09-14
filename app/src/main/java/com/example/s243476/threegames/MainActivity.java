package com.example.s243476.threegames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void twentygo(View view){
        Intent randomIntent = new Intent(this, TwentyActivity.class);
        startActivity(randomIntent);
    }

    public void tictacgo(View view){
        Log.d("got here", "ya tictacgo");
        Intent randomIntent = new Intent(this, TttActivity.class);
        startActivity(randomIntent);
    }
}
