package com.example.sometalk;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class CrawlingBoardTask extends AsyncTask<String, Void, Map<String, String>> {
    public Map<String, String> UserCookie;
    public boolean isUse = false;
    public CrawlingBoardItem CBI[];
    public int CBI_COUNT = 0;
    public CrawlingBoardItem RecentPost = null;
    public CommentListItem Comments[];


    CrawlingBoardTask(Map<String, String> UserCookie) {
        this.UserCookie = UserCookie;
    }

    @Override
    protected Map<String, String> doInBackground(String... voids) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        String body = "";
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
                        CBI[i].setLink(e.get(i).select("#Title > a").attr("href"));
                    }
                } catch (IOException e) {

                }
                break;
            case "get_teen_board" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/board?Type=1&Page=" + voids[1])
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
                        CBI[i].setLink(e.get(i).select("#Title > a").attr("href"));
                    }
                } catch (IOException e) {

                }
                break;

            case "get_twenty_board" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/board?Type=2&Page=" + voids[1])
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
                        CBI[i].setLink(e.get(i).select("#Title > a").attr("href"));
                    }
                } catch (IOException e) {

                }
                break;

            case "getPost" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();


                    Document doc = res.parse();

                    Element e = doc.selectFirst("#PostContent");

                    String Author = e.select("#Author").text();
                    String Date = e.select("#Date").text();
                    String Content = e.select("#Content").text();
                    String Title = e.select("#Title").text();

                    RecentPost = new CrawlingBoardItem(Title, Author, Date);
                    RecentPost.setContent(Content);

                    Elements es = doc.select("#CommentContent");

                    Comments = new CommentListItem[es.size()];

                    for(int i = 0; i < es.size(); ++i) {
                        Comments[i] = new CommentListItem(es.get(i).select("#Author").text(), es.get(i).select("#Date").text(), es.get(i).select("#Content").text());
                    }
                } catch (IOException e) {

                }
                break;

            case "setPost" :
                body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"category\"\n" +
                        "\n" +
                        voids[2].substring(voids[2].length() - 1) + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Title\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Content\"\n" +
                        "\n" +
                        voids[4] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Type\"\n" +
                        "\n" +
                        voids[1] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"file\"; filename=\"\"\n" +
                        "Content-Type: application/octet-stream\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/WritePost")
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .ignoreContentType(true)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();



                } catch (IOException e) {

                }
                break;

            case "editPost" :
                body =  "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Title\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Content\"\n" +
                        "\n" +
                        voids[4] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Type\"\n" +
                        "\n" +
                        voids[1] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"No\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/EditPost")
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .ignoreContentType(true)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();



                } catch (IOException e) {

                }
                break;

            case "setComment" :
                body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Comment\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Type\"\n" +
                        "\n" +
                        voids[1] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"No\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/writeComment")
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .ignoreContentType(true)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();



                } catch (IOException e) {

                }
                break;

            case "deletePost" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/DeletePost")
                            .ignoreContentType(true)
                            .data("Type", voids[1],
                                    "No", voids[2]
                            )
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
                        CBI[i].setLink(e.get(i).select("#Title > a").attr("href"));
                    }
                } catch (IOException e) {

                }
                break;


        }

        return null;
    }

    @Override
    protected void onPostExecute(final Map<String, String> success) {
        isUse = false;
    }

    @Override
    protected void onCancelled() {

    }
}