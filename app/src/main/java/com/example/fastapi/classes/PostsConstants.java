package com.example.fastapi.classes;

public class PostsConstants {

//    192.168.0.136
    private static String ROOT_URL = "http://192.168.0.136:8000/";
//    private static String ROOT_URL = "http://127.0.0.1:8000/";
//    private static String ROOT_URL = "http://10.0.2.2:8000/";
//    private static String ROOT_URL = "http://localhost:8000/";
    private boolean published;
    private String title;
    private String content;
    private String created_at;
    private String count;

//    public Constants() {
//        ROOT_URL += "Api.php?apicall=images";
//    }


    public PostsConstants(boolean published, String title, String content, String created_at) {
        this.published = published;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

//    public PostsConstants(){};

    public String getCount() {
        return count;
    }

    public static String getRootUrl() {
        return ROOT_URL;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public boolean getPublished() {
        return published;
    }




    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
