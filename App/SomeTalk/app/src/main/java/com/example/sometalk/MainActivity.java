package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public Map<String, String> UserCookie;
    public static Context context_main;
    public WebLogic w = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;
    }

    public void clickLogin(View v) {
        String email = ((EditText)findViewById(R.id.et_id)).getText().toString(); // id 입력란 : "admin";
        String password = ((EditText)findViewById(R.id.et_pass)).getText().toString();// pw 입력란 : "sometalk";

        email = "test2";
        password = "1";
//        email = "admin";
//        password = "sometalk";

        w = new WebLogic(email, password);

        if(w.attemptLogin()) { // 쿠키 값이 생겼다면(로그인에 성공했다면)
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SometalkMain.class);
            startActivity(intent);// SomeTalk Activity로 이동
        }
        else { // Fail
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickRegister(View v) {
        Intent intent = new Intent(getApplicationContext(), Join.class);
        startActivity(intent);
    }
}