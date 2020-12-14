package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SomeTalk extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some_talk);
//        Log.e("Sometalk", getIntent().getExtras().getString("Cookie"));
    }

    public void management_mentor(View view) {
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }

    public void popularBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }

    public void selectBoard(View view) {
        Intent intent = new Intent(getApplicationContext(), SelectBoard.class);
        startActivity(intent);
    }
}