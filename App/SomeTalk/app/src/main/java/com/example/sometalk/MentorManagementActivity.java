package com.example.sometalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MentorManagementActivity extends AppCompatActivity {
    MentorReplyAdapter reply_adapter;
    ListView reply_listview;

    MentorRequestAdapter request_adapter;
    ListView request_listview;

    ImageView imageView;
    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_management);
        context_main = this;

        init();

        reply_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                intent.putExtra("Link", ((MainActivity) MainActivity.context_main).w.CUT.Replys[position].getLink());
                startActivityForResult(intent, 1);
                reply_adapter.notifyDataSetChanged();
            }
        });
    }

    void init() {
        reply_adapter = new MentorReplyAdapter();
        reply_listview = (ListView) findViewById(R.id.list_reply); // Check
        reply_listview.setVerticalScrollBarEnabled(false);
        reply_listview.setAdapter(reply_adapter);

        request_adapter = new MentorRequestAdapter();
        request_listview = (ListView) findViewById(R.id.list_request_mentor);
        request_listview.setVerticalScrollBarEnabled(false);
        request_listview.setAdapter(request_adapter);

        ((MainActivity) MainActivity.context_main).w.getMentorProfile();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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

        ((TextView)findViewById(R.id.username)).setText(((MainActivity)MainActivity.context_main).w.CUT.Mentor.getID());
        ((TextView)findViewById(R.id.userpoint)).setText(((MainActivity)MainActivity.context_main).w.CUT.Mentor.getPOINT() + "  ");

        ((TextView)findViewById(R.id.username)).bringToFront();

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CUT.Replys.length; ++i) {
            MentorReplyItem Reply = ((MainActivity) MainActivity.context_main).w.CUT.Replys[i];
            reply_adapter.addItem(Reply.getAccept(), Reply.getLink(), Reply.getNo(), Reply.getCategory(), Reply.getContent(), Reply.getDate());
        }

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CUT.Requests.length; ++i) {
            MentorRequestItem Request = ((MainActivity) MainActivity.context_main).w.CUT.Requests[i];
            request_adapter.addItem(Request.getId(), Request.getDate(), Request.getAns1(), Request.getAns2());
        }

        setListViewHeightBasedOnChildrenReply(reply_listview);
        setListViewHeightBasedOnChildrenRequest(request_listview);

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

    public static void setListViewHeightBasedOnChildrenReply(@NonNull ListView listView) {
        MentorReplyAdapter listAdapter = (MentorReplyAdapter) listView.getAdapter();

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
    public static void setListViewHeightBasedOnChildrenRequest(@NonNull ListView listView) {
        MentorRequestAdapter listAdapter = (MentorRequestAdapter) listView.getAdapter();

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
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/profile.jpeg";

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
    }

    public void reset() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void gotoAdBoard(View view) {
        Intent intent = new Intent(getApplicationContext(), Mentor_Ad.class);
        startActivityForResult(intent, 1);
    }
}