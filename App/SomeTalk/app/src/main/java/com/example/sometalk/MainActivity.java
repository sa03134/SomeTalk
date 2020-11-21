package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public Map<String, String> UserCookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void clickLogin(View v) {
        String email = ((EditText)findViewById(R.id.et_id)).getText().toString(); // id 입력란 : "admin";
        String password = ((EditText)findViewById(R.id.et_pass)).getText().toString();// pw 입력란 : "sometalk";

        WebLogic mAuthTask = new WebLogic(email, password);
        try {
            UserCookie = mAuthTask.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(UserCookie.toString().length() > 10) { // 쿠키 값이 생겼다면(로그인에 성공했다면)
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SomeTalk.class);
            intent.putExtra("Cookie", UserCookie.toString()); // Cookie 값을 액티비티에 포함.
            startActivity(intent);// SomeTalk Activity로 이동
        }
        else { // Fail
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }
}