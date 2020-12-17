package com.example.sometalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrawlingManagementPostItem extends UserPostItem{
    private String Author;

    CrawlingManagementPostItem(String Link, String No, String Category, String Author, String Title, String Date) {
        super(Link, No, Category, Title, Date);
        setAuthor(Author);
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}

class CrawlingManagementPostAdapter extends BaseAdapter {

    private ArrayList<CrawlingManagementPostItem> listViewItemList = new ArrayList<>();

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
            convertView = inflater.inflate(R.layout.content_post_management, parent, false);
        }

        TextView Category = (TextView) convertView.findViewById(R.id.manage_post_category) ;
        TextView Title = (TextView) convertView.findViewById(R.id.manage_post_title) ;
        TextView Date = (TextView) convertView.findViewById(R.id.manage_post_date) ;
        TextView No = (TextView) convertView.findViewById(R.id.manage_post_number) ;
        TextView Author = (TextView) convertView.findViewById(R.id.manage_post_author) ;

        CrawlingManagementPostItem listViewItem = listViewItemList.get(position);

        Category.setText(listViewItem.getCategory());
        Title.setText(listViewItem.getTitle());
        Date.setText(listViewItem.getDate());
        No.setText(listViewItem.getNo());
        Author.setText(listViewItem.getAuthor());

        ImageView editPost = convertView.findViewById(R.id.manage_edit_post);
        editPost.setTag(listViewItem.getLink());

        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Link = String.valueOf(view.getTag());
                Intent intent = new Intent(context, PostManagementEdit.class);
                intent.putExtra("Link", Link);
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });

        ImageView deletePost = convertView.findViewById(R.id.manage_delete_post);
        deletePost.setTag(listViewItem.getLink());

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Link = String.valueOf(view.getTag()).replace("ViewPost", "DeletePost");
                ((MainActivity)MainActivity.context_main).w.deletePostFromAdmin(Link);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((PostManagement)PostManagement.context_main).reset();
            }
        });


        return convertView;
    }

    public void addItem(String Link, String No, String Category, String Author, String Title, String Date) {
        CrawlingManagementPostItem item = new CrawlingManagementPostItem(Link, No, Category, Author, Title, Date);

        listViewItemList.add(item);
    }
}


class CrawlingManagementUserItem extends CrawlingUserStructure{
    CrawlingManagementUserItem(String PERM, String ID, String NICK, String EMAIL) {
        super(PERM, ID, NICK, EMAIL);
    }
}

class CrawlingManagementUserAdapter extends BaseAdapter {

    private ArrayList<CrawlingManagementUserItem> listViewItemList = new ArrayList<>();

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
            convertView = inflater.inflate(R.layout.content_user_management, parent, false);
        }

        TextView Id = (TextView) convertView.findViewById(R.id.manage_user_id) ;
        TextView Email = (TextView) convertView.findViewById(R.id.manage_user_email) ;
        TextView Nick = (TextView) convertView.findViewById(R.id.manage_user_nick) ;
        TextView Perm = (TextView) convertView.findViewById(R.id.manage_user_perm) ;


        CrawlingManagementUserItem listViewItem = listViewItemList.get(position);

        Id.setText(listViewItem.getID());
        Email.setText(listViewItem.getEMAIL());
        Nick.setText(listViewItem.getNICK());
        Perm.setText(listViewItem.getPERM());

        ImageView editUser = (ImageView)convertView.findViewById(R.id.manage_edit_user);
        editUser.setTag(listViewItem.getID());

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = String.valueOf(view.getTag());
                Intent intent = new Intent(context, UserManagementProfile.class);
                intent.putExtra("Id", Id);
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });

        return convertView;
    }

    public void addItem(String PERM, String ID, String PW, String NICK, String EMAIL) {
        CrawlingManagementUserItem item = new CrawlingManagementUserItem(PERM, ID, NICK, EMAIL);
        item.setPW(PW);

        listViewItemList.add(item);
    }
}

class CrawlingManagementEditPostItem {
    private String Link;
    private String Title;
    private String Content;

    CrawlingManagementEditPostItem(String Link, String Title, String Content) {
        setLink(Link);
        setTitle(Title);
        setContent(Content);
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}