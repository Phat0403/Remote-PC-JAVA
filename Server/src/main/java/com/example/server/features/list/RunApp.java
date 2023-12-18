package com.example.server.features.list;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunApp {
    private boolean success = false;
    public boolean isSuccess(){
        return this.success;
    }
    public void run(String appName) throws IOException {
        Process process = null;
        String command = "cmd /c start " + appName;
        System.out.println(command);
        int exitcode=-1;
        try {
            process = Runtime.getRuntime().exec(command);
            exitcode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (exitcode==0){
            this.success = true;
        }
    }
}
