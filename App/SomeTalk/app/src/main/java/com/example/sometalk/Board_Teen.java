package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Board_Teen extends AppCompatActivity {
    TeenBoardAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__teen);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WritePostActivity.class);
                intent.putExtra("Type", "1");
                startActivityForResult(intent, 1);
                adapter.notifyDataSetChanged();
            }
        });

        listview = (ListView) findViewById(R.id.teen_board_list);
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
        adapter = new TeenBoardAdapter();
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