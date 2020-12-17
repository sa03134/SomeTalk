package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PostManagementEdit extends AppCompatActivity {
    String Link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_management_edit);
        Link = getIntent().getExtras().getString("Link").replace("../ViewPost", "/Manage/EditPost");

        init();
    }

    public void init() {
        ((MainActivity)MainActivity.context_main).w.getPostFromAdmin(Link);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ((EditText)findViewById(R.id.manage_edit_post_content)).setText(((MainActivity)MainActivity.context_main).w.CBT.Manage_Post.getContent());
        ((EditText)findViewById(R.id.manage_edit_post_title)).setText(((MainActivity)MainActivity.context_main).w.CBT.Manage_Post.getTitle());
    }

    public void setPost(View view) {
        ((MainActivity)MainActivity.context_main).w.setPostFromAdmin(
                Link,
                ((EditText)findViewById(R.id.manage_edit_post_title)).getText().toString(),
                ((EditText)findViewById(R.id.manage_edit_post_content)).getText().toString()
        );

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }

    public void prev(View view) {
        finish();
    }
}