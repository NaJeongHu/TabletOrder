package com.example.tabletorder;

public class auth {
    String user_id;
    String access_token;
    public auth(String id, String token){
        this.user_id =id;
        this.access_token=token;
    }

    // getter
    public String getUser_id() {
        return user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    // setter
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}