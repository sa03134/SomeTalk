package com.example.sometalk;

import android.os.AsyncTask;
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
    public UserLoginTask mAuthTask = null;
    public Map<String, String> UserCookie = null;
    public CrawlingBoardTask CBT = null;

    WebLogic(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() { return password; }
    public String getEmail() { return email; }

    public boolean attemptLogin() {
        if (mAuthTask != null) return true;

        boolean cancel = false;

        if(!cancel) {
            mAuthTask = new UserLoginTask(email, password);
            try {
                UserCookie = mAuthTask.execute((Void) null).get();
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

    public void getTwentyBoard(int page) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("get_twenty_board", Integer.toString(page));
    }

    public void getPost(String Link) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("getPost", Link);
    }

    public void setPost(String Board_Type, String Category, String Title, String Content) {
        CBT = new CrawlingBoardTask(UserCookie);
        CBT.execute("setPost", Board_Type, Category, Title, Content);
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
}