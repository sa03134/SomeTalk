package com.example.sometalk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class PopularBoardAdapter extends BaseAdapter {

    private ArrayList<PopularBoardItem> listViewItemList = new ArrayList<>();

    // Constructor
    public PopularBoardAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_popular_board, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.PostAuthor) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;

        PopularBoardItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Title.setText(listViewItem.getTitle());

        if(listViewItem.getAccept() != "") {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);
        }

        return convertView;
    }

    public void addItem(String Title, String Content, String Accept) {
        PopularBoardItem item = new PopularBoardItem();

        item.setTitle(Title);
        item.setContent(Content);
        item.setAccept(Accept);

        listViewItemList.add(item);
    }
}



class TeenBoardAdapter extends BaseAdapter {

    private ArrayList<TeenBoardItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_board_teen, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.PostAuthor) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;

        TeenBoardItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Title.setText(listViewItem.getTitle());

        if(listViewItem.getAccept() != "") {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);
        }

        return convertView;
    }

    public void addItem(String Title, String Content, String Accept) {
        TeenBoardItem item = new TeenBoardItem();

        item.setTitle(Title);
        item.setContent(Content);
        item.setAccept(Accept);

        listViewItemList.add(item);
    }
}

class TwentyBoardAdapter extends BaseAdapter {

    private ArrayList<TwentyBoardItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_board_twenty, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.PostAuthor) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;

        TwentyBoardItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Title.setText(listViewItem.getTitle());

        if(listViewItem.getAccept() != "") {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);
        }

        return convertView;
    }

    public void addItem(String Title, String Content, String Accept) {
        TwentyBoardItem item = new TwentyBoardItem();

        item.setTitle(Title);
        item.setContent(Content);
        item.setAccept(Accept);

        listViewItemList.add(item);
    }
}

class SomeBoardAdapter extends BaseAdapter {

    private ArrayList<SomeBoardItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_board_teen, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.PostAuthor) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;

        SomeBoardItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Title.setText(listViewItem.getTitle());

        if(listViewItem.getAccept() != "") {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);
        }

        return convertView;
    }

    public void addItem(String Title, String Content, String Accept) {
        SomeBoardItem item = new SomeBoardItem();

        item.setTitle(Title);
        item.setContent(Content);
        item.setAccept(Accept);

        listViewItemList.add(item);
    }
}

class LoveBoardAdapter extends BaseAdapter {

    private ArrayList<LoveBoardItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_board_teen, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.PostAuthor) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;

        LoveBoardItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Title.setText(listViewItem.getTitle());

        if(listViewItem.getAccept() != "") {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);
        }

        return convertView;
    }

    public void addItem(String Title, String Content, String Accept) {
        LoveBoardItem item = new LoveBoardItem();

        item.setTitle(Title);
        item.setContent(Content);
        item.setAccept(Accept);

        listViewItemList.add(item);
    }
}


class CommentListAdapter extends BaseAdapter {
    private ArrayList<CommentListItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_comment_view, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.Comment_Content) ;
        TextView Author = (TextView) convertView.findViewById(R.id.Author) ;
        TextView Date = (TextView) convertView.findViewById(R.id.Date) ;

        CommentListItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Author.setText(listViewItem.getAuthor());
        Date.setText(listViewItem.getDate());

        if(listViewItem.getPkey() != null && listViewItem.getPkey() != "") {
            ImageView DeleteComment = (ImageView)convertView.findViewById(R.id.deleteCommentButton);
            DeleteComment.setVisibility(View.VISIBLE);
            DeleteComment.setEnabled(true);
            DeleteComment.setTag(listViewItem.getPkey());

            DeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pKey = String.valueOf(view.getTag()).split("_")[0];
                    ((MainActivity)MainActivity.context_main).w.deleteComment(pKey);
                    String position = String.valueOf(view.getTag()).split("_")[1];
                    listViewItemList.remove(Integer.parseInt(position));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((ViewPostActivity)ViewPostActivity.context_main).reset();
                }
            });

        }

        return convertView;
    }

    public void addItem(String Author, String Date, String Content) {
        CommentListItem item = new CommentListItem(Author, Date, Content);
        listViewItemList.add(item);
    }

    public void addItem(String Author, String Date, String Content, String pKey) {
        CommentListItem item = new CommentListItem(Author, Date, Content, pKey);
        listViewItemList.add(item);
    }
}

class ReplyListAdapter extends BaseAdapter {
    private ArrayList<ReplyListItem> listViewItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_reply_view, parent, false);
        }

        TextView Content = (TextView) convertView.findViewById(R.id.Reply_Content) ;
        TextView Author = (TextView) convertView.findViewById(R.id.Author) ;
        TextView Date = (TextView) convertView.findViewById(R.id.Date) ;

        ReplyListItem listViewItem = listViewItemList.get(position);

        Content.setText(listViewItem.getContent());
        Author.setText(listViewItem.getAuthor());
        Date.setText(listViewItem.getDate());

        String cmpString = listViewItem.getAuthor().substring(1);


        if(listViewItem.getAccepted()) {
            float scale = convertView.getResources().getDisplayMetrics().density;
            ImageView acceptImg = convertView.findViewById(R.id.acceptImage);
            acceptImg.setVisibility(View.VISIBLE);
            acceptImg.getLayoutParams().height = (int)(20 * scale);
            acceptImg.getLayoutParams().width = (int)(20 * scale);

//            acceptImg.setLayoutParams(layoutParams);
        }

        Boolean owner = ((MainActivity)MainActivity.context_main).w.CBT.RecentPost.getAuthor().equals((((MainActivity)MainActivity.context_main).w.getEmail()));

        if(owner && !((MainActivity)MainActivity.context_main).w.CBT.isAccept) {
            ImageView AcceptReply = (ImageView)convertView.findViewById(R.id.acceptReplyButton);
            AcceptReply.setVisibility(View.VISIBLE);
            AcceptReply.setEnabled(true);
            AcceptReply.setTag(listViewItem.getPkey() + "&Author=" + cmpString);

            AcceptReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pKey = String.valueOf(view.getTag()).replace("ViewPost?", "AcceptReply?");
                    ((MainActivity)MainActivity.context_main).w.acceptReply(pKey);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((ViewPostActivity)ViewPostActivity.context_main).reset();
                }
            });
        }

        if ( ( ((MainActivity)MainActivity.context_main).w.getEmail().equals(cmpString)
            || ((MainActivity)MainActivity.context_main).w.getEmail().equals("admin"))
                && !((MainActivity)MainActivity.context_main).w.CBT.isAccept) {
            // 채택이 안된 경우에, 직접 작성한 답변 삭제 가능.
            ImageView DeleteReply = (ImageView)convertView.findViewById(R.id.deleteReplyButton);
            DeleteReply.setVisibility(View.VISIBLE);
            DeleteReply.setEnabled(true);
            DeleteReply.setTag(listViewItem.getPkey());

            DeleteReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pKey = String.valueOf(view.getTag()).replace("ViewPost?", "DeleteReply?");
                    ((MainActivity)MainActivity.context_main).w.deleteReply(pKey);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((ViewPostActivity)ViewPostActivity.context_main).reset();
                }
            });
        }

        return convertView;
    }

    public void addItem(String Author, String Date, String Content) {
        ReplyListItem item = new ReplyListItem(Author, Date, Content);
        listViewItemList.add(item);
    }

    public void addItem(String Author, String Date, String Content, String pKey) {
        ReplyListItem item = new ReplyListItem(Author, Date, Content, pKey);
        listViewItemList.add(item);
    }

    public void addItem(String Author, String Date, String Content, String pKey, Boolean isAccepted) {
        ReplyListItem item = new ReplyListItem(Author, Date, Content, pKey, isAccepted);
        listViewItemList.add(item);
    }
}