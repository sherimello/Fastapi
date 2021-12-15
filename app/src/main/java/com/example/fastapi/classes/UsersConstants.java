package com.example.fastapi.classes;

public class UsersConstants {

    private static String ROOT_URL = "http://192.168.0.136:8000/";
//    private static String ROOT_URL = "http://10.0.2.2:8000/";
    private String email, password;

    public UsersConstants(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static String getRootUrl() {
        return ROOT_URL;
    }

    public String getemail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
