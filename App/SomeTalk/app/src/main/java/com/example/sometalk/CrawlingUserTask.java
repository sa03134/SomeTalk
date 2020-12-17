package com.example.sometalk;

import android.os.AsyncTask;
import android.transition.Transition;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class CrawlingUserTask extends AsyncTask<String, Void, Map<String, String>> {
    public boolean isUse = false;
    public CrawlingUserStructure User = null;
    Map<String, String> t_cookie = new HashMap<String, String>();
    public UserPostItem Posts[];
    public UserCommentItem Comments[];
    public String profile_img;

    public CrawlingMentorStructure Mentor;
    public MentorReplyItem Replys[];
    public MentorRequestItem Requests[];

    public CrawlingManagementUserItem Manage_Users[];

    public MentorRankItem Mentors[];


    CrawlingUserTask() {

    }

    CrawlingUserTask(Map<String, String> UserCookie) {
        this.t_cookie = UserCookie;
    }

    @Override
    protected Map<String, String> doInBackground(String... voids) {
        // TODO: attempt authentication against a network service.
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        String body = "";
        int size;
        byte[] lpbuffer;

        File f;
        FileInputStream fs1;
        String encodeBytes;

        isUse = true;

        switch (voids[0]) {
            case "login" :
                Document doc = null;
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Auth")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .data("ID", voids[1],
                                    "password", voids[2])
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
                break;

            case "register" :
                body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"ID\"\n" +
                        "\n" +
                        voids[1] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"password\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Nickname\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Email\"\n" +
                        "\n" +
                        voids[4] + "\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/reg_Auth")
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .ignoreContentType(true)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "getProfile" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Profile")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                    doc = res.parse();

                    Element Account = doc.selectFirst("#Account");

                    Element Picture = doc.selectFirst("#userprofile");

                    if(Picture != null) {
                        profile_img = "http://www.qerogram.kro.kr:41528/" + Picture.attr("src");
                    }

                    User = new CrawlingUserStructure(Account.select("#Perm").val(),
                                                     Account.select("#Id").val(),
                                                     Account.select("#Password").val(),
                                                     Account.select("#Nickname").val(),
                                                     Account.select("#Email").val());



                    Elements e_Posts = doc.select("#PostContent");
                    Posts = new UserPostItem[e_Posts.size()];

                    for(int i = 0; i < e_Posts.size(); ++i) {
                        Posts[i] = new UserPostItem(e_Posts.get(i).select("#Title").attr("href"),
                                                    e_Posts.get(i).select("#No").text(),
                                                    e_Posts.get(i).select("#Category").text(),
                                                    e_Posts.get(i).select("#Title").text(),
                                                    e_Posts.get(i).select("#Date").text());
                    }

                    Elements e_Comments = doc.select("#CommentContent");
                    Comments = new UserCommentItem[e_Comments.size()];

                    for(int i = 0; i < e_Comments.size(); ++i) {
                        Comments[i] = new UserCommentItem(
                                e_Comments.get(i).select("#No").text(),
                                e_Comments.get(i).select("#Category").text(),
                                e_Comments.get(i).select("#Content").text(),
                                e_Comments.get(i).select("#Date").text());
                    }

                } catch (IOException e) {

                }
                break;

            case "setProfile" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Edit_Profile")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .data(
                                    "Perm", voids[1],
                                    "Id", voids[2],
                                    "Nickname", voids[3],
                                    "Password", voids[4],
                                    "Email", voids[5]
                            )
                            .cookies(t_cookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "setPicture" :
                size = (int)new File(voids[1]).length();
                lpbuffer = new byte[size];

                f = new File(voids[1]);
                fs1 = null;
                encodeBytes = null;

                try {
                    fs1 = new FileInputStream(f);
                    fs1.read(lpbuffer, 0, size);
                    Base64.Encoder encoder = Base64.getEncoder();
                    encodeBytes = encoder.encodeToString(lpbuffer);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


                body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"file\"; filename=\"test.jpg\"\n" +
                        "\n" +
                        encodeBytes + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";

                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/UploadProfile")
                            .ignoreContentType(true)
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .cookies(t_cookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "requestMentorPerm" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Mentor/requestMentorPerm")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .data("ans1", voids[1],
                                    "ans2", voids[2])
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();
                } catch (IOException e) {

                }
                break;

            case "getMentorProfile" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Mentor/Manage")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                    doc = res.parse();

                    Element Picture = doc.selectFirst("#userprofile");
                    Mentor = new CrawlingMentorStructure(doc.selectFirst("#username").text(), doc.selectFirst("#userpoint").val());


                    if(Picture != null) {
                        profile_img = "http://www.qerogram.kro.kr:41528/" + Picture.attr("src");
                    }
                    Log.e("A", "A");

                    Elements e_Replys = doc.select("#PostContent");
                    Replys = new MentorReplyItem[e_Replys.size()];

                    for(int i = 0; i < e_Replys.size(); ++i) {
                        Replys[i] = new MentorReplyItem(e_Replys.get(i).select("#Accept") != null,
                                e_Replys.get(i).select("#Content").attr("href"),
                                e_Replys.get(i).select("#No").text(),
                                e_Replys.get(i).select("#Category").text(),
                                e_Replys.get(i).select("#Content").text(),
                                e_Replys.get(i).select("#Date").text());
                    }

                    Elements e_Requests = doc.select("#RequestContent");
                    Requests = new MentorRequestItem[e_Requests.size()];

                    for(int i = 0; i < e_Requests.size(); ++i) {
                        Requests[i] = new MentorRequestItem(e_Requests.get(i).select("#RequestId").text(),
                                                            e_Requests.get(i).select("#RequestDate").text(),
                                                            e_Requests.get(i).select("#RequestAns1").text(),
                                                            e_Requests.get(i).select("#RequestAns2").text()
                                );
                    }


//                    Elements e_Comments = doc.select("#CommentContent");
//                    Comments = new UserCommentItem[e_Comments.size()];
//
//                    for(int i = 0; i < e_Comments.size(); ++i) {
//                        Comments[i] = new UserCommentItem(
//                                e_Comments.get(i).select("#No").text(),
//                                e_Comments.get(i).select("#Category").text(),
//                                e_Comments.get(i).select("#Content").text(),
//                                e_Comments.get(i).select("#Date").text());
//                    }

                } catch (IOException e) {

                }
                break;

            case "acceptMentorPerm" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Mentor/acceptMentorPerm")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .data(
                                    "id", voids[1],
                                    "req", voids[2]
                            )
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;


            case "setAd" :

                if(voids[1] != null) {
                    size = (int) new File(voids[1]).length();
                    lpbuffer = new byte[size];

                    f = new File(voids[1]);
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


                    body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"Content\"\n" +
                            "\n" +
                            voids[2] + "\n" +
                            "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"file\"; filename=\"test.jpg\"\n" +
                            "\n" +
                            encodeBytes + "\n" +
                            "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                }
                else {
                    body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"Content\"\n" +
                            "\n" +
                            voids[2] + "\n" +
                            "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                            "Content-Disposition: form-data; name=\"file\"; filename=\"\"\n" +
                            "\n" +
                            "\n" +
                            "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                }

                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Mentor/WriteAd")
                            .ignoreContentType(true)
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                            .userAgent(userAgent)
                            .requestBody(body)
                            .cookies(t_cookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "getManageUserPage" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Manage/user")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();


                    doc = res.parse();

                    Elements e = doc.select("#userContent");

                    Manage_Users = new CrawlingManagementUserItem[e.size()];

                    for(int i = 0; i < e.size(); ++i) {
                        Manage_Users[i] = new CrawlingManagementUserItem(
                                e.get(i).select("#Perm").text(),
                                e.get(i).select("#Id").text(),
                                e.get(i).select("#Nick").text(),
                                e.get(i).select("#Email").text());
                    }

                } catch (IOException e) {

                }
                break;

            case "getProfileFromAdmin" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Manage/user/profile?id=" + voids[1])
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();

                    doc = res.parse();

                    Element Account = doc.selectFirst("#Account");

                    User = new CrawlingUserStructure(Account.select("#Perm").val(),
                            Account.select("#Id").val(),
                            Account.select("#Password").val(),
                            Account.select("#Nickname").val(),
                            Account.select("#Email").val());

                } catch (IOException e) {

                }
                break;

            case "setProfileFromAdmin" :
                body = "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Id\"\n" +
                        "\n" +
                        voids[5] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Perm\"\n" +
                        "\n" +
                        voids[2] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Password\"\n" +
                        "\n" +
                        voids[3] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Nickname\"\n" +
                        "\n" +
                        voids[1] + "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V\n" +
                        "Content-Disposition: form-data; name=\"Email\"\n" +
                        "\n" +
                        voids[4] + "\n" +
                        "\n" +
                        "\n" +
                        "------WebKitFormBoundary3Fc9KrOBytBNQJ6V--\n";
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Edit_Profile")
                            .ignoreContentType(true)
                            .referrer("http://www.qerogram.kro.kr:41528/Manage/user")
                            .userAgent(userAgent)
                            .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary3Fc9KrOBytBNQJ6V")
                            .requestBody(body)
                            .cookies(t_cookie)
                            .method(Connection.Method.POST)
                            .timeout(5000)
                            .execute();

                } catch (IOException e) {

                }
                break;

            case "getMentorRanking" :
                try {
                    Connection.Response res = Jsoup.connect("http://www.qerogram.kro.kr:41528/Mentor/Rank")
                            .ignoreContentType(true)
                            .userAgent(userAgent)
                            .cookies(t_cookie)
                            .method(Connection.Method.GET)
                            .timeout(5000)
                            .execute();


                    doc = res.parse();

                    Elements e = doc.select("#MentorContent");

                    Mentors = new MentorRankItem[e.size()];

                    for(int i = 0; i < e.size(); ++i) {
                        Mentors[i] = new MentorRankItem(
                                e.get(i).select("#No").text(),
                                e.get(i).select("#Author").text(),
                                e.get(i).select("#Reply").text(),
                                e.get(i).select("#Accept").text());
                    }

                } catch (IOException e) {

                }
                break;

        }

        return t_cookie;
    }

        @Override
        protected void onPostExecute(final Map<String, String> success) {
            isUse = false;
        }

        @Override
        protected void onCancelled() {

        }
}
