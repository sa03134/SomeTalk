package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class UserManagement extends AppCompatActivity {
    CrawlingManagementUserAdapter adapter;
    ListView listview;
    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        context_main = this;

        listview = (ListView) findViewById(R.id.list_manage_user);
        listview.setVerticalScrollBarEnabled(false);

        init();
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        init();
    }

    public void init() {
        adapter = new CrawlingManagementUserAdapter();
        listview.setAdapter(adapter);

        ((MainActivity)MainActivity.context_main).w.getManageUserPage();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CUT.Manage_Users.length; ++i) {
            CrawlingManagementUserItem CMUI = ((MainActivity)MainActivity.context_main).w.CUT.Manage_Users[i];
            adapter.addItem(CMUI.getPERM(), CMUI.getID(), CMUI.getPW(), CMUI.getNICK(), CMUI.getEMAIL());
        }
    }

    public void reset() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}