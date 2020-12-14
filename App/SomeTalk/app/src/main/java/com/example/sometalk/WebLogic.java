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
}