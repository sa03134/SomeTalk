package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MentorRanking extends AppCompatActivity {
    MentorRankAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_ranking);

        listview = (ListView) findViewById(R.id.list_mentor_user); // Check
        listview.setVerticalScrollBarEnabled(false);

        init();
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        init();
    }

    public void init() {
        adapter = new MentorRankAdapter();
        listview.setAdapter(adapter);

        ((MainActivity)MainActivity.context_main).w.getMentorRanking();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CUT.Mentors.length; ++i) {
            MentorRankItem MRI = ((MainActivity)MainActivity.context_main).w.CUT.Mentors[i];
            adapter.addItem(MRI.getNo(), MRI.getAuthor(), MRI.getReply(), MRI.getAccept());
        }
    }
}

