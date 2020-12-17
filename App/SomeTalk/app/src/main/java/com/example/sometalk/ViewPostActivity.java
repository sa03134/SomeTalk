package com.example.sometalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewPostActivity extends AppCompatActivity {
    String Link = "";
    String No = "";
    String Type = "";
    ListView Comment_listview ;
    CommentListAdapter Comment_adapter;

    ListView Reply_listview ;
    ReplyListAdapter Reply_adapter;

    ImageView EditPost = null;
    ImageView DeletePost = null;
    CrawlingBoardItem CBI = null;
    public static Context context_main;


    public static void setListViewHeightBasedOnChildrenComment(@NonNull ListView listView) {
        CommentListAdapter listAdapter = (CommentListAdapter) listView.getAdapter();

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

    public static void setListViewHeightBasedOnChildrenReply(@NonNull ListView listView) {
        ReplyListAdapter listAdapter = (ReplyListAdapter) listView.getAdapter();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        context_main = this;

        EditPost = findViewById(R.id.editPostButton);
        EditPost.setEnabled(false);
        EditPost.setVisibility(View.INVISIBLE);

        DeletePost = findViewById(R.id.deletePostButton);
        DeletePost.setEnabled(false);
        DeletePost.setVisibility(View.INVISIBLE);

        Link = getIntent().getStringExtra("Link");
        No = Link.split("=")[2];
        Type = Link.split("=")[1].split("&")[0];

        init();
    }

    public void init() {
        ((MainActivity)MainActivity.context_main).w.getPost(Link);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CBI = ((MainActivity)MainActivity.context_main).w.CBT.RecentPost;

        if(CBI.getAuthor().equals(((MainActivity)MainActivity.context_main).w.getEmail())
                || ((MainActivity)MainActivity.context_main).w.getEmail().equals("admin")) {
            EditPost.setEnabled(true);
            EditPost.setVisibility(View.VISIBLE);

            DeletePost.setEnabled(true);
            DeletePost.setVisibility(View.VISIBLE);
        }
        // Post Content
        ((TextView)findViewById(R.id.TitleText)).setText(CBI.getTitle());
        ((TextView)findViewById(R.id.AuthorText)).setText(CBI.getAuthor());
        ((TextView)findViewById(R.id.DateTimeText)).setText(CBI.getDatetime());
        ((TextView)findViewById(R.id.ContentText)).setText(CBI.getContent() + "\n");


        // Comments
        ((TextView)findViewById(R.id.Comment_Count)).setText(" 댓글 "  + String.valueOf(((MainActivity) MainActivity.context_main).w.CBT.Comments.length)+ "개");

        Comment_adapter = new CommentListAdapter() ;

        Comment_listview = (ListView) findViewById(R.id.list_comment);
        Comment_listview.setVerticalScrollBarEnabled(false);

        Comment_listview.setAdapter(Comment_adapter);


        for(int i = 0; i < ((MainActivity) MainActivity.context_main).w.CBT.Comments.length; ++i) {
            Comment_adapter.addItem("  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getAuthor(),
                    "  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getDate(),
                    "  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getContent(),
                    ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getPkey());
        }

        setListViewHeightBasedOnChildrenComment(Comment_listview);


        // Reply
        Reply_adapter = new ReplyListAdapter();

        Reply_listview = (ListView) findViewById(R.id.list_reply);
        Reply_listview.setVerticalScrollBarEnabled(false);

        Reply_listview.setAdapter(Reply_adapter);

        for(int i = 0; i < ((MainActivity)MainActivity.context_main).w.CBT.Replys.length; ++i) {
            Reply_adapter.addItem(" " + ((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getAuthor(),
                                    " " + ((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getDate(),
                                " " + ((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getContent(),
                                                ((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getPkey(),
                                                ((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getAccepted());


            if(((MainActivity)MainActivity.context_main).w.CBT.Replys[i].getAuthor().equals(((MainActivity)MainActivity.context_main).w.getEmail())) {
                // Do not write Reply(duplicate)
                ((EditText)findViewById(R.id.Reply)).setEnabled(false);
                ((EditText)findViewById(R.id.Reply)).setFocusable(false);
                ((EditText)findViewById(R.id.Reply)).setVisibility(View.INVISIBLE);

                ((ImageView)findViewById(R.id.btn_replyPush)).setEnabled(false);
                ((ImageView)findViewById(R.id.btn_replyPush)).setFocusable(false);
                ((ImageView)findViewById(R.id.btn_replyPush)).setVisibility(View.INVISIBLE);
            }
        }
//
        if(Reply_adapter.getCount() > 0) {
            ((TextView)findViewById(R.id.Reply_Count)).setText(" 답변 "  + String.valueOf(((MainActivity) MainActivity.context_main).w.CBT.Replys.length)+ "개");
            setListViewHeightBasedOnChildrenReply(Reply_listview);
        }
    }

    public void setComment(View view) {
        String Comment = ((EditText)findViewById(R.id.Comment)).getText().toString();


        if(Comment.length() > 0) {
            ((MainActivity) MainActivity.context_main).w.setComment(Type, No, Comment);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else {
            Toast.makeText(this, "댓글을 작성해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePost(View view) {
        ((MainActivity)MainActivity.context_main).w.deletePost(Type, No);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }


    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        init();
    }


    public void editPost(View view) {
        String Title = CBI.getTitle();
        String Content = CBI.getContent();
        Intent intent = new Intent(getApplicationContext(), EditPostActivity.class);
        intent.putExtra("Title", Title);
        intent.putExtra("Content", Content);
        intent.putExtra("Type", Type);
        intent.putExtra("No", No);
        startActivityForResult(intent, 1);
        overridePendingTransition(0, 0);
    }

    public void reset() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void setReply(View view) {
        if(((EditText)findViewById(R.id.Reply)).getText().toString().length() > 0) {
            String Reply = ((EditText)findViewById(R.id.Reply)).getText().toString();
            ((MainActivity)MainActivity.context_main).w.setReply(Type, No, Reply);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else {
            Toast.makeText(this, "답변을 작성해주세요.", Toast.LENGTH_SHORT).show();
        }

    }
}