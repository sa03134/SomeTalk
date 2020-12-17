package com.example.sometalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {
    UserPostAdapter post_adapter;
    ListView post_listview;

    UserCommentAdapter comment_adapter;
    ListView comment_listview;

    ImageView imageView;
    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context_main = this;

        init();

        post_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                intent.putExtra("Link", ((MainActivity) MainActivity.context_main).w.CUT.Posts[position].getLink());
                startActivityForResult(intent, 1);
                post_adapter.notifyDataSetChanged();
            }
        });

    }


    public void init() {
        ((MainActivity) MainActivity.context_main).w.getProfile();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        post_adapter = new UserPostAdapter();

        post_listview = (ListView) findViewById(R.id.list_post); // Check
        post_listview.setVerticalScrollBarEnabled(false);

        post_listview.setAdapter(post_adapter);

        comment_adapter = new UserCommentAdapter();

        comment_listview = (ListView) findViewById(R.id.list_comment); // Check
        comment_listview.setVerticalScrollBarEnabled(false);

        comment_listview.setAdapter(comment_adapter);

        ((EditText) findViewById(R.id.permission)).setText(((MainActivity) MainActivity.context_main).w.CUT.User.getPERM());
        ((EditText) findViewById(R.id.permission)).setEnabled(false);
        ((EditText) findViewById(R.id.edit_nickname)).setText(((MainActivity) MainActivity.context_main).w.CUT.User.getNICK());
        ((EditText) findViewById(R.id.edit_id)).setText(((MainActivity) MainActivity.context_main).w.CUT.User.getID());
        ((EditText) findViewById(R.id.edit_password)).setText(((MainActivity) MainActivity.context_main).w.CUT.User.getPW());
        ((EditText) findViewById(R.id.edit_email)).setText(((MainActivity) MainActivity.context_main).w.CUT.User.getEMAIL());

        imageView = findViewById(R.id.User_Profile);

        Handler handler = new Handler();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ImageView iv = (ImageView) findViewById(R.id.User_Profile);
                    URL url = new URL(((MainActivity) MainActivity.context_main).w.CUT.profile_img);
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

        for (int i = 0; i < ((MainActivity) MainActivity.context_main).w.CUT.Posts.length; ++i) {
            UserPostItem Post = ((MainActivity) MainActivity.context_main).w.CUT.Posts[i];
            post_adapter.addItem(Post.getLink(), Post.getNo(), Post.getCategory(), Post.getTitle(), Post.getDate());
        }

        for (int i = 0; i < ((MainActivity) MainActivity.context_main).w.CUT.Comments.length; ++i) {
            UserCommentItem Post = ((MainActivity) MainActivity.context_main).w.CUT.Comments[i];
            comment_adapter.addItem(Post.getNo(), Post.getCategory(), Post.getContent(), Post.getDate());
        }
        setListViewHeightBasedOnChildrenPost(post_listview);
        setListViewHeightBasedOnChildrenComment(comment_listview);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });
    }

    public static void setListViewHeightBasedOnChildrenPost(@NonNull ListView listView) {
        UserPostAdapter listAdapter = (UserPostAdapter) listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildrenComment(@NonNull ListView listView) {
        UserCommentAdapter listAdapter = (UserCommentAdapter) listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void setAccount(View view) {
        // Change Profile
        ((MainActivity) MainActivity.context_main).w.setProfile(((EditText) findViewById(R.id.permission)).getText().toString(),
                ((EditText) findViewById(R.id.edit_id)).getText().toString(),
                ((EditText) findViewById(R.id.edit_nickname)).getText().toString(),
                ((EditText) findViewById(R.id.edit_password)).getText().toString(),
                ((EditText) findViewById(R.id.edit_email)).getText().toString());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    imageView.setImageBitmap(img);
                    in.close();
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.profile.jpeg";

                    FileOutputStream out = new FileOutputStream(path);
                    img.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    ((MainActivity) MainActivity.context_main).w.setPicture(path);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    Log.e("E", String.valueOf(e.getMessage()));
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 1) {
            init();
        }
    }

    public void gotoMentorFunction(View view) {
        String Perm = ((MainActivity) MainActivity.context_main).w.CUT.User.getPERM();
        if (Perm.equals("일반 사용자")) {
            Intent intent = new Intent(getApplicationContext(), MentorApplication.class);
            startActivityForResult(intent, 1);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MentorManagementActivity.class);
            startActivityForResult(intent, 1);
        }
    }

}