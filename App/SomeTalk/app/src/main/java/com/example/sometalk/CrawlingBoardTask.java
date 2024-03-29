package com.example.sometalk;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class CrawlingBoardTask extends AsyncTask<String, Void, Map<String, String>> {
    public Map<String, String> UserCookie;
    public boolean isUse = false;
    public CrawlingBoardItem CBI[];
    public int CBI_COUNT = 0;
    public CrawlingBoardItem RecentPost = null;
    public CommentListItem Comments[];
    public ReplyListItem Replys[];
    public boolean isReplyPerm = false;
    public boolean isAccept = false;

    public CrawlingManagementPostItem Manage_Posts[];
    public CrawlingManagementEditPostItem Manage_Post = null;

    public DashboardItem Dashboard = null;


    CrawlingBoardTask(Map<String, String> UserCookie) {
        this.UserCookie = UserCookie;
    }
    CrawlingBoardTask() {  }

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
                        CBI[i].setAccept(e.get(i).select("#Accept").text());
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
                        CBI[i].setAccept(e.get(i).select("#Accept").text());
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
                        CBI[i].setAccept(e.get(i).select("#Accept").text());
                    }
                } catch (IOException e) {

                }
                break;

            case "get_some_board" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/board?Type=3&Page=" + voids[1])
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
                        CBI[i].setAccept(e.get(i).select("#Accept").text());
                    }
                } catch (IOException e) {

                }
                break;

            case "get_love_board" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/board?Type=4&Page=" + voids[1])
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
                        CBI[i].setAccept(e.get(i).select("#Accept").text());
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
                    String Likes = e.select("#Likes").val();

                    RecentPost = new CrawlingBoardItem(Title, Author, Date);
                    RecentPost.setContent(Content);
                    RecentPost.setLike(Likes.split("_")[0]);
                    RecentPost.setUnLike(Likes.split("_")[1]);

                    Elements es = doc.select("#CommentContent");
                    Comments = new CommentListItem[es.size()];

                    for(int i = 0; i < es.size(); ++i) {
                        if(es.get(i).select("#pkey").attr("pkey") != null && es.get(i).select("#pkey").attr("pkey") != "") {
                            String pKey = es.get(i).select("#pkey").attr("pkey") + "_" + String.valueOf(i);
                            Comments[i] = new CommentListItem(es.get(i).select("#Author").text(), es.get(i).select("#Date").text(), es.get(i).select("#Content").text(), pKey);
                        }
                        else {
                            Comments[i] = new CommentListItem(es.get(i).select("#Author").text(), es.get(i).select("#Date").text(), es.get(i).select("#Content").text());
                        }
                    }

                    Elements Reply_elements = doc.select("#ReplyContent");
                    Replys = new ReplyListItem[Reply_elements.size()];

                    for(int i = 0; i < Reply_elements.size(); ++i) {
                        if(!isAccept) {
                            isAccept = Reply_elements.get(i).select("#acceptReply").attr("value").equals("1");
                        }

                        Replys[i] = new ReplyListItem(Reply_elements.get(i).select("#Author").text(),
                                                    Reply_elements.get(i).select("#Date").text(),
                                                    Reply_elements.get(i).select("#Content").text(),
                                                    String.valueOf(voids[1]),
                                                    Reply_elements.get(i).select("#acceptReply").attr("value").equals("1")
                        );
                    }

                    Element Element_isReplyPerm = doc.selectFirst("#isReplyPerm");

                    isReplyPerm = (Element_isReplyPerm != null) ? true : false;
                    if(isAccept) isReplyPerm = false;


                } catch (IOException e) {

                }
                break;

            case "setPost" :
                String encodeBytes = "";
                if(voids[4] != null) {
                    int size = (int) new File(voids[4]).length();
                    byte[] lpbuffer = new byte[size];


                    File f = new File(voids[4]);
                    FileInputStream fs1 = null;
                    encodeBytes = null;

                    try {
                        fs1 = new FileInputStream(f);
                        fs1.read(lpbuffer, 0, size);
                        Base64.Encoder encoder = Base64.getEncoder();
                        encodeBytes = encoder.encodeToString(lpbuffer);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                body =  "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Title\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Content\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Type\"\n" +
                        "\n" +
                        voids[1] + "\n";

                if(encodeBytes != "") {
                    body += "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"file\"; filename=\"test.jpg\"\n" +
                            "\n" +
                            encodeBytes + "\n" +
                            "\n";
                } else {
                    body += "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"file\"; filename=\"\"\n" +
                            "\n" +
                            "\n";
                }
                body += "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";

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

            case "deleteComment" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/DeleteComment")
                            .ignoreContentType(true)
                            .data("pkey", voids[1])
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "deleteReply" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "setReply" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/WriteReply")
                            .data(
                                    "Type", voids[1],
                                    "No", voids[2],
                                    "Content", voids[3]
                            )
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "acceptReply" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "setUnlikePost" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/UnLikePost")
                            .ignoreContentType(true)
                            .data("Type", voids[1],
                                    "No", voids[2]
                            )
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                }catch (IOException e) {

                }
                break;

            case "setLikePost" :
                try {
                        Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/LikePost")
                                .ignoreContentType(true)
                                .data("Type", voids[1],
                                        "No", voids[2]
                                )
                                .userAgent(userAgent)
                                .cookies(UserCookie)
                                .method(Connection.Method.GET)
                                .timeout(5000)
                                .execute();
                }catch (IOException e) {

                }
                break;

            case "getManageBoardPage" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Manage/board")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();


                    Document doc = res.parse();

                    Elements e = doc.select("#boardRow");

                    Manage_Posts = new CrawlingManagementPostItem[e.size()];

                    for(int i = 0; i < e.size(); ++i) {
                        Manage_Posts[i] = new CrawlingManagementPostItem(e.get(i).select("#Title > a").attr("href"),
                                                                         e.get(i).select("#No").text(),
                                                                         e.get(i).select("#Category").text(),
                                                                         e.get(i).select("#Author").text(),
                                                                         e.get(i).select("#Title").text(),
                                                                         e.get(i).select("#Date").text());
                    }

                } catch (IOException e) {

                }
                break;

            case "getPostFromAdmin" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();
                    Manage_Post = new CrawlingManagementEditPostItem(voids[1], doc.select("#Title").val(), doc.select("#Content").text());
                } catch (IOException e) {

                }
                break;

            case "setPostFromAdmin" :
                body =  "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Title\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Content\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Type\"\n" +
                        "\n" +
                        voids[1].split("=")[1].split("&")[0] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"No\"\n" +
                        "\n" +
                        voids[1].split("=")[2] + "\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528" + voids[1])
                            .ignoreContentType(true)
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .referrer("http://www.qerogram.kro.kr:41528/Manage/")
                            .requestBody(body)
                            .cookies(UserCookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "deletePostFromAdmin" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .referrer("http://www.qerogram.kro.kr:41528/Manage/")
                            .data("Type", voids[1].split("=")[1].split("&")[0],
                                    "No", voids[1].split("=")[2])
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "getDashboard" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Manage")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(UserCookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                    Document doc = res.parse();

                    Dashboard = new DashboardItem(doc.selectFirst("#countUser").text(),
                    doc.selectFirst("#countPost").text(),
                    doc.selectFirst("#countAccept").text());


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
