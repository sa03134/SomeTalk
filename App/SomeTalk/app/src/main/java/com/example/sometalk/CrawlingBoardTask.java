package com.example.sometalk;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class CrawlingBoardTask extends AsyncTask<String, Void, Map<String, String>> {
    public Map<String, String> UserCookie;
    public boolean isUse = false;
    public CrawlingBoardItem CBI[];
    public int CBI_COUNT = 0;


    CrawlingBoardTask(Map<String, String> UserCookie) {
        this.UserCookie = UserCookie;
    }

    @Override
    protected Map<String, String> doInBackground(String... voids) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        isUse = true;
        switch (voids[0]) {
            case "get_popular_board":
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();


                    Document doc = res.parse();

                    Elements e = doc.select("#boardRow");

                    CBI = new CrawlingBoardItem[e.size()];
                    CBI_COUNT = e.size();

                    for(int i = 0; i < e.size(); ++i) {
                        CBI[i] = new CrawlingBoardItem(e.get(i).select("#Title").text(), e.get(i).select("#Author").text(), e.get(i).select("#Date").text());
                    }

                    isUse = false;

                } catch (IOException e) {

                }
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Map<String, String> success) {
//        isUse = false;
    }

    @Override
    protected void onCancelled() {

    }
}