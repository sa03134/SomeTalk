package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Board_Twenty extends AppCompatActivity {
    TwentyBoardAdapter adapter = null;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__twenty);

        listview = (ListView) findViewById(R.id.twenty_board_list);
        listview.setVerticalScrollBarEnabled(false);

        init();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                intent.putExtra("Link", ((MainActivity)MainActivity.context_main).w.CBT.CBI[position].getLink());
                startActivityForResult(intent, 1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        init();
    }

    public void init() {
        adapter = new TwentyBoardAdapter();
        listview.setAdapter(adapter);

        ((MainActivity)MainActivity.context_main).w.getTwentyBoard(1);

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