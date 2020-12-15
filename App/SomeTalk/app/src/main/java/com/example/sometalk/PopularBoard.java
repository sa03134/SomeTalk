package com.example.sometalk;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class PopularBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_board);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PopularBoardAdapter adapter = new PopularBoardAdapter();

        ListView listview = (ListView) findViewById(R.id.popular_board_list);
        listview.setVerticalScrollBarEnabled(false);
        listview.setAdapter(adapter);


        ((MainActivity)MainActivity.context_main).w.getPopularPage();


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ((CrawlingBoardTask)((MainActivity)MainActivity.context_main).w.CBT).CBI_COUNT; ++i) {
            CrawlingBoardItem CBI = ((MainActivity)MainActivity.context_main).w.CBT.CBI[i];
            adapter.addItem(CBI.getTitle(), CBI.getAuthor() + " | " + CBI.getDatetime());
        }
    }
}