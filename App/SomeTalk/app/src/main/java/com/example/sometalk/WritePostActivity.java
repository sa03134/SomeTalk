package com.example.sometalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class WritePostActivity extends AppCompatActivity {
    private String Board_Type = "";
    String path;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.temp_post.jpeg";

                    ((TextView)findViewById(R.id.FileName)).setText("첨부 됨");

                    FileOutputStream out = new FileOutputStream(path);
                    img.compress(Bitmap.CompressFormat.JPEG, 100, out);

                } catch (Exception e) {
                    Log.e("E", String.valueOf(e.getMessage()));
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 1) {
//            init();
        }
    }

    public void setPost(View view) {
        String Title = ((EditText)findViewById(R.id.Title)).getText().toString();
        String Content = ((EditText)findViewById(R.id.Content)).getText().toString();

        ((MainActivity)MainActivity.context_main).w.setPost(String.valueOf(Integer.parseInt(Board_Type) + 1), Title, Content, path);

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

    public void uploadFile(View view) {
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }
}