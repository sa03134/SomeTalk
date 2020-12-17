package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void register(View view) {
        String id = ((EditText)findViewById(R.id.edit_id)).getText().toString();
        String pw = ((EditText)findViewById(R.id.edit_password)).getText().toString();
        String nick = ((EditText)findViewById(R.id.edit_nickname)).getText().toString();
        String email = ((EditText)findViewById(R.id.edit_email)).getText().toString();

        if(id == "" || pw == "" || nick == "" || email == "") {
            Toast.makeText(this, "모든 입력란을 채운 뒤 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            WebLogic w = new WebLogic();
            w.register(id, pw, nick, email);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            finish();
        }
    }
}