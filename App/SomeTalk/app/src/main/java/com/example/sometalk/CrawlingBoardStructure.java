package com.example.sometalk;

public class CrawlingBoardStructure {

}

class CrawlingBoardItem {
    private String Link; // 이동될 공간
    private int BoardType ; // 게시판 번호
    private int Index ; // 게시글 번호
    private String Title ; // 게시글 제목
    private String Datetime ; // 게시글 작성 시간
    private String Author ;
    private String Content;
    private int Like;
    private int UnLike;
    private String Accept;

    CrawlingBoardItem(String Title, String Author, String DateTime) {
        setTitle(Title);
        setAuthor(Author);
        setDatetime(DateTime);
    }

    public String getLink() { return Link; }

    public void setLink(String link) {
        Link = link;
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

    public int getUnLike() {
        return UnLike;
    }

    public void setUnLike(int unLike) {
        UnLike = unLike;
    }

    public String getAccept() {
        return Accept;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }
}