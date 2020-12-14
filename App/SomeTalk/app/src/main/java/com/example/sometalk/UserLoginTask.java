package com.example.sometalk;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

public class UserLoginTask extends AsyncTask<Void, Void, Map<String, String>> {
    private final String mEmail;
    private final String mPassword;


    UserLoginTask(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected Map<String, String> doInBackground(Void... params) {
//    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.
        Map<String, String> t_cookie = new HashMap<String, String>();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";
        Document doc = null;

        try {
            Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Auth")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data("ID", mEmail,
                            "password", mPassword)
                    .ignoreContentType(true)
                    .userAgent(userAgent)
                    .timeout(5000)
                    .method(Connection.Method.POST)
                    .execute();
            t_cookie = res.cookies();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return doc.toString();
        return t_cookie;
    }

    @Override
    protected void onPostExecute(final Map<String, String> success) {
//    protected void onPostExecute(final String success) {
//        if (success.toString().length() > 30) {
//            Log.e("log", success.toString());
//        } else {
//            Log.e("log", "ErrorLog");
//        }
    }

    @Override
    protected void onCancelled() {

    }
}
