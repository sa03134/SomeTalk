package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.net.URL;

public class SometalkMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sometalk_main);

        init();
    }

    public void init() {
        ((MainActivity) MainActivity.context_main).w.getProfile();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(((MainActivity) MainActivity.context_main).w.CUT.User.getPERM().equals("관리자 계정")) {
            ((LinearLayout)findViewById(R.id.admin_layout)).setVisibility(View.VISIBLE);
            ((LinearLayout)findViewById(R.id.admin_layout)).setEnabled(true);
        }
        else {
            ((LinearLayout)findViewById(R.id.admin_layout)).setVisibility(View.INVISIBLE);
            ((LinearLayout)findViewById(R.id.admin_layout)).setEnabled(false);
        }

        Handler handler = new Handler();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ImageView iv = (ImageView) findViewById(R.id.sometalkAd);
                    URL url = new URL("http://www.qerogram.kro.kr:41528/static/ad.jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            iv.setImageBitmap(bm);
                        }
                    });
                    iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch (Exception e) {

                }

            }
        });

        t.start();
    }

    public void gotoTwentyBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Twenty.class);
        startActivity(intent);
    }

    public void gotoTeenBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Teen.class);
        startActivity(intent);
    }

    public void popularBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),PopularBoard.class);
        startActivity(intent);
    }

    public void gotoSomeBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Some.class);
        startActivity(intent);
    }

    public void gotoLoveBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),Board_Love.class);
        startActivity(intent);
    }

    public void gotoWriteBoard(View view) {
        Intent intent = new Intent(getApplicationContext(), WritePostActivity.class);
        intent.putExtra("Type", "1");
        startActivityForResult(intent, 1);
    }

    public void gotoUserProfile(View view) {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

    public void gotoLogout(View view) {
        finish();
    }

    public void gotoManagePostBoard(View view) {
        Intent intent = new Intent(getApplicationContext(),PostManagement.class);
        startActivity(intent);
    }

    public void gotoManageUser(View view) {
        Intent intent = new Intent(getApplicationContext(),UserManagement.class);
        startActivity(intent);
    }

    public void gotoDashboard(View view) {
        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
    }

    public void gotoMentorRank(View view) {
        Intent intent = new Intent(getApplicationContext(),MentorRanking.class);
        startActivity(intent);
    }
}