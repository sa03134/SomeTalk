package com.example.sometalk;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrawlingUserStructure {
    private String PERM;
    private String ID;
    private String PW;
    private String NICK;
    private String EMAIL;

    CrawlingUserStructure(String PERM, String ID, String PW, String NICK, String EMAIL) {
        setPERM(PERM);
        setID(ID);
        setPW(PW);
        setNICK(NICK);
        setEMAIL(EMAIL);
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getNICK() {
        return NICK;
    }

    public void setNICK(String NICK) {
        this.NICK = NICK;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPERM() {
        return PERM;
    }

    public void setPERM(String PERM) {
        this.PERM = PERM;
    }
}

class CrawlingMentorStructure {
    private String ID;
    private String POINT;


    CrawlingMentorStructure(String ID, String POINT) {
        setID(ID);
        setPOINT(POINT);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPOINT() {
        return POINT;
    }

    public void setPOINT(String POINT) {
        this.POINT = POINT;
    }
}


class UserPostItem {
    private String Link;
    private String No;
    private String Category;
    private String Title;
    private String Date;

    UserPostItem(String Link, String No, String Category, String Title, String Date) {
        setLink(Link);
        setNo(No);
        setCategory(Category);
        setTitle(Title);
        setDate(Date);
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDate() {
        return Date;
    }
}

class UserPostAdapter extends BaseAdapter {
    private ArrayList<UserPostItem> listViewItemList = new ArrayList<>();

    // Constructor
    public UserPostAdapter() {

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
            convertView = inflater.inflate(R.layout.content_board_user, parent, false);
        }

        TextView Category = (TextView) convertView.findViewById(R.id.PostCategory) ;
        TextView Title = (TextView) convertView.findViewById(R.id.PostTitle) ;
        TextView Date = (TextView) convertView.findViewById(R.id.PostDate) ;

        UserPostItem listViewItem = listViewItemList.get(position);

        Category.setText(listViewItem.getCategory());
        Title.setText(listViewItem.getTitle());
        Date.setText(listViewItem.getDate());//listViewItem.getDate());

        return convertView;
    }

    public void addItem(String Link, String No, String Category, String Title, String Date) {
        UserPostItem item = new UserPostItem(Link, No, Category, Title, Date);
        listViewItemList.add(item);
    }
}

class UserCommentItem {
    private String Category;
    private String No;
    private String Content;
    private String Date;

    UserCommentItem(String Category, String No, String Content, String Date) {
        setCategory(Category);
        setNo(No);
        setContent(Content);
        setDate(Date);
    }


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}

class UserCommentAdapter extends BaseAdapter {
    private ArrayList<UserCommentItem> listViewItemList = new ArrayList<>();

    // Constructor
    public UserCommentAdapter() {

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
            convertView = inflater.inflate(R.layout.content_board_user_comment, parent, false);
        }

        TextView Category = (TextView) convertView.findViewById(R.id.CommentCategory) ;
        TextView Content = (TextView) convertView.findViewById(R.id.CommentContent) ;
        TextView No = (TextView) convertView.findViewById(R.id.CommentNo) ;
        TextView Date = (TextView) convertView.findViewById(R.id.CommentDate) ;

        UserCommentItem listViewItem = listViewItemList.get(position);

        Category.setText(listViewItem.getCategory());
        Content.setText(listViewItem.getContent());
        No.setText(listViewItem.getNo());
        Date.setText(listViewItem.getDate());//listViewItem.getDate());

        return convertView;
    }

    public void addItem(String No, String Category, String Content, String Date) {
        UserCommentItem item = new UserCommentItem(No, Category, Content, Date);
        listViewItemList.add(item);
    }
}

class MentorReplyItem extends UserCommentItem {
    private boolean Accept;
    private String Link;

    MentorReplyItem(boolean Accept, String Link, String Category, String No, String Content, String Date) {
        super(Category, No, Content, Date);
        setAccept(Accept);
        setLink(Link);
    }

    public boolean getAccept() {  return Accept;  }

    public void setAccept(boolean accept) {  Accept = accept;  }

    public String getLink() { return Link; }

    public void setLink(String link) { Link = link; }
}

class MentorReplyAdapter extends BaseAdapter {
    private ArrayList<MentorReplyItem> listViewItemList = new ArrayList<>();

    // Constructor
    public MentorReplyAdapter() {

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
            convertView = inflater.inflate(R.layout.content_mentor_reply, parent, false);
        }

        TextView Category = (TextView) convertView.findViewById(R.id.ReplyCategory) ;
        TextView Content = (TextView) convertView.findViewById(R.id.ReplyContent) ;
        TextView Date = (TextView) convertView.findViewById(R.id.ReplyDate) ;
        TextView No = (TextView) convertView.findViewById(R.id.ReplyNo) ;

        MentorReplyItem listViewItem = listViewItemList.get(position);

        Category.setText(listViewItem.getCategory());
        Content.setText(listViewItem.getContent());
        Date.setText(listViewItem.getDate());
        No.setText(listViewItem.getNo());

        return convertView;
    }

    public void addItem(Boolean Accept, String Link, String No, String Category, String Content, String Date) {
        MentorReplyItem item = new MentorReplyItem(Accept, Link, No, Category, Content, Date);
        listViewItemList.add(item);
    }
}


class MentorRequestItem  {
    private String Id;
    private String Date;
    private String Score;

    MentorRequestItem(String Id, String Date, String Score) {
        setId(Id);
        setDate(Date);
        setScore(Score);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}

class MentorRequestAdapter extends BaseAdapter {
    private ArrayList<MentorRequestItem> listViewItemList = new ArrayList<>();

    // Constructor
    public MentorRequestAdapter() {

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
            convertView = inflater.inflate(R.layout.content_mentor_request, parent, false);
        }

        TextView UserId = (TextView) convertView.findViewById(R.id.RequestUserId) ;
        TextView Date = (TextView) convertView.findViewById(R.id.RequestDate) ;
        TextView Score = (TextView) convertView.findViewById(R.id.RequestScore) ;

        MentorRequestItem listViewItem = listViewItemList.get(position);

        UserId.setText(listViewItem.getId());
        Date.setText(listViewItem.getDate());
        Score.setText(listViewItem.getScore());

        ImageView AcceptImage = (ImageView)convertView.findViewById(R.id.RequestAcceptImage);
        AcceptImage.setTag(listViewItem.getId());

        AcceptImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = String.valueOf(view.getTag());
                ((MainActivity)MainActivity.context_main).w.acceptMentorPerm(Id, "1");

                Toast.makeText(null, "승인되었습니다", Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((MentorManagementActivity)MentorManagementActivity.context_main).reset();
            }
        });


        ImageView DenyImage = (ImageView)convertView.findViewById(R.id.RequestDenyImage);
        DenyImage.setTag(listViewItem.getId());

        DenyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = String.valueOf(view.getTag());
                ((MainActivity)MainActivity.context_main).w.acceptMentorPerm(Id, "0");
                Toast.makeText(null, "거절되었습니다.", Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((MentorManagementActivity)MentorManagementActivity.context_main).reset();
            }
        });

        return convertView;
    }

    public void addItem(String Id, String Date, String Score) {
        MentorRequestItem item = new MentorRequestItem(Id, Date, Score);
        listViewItemList.add(item);
    }
}