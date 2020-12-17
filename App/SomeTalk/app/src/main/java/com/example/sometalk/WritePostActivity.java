package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class WritePostActivity extends AppCompatActivity {
    private String Board_Type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        Board_Type = getIntent().getExtras().getString("Type");
        Spinner spinner = ((Spinner)findViewById(R.id.Category));

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("10대 질문");
        arrayList.add("20대 질문");
        arrayList.add("썸타는 중");
        arrayList.add("연애 중");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Board_Type = String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    public void setPost(View view) {
        String Title = ((EditText)findViewById(R.id.Title)).getText().toString();
        String Content = ((EditText)findViewById(R.id.Content)).getText().toString();

        ((MainActivity)MainActivity.context_main).w.setPost(String.valueOf(Integer.parseInt(Board_Type) + 1), Title, Content);

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