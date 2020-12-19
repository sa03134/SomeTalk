package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditPostActivity extends AppCompatActivity {
    String Type;
    String No;
    String Content;
    String Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Title      =  getIntent().getStringExtra("Title");
        No         =  getIntent().getStringExtra("No");
        Type       =  getIntent().getStringExtra("Type");
        Content    =  getIntent().getStringExtra("Content");

        ((EditText)findViewById(R.id.Title)).setText(Title);
        ((EditText)findViewById(R.id.Content)).setText(Content);
    }

    public void setPost(View view) {
        Title = ((EditText)findViewById(R.id.Title)).getText().toString();
        Content = ((EditText)findViewById(R.id.Content)).getText().toString();
        ((MainActivity)MainActivity.context_main).w.editPost(Type, No, Title, Content);

        if(Title.equals("")) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Content.equals("")) {
            Toast.makeText(this, "본문을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

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