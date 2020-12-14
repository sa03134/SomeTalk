package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        String Link = getIntent().getStringExtra("Link");

        ((MainActivity)MainActivity.context_main).w.getPost(Link);


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CrawlingBoardItem CBI = ((MainActivity)MainActivity.context_main).w.CBT.RecentPost;

        ((TextView)findViewById(R.id.TitleText)).setText(CBI.getTitle());
        ((TextView)findViewById(R.id.AuthorText)).setText(CBI.getAuthor());
        ((TextView)findViewById(R.id.DateTimeText)).setText(CBI.getDatetime());
        ((TextView)findViewById(R.id.ContentText)).setText(CBI.getContent());

    }
}