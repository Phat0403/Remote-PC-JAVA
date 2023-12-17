package com.example.server;

public class  info {
    private String stt;
    private String email;
    private String req;
    private String res;

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }




    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public info(String stt, String email, String req, String res) {
        this.stt = stt;
        this.email = email;
        this.req = req;
        this.res = res;
    }
}
