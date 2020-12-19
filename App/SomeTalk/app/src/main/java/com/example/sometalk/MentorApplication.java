package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MentorApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_application);
    }


    public void submitMentorPerm(View view) {
        String Ans1 = ((EditText)findViewById(R.id.q1_answer)).getText().toString();
        String Ans2 = ((EditText)findViewById(R.id.q2_answer)).getText().toString();

        if(Ans1.equals("")||Ans2.equals("")) {
            Toast.makeText(this, "답변을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "지원되었습니다.", Toast.LENGTH_SHORT).show();

        ((MainActivity)MainActivity.context_main).w.requestMentorPerm(Ans1, Ans2);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }

    public void cancel_RMentorPerm(View view) {
        finish();
    }
}