package com.example.server;

import com.example.server.mail.receiveMail;


public class getRequest extends Thread{
    final String DEFAULT_MAIL = "projectmangmaytinh2004@gmail.com";
    final String DEFAULT_PASSWORD = "gorabwfzyfuqfkgy";
    receiveMail rm = new receiveMail(DEFAULT_MAIL,DEFAULT_PASSWORD);

    private String auth_code;


    public getRequest(String auth_code){
        this.auth_code = auth_code;
    }
    @Override
    public void run() {

        rm.receiveMail();
        if (!rm.getContent().split(" ",-1)[0].equals(auth_code)){
            return;
        }
        else {
            if (Server.reqList.isEmpty()){
                Server.reqList.add(rm.getText().strip());
                Server.mailList.add(rm.getFrom());
                Server.numberList.add(rm.getNumber());
                return;
            }
            while (!(Server.numberList.getLast().equals(rm.getNumber()) && Server.mailList.getLast().equals(rm.getFrom()) && Server.reqList.getLast().equals(rm.getText().strip()))) {
                Server.reqList.add(rm.getText().strip());
                Server.numberList.add(rm.getNumber());
                Server.mailList.add(rm.getFrom());
                rm.receiveMail();
            }
        }
    }
}