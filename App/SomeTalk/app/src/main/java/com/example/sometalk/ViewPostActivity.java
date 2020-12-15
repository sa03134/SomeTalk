package com.example.sometalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewPostActivity extends AppCompatActivity {
    String Link = "";
    String No = "";
    String Type = "";
    ListView listview ;
    CommentListAdapter adapter;
    Button EditPost = null;
    Button DeletePost = null;
    CrawlingBoardItem CBI = null;

    public static void setListViewHeightBasedOnChildren(@NonNull ListView listView) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        EditPost = (Button)findViewById(R.id.editPostButton);
        EditPost.setEnabled(false);
        EditPost.setVisibility(View.INVISIBLE);

        DeletePost = (Button)findViewById(R.id.deletePostButton);
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

        ((TextView)findViewById(R.id.TitleText)).setText(CBI.getTitle());
        ((TextView)findViewById(R.id.AuthorText)).setText(CBI.getAuthor());
        ((TextView)findViewById(R.id.DateTimeText)).setText(CBI.getDatetime());
        ((TextView)findViewById(R.id.ContentText)).setText(CBI.getContent() + "\n");
        ((TextView)findViewById(R.id.Comment_Count)).setText(" 댓글 "  + String.valueOf(((MainActivity) MainActivity.context_main).w.CBT.Comments.length)+ "개");

        adapter = new CommentListAdapter() ;

        listview = (ListView) findViewById(R.id.list_comment);
        listview.setVerticalScrollBarEnabled(false);

        listview.setAdapter(adapter);


        for(int i = 0; i < ((MainActivity) MainActivity.context_main).w.CBT.Comments.length; ++i) {
            adapter.addItem("  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getAuthor(),
                    "  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getDate(),
                    "  " + ((MainActivity) MainActivity.context_main).w.CBT.Comments[i].getContent());
        }

        setListViewHeightBasedOnChildren(listview);
    }

    public void setComment(View view) {
        String Comment = ((EditText)findViewById(R.id.Comment)).getText().toString();

        ((MainActivity)MainActivity.context_main).w.setComment(Type, No, Comment);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);

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

//        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}