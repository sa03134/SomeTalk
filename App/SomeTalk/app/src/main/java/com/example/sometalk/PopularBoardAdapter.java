package com.example.sometalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

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

        return convertView;
    }

    public void addItem(String Title, String Content) {
        PopularBoardItem item = new PopularBoardItem();

        item.setTitle(Title);
        item.setContent(Content);

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

        return convertView;
    }

    public void addItem(String Title, String Content) {
        TeenBoardItem item = new TeenBoardItem();

        item.setTitle(Title);
        item.setContent(Content);

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

        return convertView;
    }

    public void addItem(String Title, String Content) {
        TwentyBoardItem item = new TwentyBoardItem();

        item.setTitle(Title);
        item.setContent(Content);

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

        return convertView;
    }

    public void addItem(String Author, String Date, String Content) {
        CommentListItem item = new CommentListItem(Author, Date, Content);
        listViewItemList.add(item);
    }
}