package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_board);
    }

    public void gotoTwentyBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Twenty.class);
        startActivity(intent);
    }

    public void gotoTeenBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Teen.class);
        startActivity(intent);
    }
}