package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PostManagement extends AppCompatActivity {
    CrawlingManagementPostAdapter adapter;
    ListView listview;
    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_management);

        context_main = this;

        listview = (ListView) findViewById(R.id.list_manage_post);
        listview.setVerticalScrollBarEnabled(false);

        init();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                intent.putExtra("Link", ((MainActivity)MainActivity.context_main).w.CBT.Manage_Posts[position].getLink());
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
        adapter = new CrawlingManagementPostAdapter();
        listview.setAdapter(adapter);

        ((MainActivity)MainActivity.context_main).w.getManageBoardPage();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CBT.Manage_Posts.length; ++i) {
            CrawlingManagementPostItem CBI = ((MainActivity)MainActivity.context_main).w.CBT.Manage_Posts[i];
            adapter.addItem(CBI.getLink(), CBI.getNo(), CBI.getCategory(), CBI.getAuthor(), CBI.getTitle(), CBI.getDate());
        }
    }

    public void reset() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}