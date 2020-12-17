package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class UserManagementProfile extends AppCompatActivity {
    String Perm;
    String Id;
    Spinner spinner;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_profile);

        Id = getIntent().getExtras().getString("Id");

        spinner = ((Spinner)findViewById(R.id.permission_manage));

        arrayList = new ArrayList<>();
        arrayList.add("일반 사용자 권한");
        arrayList.add("멘토 권한");
        arrayList.add("관리자 권한");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Perm = String.valueOf((int)Math.pow(2, position));
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        init();
    }

    public void init() {
        ((MainActivity)MainActivity.context_main).w.getProfileFromAdmin(Id);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Perm = ((MainActivity)MainActivity.context_main).w.CUT.User.getPERM();


        int pos = (int) ((Math.log10(Integer.parseInt(Perm)) / Math.log10(2)));

        spinner.setSelection(pos);


        ((EditText)findViewById(R.id.nickname_manage)).setText(((MainActivity)MainActivity.context_main).w.CUT.User.getNICK());
        ((EditText)findViewById(R.id.id_manage)).setText(((MainActivity)MainActivity.context_main).w.CUT.User.getID());
        ((EditText)findViewById(R.id.password_manage)).setText(((MainActivity)MainActivity.context_main).w.CUT.User.getPW());
        ((EditText)findViewById(R.id.email_manage)).setText(((MainActivity)MainActivity.context_main).w.CUT.User.getEMAIL());
    }

    public void editAccount(View view) {
        String Nick = ((EditText)findViewById(R.id.nickname_manage)).getText().toString();
        String Id = ((EditText)findViewById(R.id.id_manage)).getText().toString();
        String Password = ((EditText)findViewById(R.id.password_manage)).getText().toString();
        String Email = ((EditText)findViewById(R.id.email_manage)).getText().toString();
        ((MainActivity)MainActivity.context_main).w.setProfileFromAdmin(Nick, Perm, Password, Email, Id);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
        ((UserManagement)UserManagement.context_main).reset();
    }

    public void cancelEdit(View view) {
        finish();
    }
}