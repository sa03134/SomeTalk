package com.example.sometalk;

class BoardItem {
    private int BoardType ; // 게시판 번호
    private int Index ; // 게시글 번호
    private String Title ; // 게시글 제목
    private String Datetime ; // 게시글 작성 시간
    private String Author ;
    private String Content;
    private int Like;
    private int UnLike;


    public int getUnLike() {
        return UnLike;
    }

    public void setUnLike(int unLike) {
        UnLike = unLike;
    }

    public int getBoardType() {
        return BoardType;
    }

    public void setBoardType(int boardType) {
        BoardType = boardType;
    }

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }
}


public class PopularBoardItem extends BoardItem{

}

class TeenBoardItem extends BoardItem{

}

class TwentyBoardItem extends BoardItem{

}

class SomeBoardItem extends BoardItem{

}

class LoveBoardItem extends BoardItem{

}

class CommentListItem {
    private String Author;
    private String Date;
    private String Content;
    private String pkey;

    CommentListItem(String Author, String Date, String Content) {
        setAuthor(Author);
        setDate(Date);
        setContent(Content);
        pkey = "";
    }

    public CommentListItem(String Author, String Date, String Content, String pKey) {
        setAuthor(Author);
        setDate(Date);
        setContent(Content);
        setPkey(pKey);
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPkey() {  return pkey; }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }
}


class ReplyListItem extends CommentListItem {
    private boolean isAccepted = false;

    public ReplyListItem(String Author, String Date, String Content) {
        super(Author, Date, Content);
    }

    public ReplyListItem(String Author, String Date, String Content, String pKey) {
        super(Author, Date, Content, pKey);
    }

    public ReplyListItem(String Author, String Date, String Content, String pKey, boolean isAccepted) {
        super(Author, Date, Content, pKey);
        setAccepted(isAccepted);
    }

    public boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}