package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
    }

    public void init() {
        ((MainActivity)MainActivity.context_main).w.getDashboard();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.total_user)).setText(((MainActivity)MainActivity.context_main).w.CBT.Dashboard.getCountUser());
        ((TextView)findViewById(R.id.total_post)).setText(((MainActivity)MainActivity.context_main).w.CBT.Dashboard.getCountPost());
        ((TextView)findViewById(R.id.total_select)).setText(((MainActivity)MainActivity.context_main).w.CBT.Dashboard.getCountAccept());
    }
}