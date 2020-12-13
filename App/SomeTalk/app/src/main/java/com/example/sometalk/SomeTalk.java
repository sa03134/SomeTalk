package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SomeTalk extends AppCompatActivity {

    private Button popularBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some_talk);
        Log.e("Sometalk", getIntent().getExtras().getString("Cookie"));
        popularBtn = (Button)findViewById(R.id.popluar_board);
        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
                startActivity(intent);
            }
        });
    }

    public void management_mentor(View view) {
        //Log.e("ee", " aaa");
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }

    public void popular(View view) {
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }
}