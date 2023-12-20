package com.example.server.features.power;
import java.io.IOException;

public class SRS {
    public static void shutdown() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            Runtime.getRuntime().exec("shutdown -s -t 15");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restart() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            Runtime.getRuntime().exec("shutdown -r -t 15");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout(){
        try {
            String os = System.getProperty("os.name").toLowerCase();

            Runtime.getRuntime().exec("shutdown -l -t 15");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sleep(){
        try {
            String os = System.getProperty("os.name").toLowerCase();

            Runtime.getRuntime().exec("shutdown -h -t 15");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

