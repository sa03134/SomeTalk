package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SometalkMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sometalk_main);
    }

    public void gotoTwentyBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Twenty.class);
        startActivity(intent);
    }

    public void gotoTeenBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Teen.class);
        startActivity(intent);
    }

    public void popularBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }

    public void gotoSomeBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Some.class);
        startActivity(intent);
    }

    public void gotoLoveBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Love.class);
        startActivity(intent);
    }
}