package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Board_Teen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__teen);


        TeenBoardAdapter adapter = new TeenBoardAdapter();

        ListView listview = (ListView) findViewById(R.id.teen_board_list);
        listview.setVerticalScrollBarEnabled(false);
        listview.setAdapter(adapter);

        ((MainActivity)MainActivity.context_main).w.getTeenBoard(1);

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