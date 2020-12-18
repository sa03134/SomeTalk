package com.example.sometalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WebLogic {
    private String password;
    private String email;
    public Map<String, String> UserCookie = null;
    public CrawlingBoardTask CBT = null;
    public CrawlingUserTask CUT = null;

    WebLogic(String email, String password) {
        this.email = email;
        this.password = password;
    }

    WebLogic() {}

    public String getPassword() { return password; }
    public String getEmail() { return email; }

    public boolean attemptLogin() {
        if (CUT != null) return true;

        boolean cancel = false;

        if(!cancel) {
//            mAuthTask = new UserLoginTask(email, password);
            CUT = new CrawlingUserTask();
            try {
                UserCookie = CUT.execute("login", email, password).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String arg = UserCookie.toString();
            if(arg.length() < 30) return false;

            CBT = new CrawlingBoardTask(UserCookie);
        }
        return true;
    }

    public void getPopularPage() {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_popular_board");
    }

    public void getTeenBoard(int page) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_teen_board", Integer.toString(page));
    }

    public void getSomeBoard(int page) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_some_board", Integer.toString(page));
    }

    public void getLoveBoard(int page) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_love_board", Integer.toString(page));
    }

    public void getTwentyBoard(int page) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_twenty_board", Integer.toString(page));
    }

    public void getPost(String Link) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("getPost", Link);
    }

    public void setPost(String Board_Type, String Title, String Content, String path) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setPost", Board_Type, Title, Content, path);
    }

    public void setComment(String Board_Type, String No, String Comment) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setComment", Board_Type, No, Comment);
    }

    public void deletePost(String Type, String No) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("deletePost", Type, No);
    }

    public void editPost(String Type, String No, String Title, String Content) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("editPost", Type, No, Title, Content);
    }

    public void deleteComment(String pKey) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("deleteComment", pKey);
    }

    public void deleteReply(String pKey) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("deleteReply", pKey);
    }

    public void setReply(String Type, String No, String Reply) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setReply", Type, No, Reply);
    }

    public void acceptReply(String pKey) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("acceptReply", pKey);
    }

    public void setUnlikePost(String Type, String No) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setUnlikePost", Type, No);
    }

    public void setLikePost(String Type, String No) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setLikePost", Type, No);
    }

    /////////////////////////////////////
    public void register(String id, String pw, String nick, String email) {
        CUT = new CrawlingUserTask();
        CUT.execute("register", id, pw, nick, email);
    }

    public void getProfile() {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("getProfile");
    }

    public void setProfile(String Permission, String Id, String Nickname, String Password, String Email) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("setProfile", Permission, Id, Nickname, Password, Email);
    }

    public void setPicture(String data) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("setPicture", data);
    }

    public void requestMentorPerm(String ans1, String ans2) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("requestMentorPerm", ans1, ans2);
    }

    public void acceptMentorPerm(String Id, String Request_Code) {
        // Request_Code == "1" : Accept
        // Request_Code == "0" : Deny
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("acceptMentorPerm", Id, Request_Code);
    }

    public void getMentorProfile() {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("getMentorProfile");
    }

    public void setAd(String Path, String Content) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("setAd", Path, Content);
    }

    /////////////////////////////////

    public void getManageBoardPage() {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("getManageBoardPage");
    }

    public void getManageUserPage() {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("getManageUserPage");
    }

    public void getProfileFromAdmin(String Id) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("getProfileFromAdmin", Id);
    }

    public void setProfileFromAdmin(String Nick, String Perm, String Password, String Email, String Id) {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("setProfileFromAdmin", Nick, Perm, Password, Email, Id);
    }

    public void getPostFromAdmin(String Link) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("getPostFromAdmin", Link);
    }

    public void setPostFromAdmin(String Link, String Title, String Content) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setPostFromAdmin", Link, Title, Content);
    }

    public void deletePostFromAdmin(String Link) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("deletePostFromAdmin", Link);
    }

    /////////////////////////////////////////////
    public void getMentorRanking() {
        CUT = new CrawlingUserTask(UserCookie);
        CUT.execute("getMentorRanking");
    }

    public void getDashboard() {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("getDashboard");
    }
}