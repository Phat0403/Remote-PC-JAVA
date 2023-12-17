package com.example.server;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Server {
    private final String key = keyGenerator();
    static ArrayList<String> reqList = new ArrayList<String>();
    static ArrayList<String> mailList = new ArrayList<String>();
    static ArrayList<String> nameReqList = new ArrayList<String>();

    static ArrayList<String> numberList = new ArrayList<String>();
    static ArrayList<String> resList = new ArrayList<String>();
    @FXML
    private TableView<info> table_view;
    @FXML
    private TableColumn<info, String> stt;
    @FXML
    private TableColumn<info, String> email;
    @FXML
    private TableColumn<info, String> request;
    @FXML
    private TableColumn<info, String> response;
    private ObservableList<info> list;


    private String keyGenerator() {
        Random r = new Random();
        String s = "";
        for (int i = 0; i < 8; i++) {
            s += (char) (r.nextInt(25) + 65);
        }
        return s;
    }

    @FXML
    private Label timerLabel;
    @FXML
    private Label text_code;

    private int seconds;
    private int minute;

    public String getKey() {
        return key;
    }

    public void generate() {

        System.out.println("CODE: " + key);
    }

    public void run() {
        Server server = new Server();
        server.generate();
        getRequest gr = new getRequest(server.key);
        gr.start();
        sendResponse sr = new sendResponse(server.key);
        sr.start();
        Platform.runLater(() -> {
            text_code.setText(" CODE: " + server.key);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds++;
                if (seconds >= 60){
                    seconds = seconds % 60;
                    minute++;
                }
                timerLabel.setText(" Timer: "+minute+":" + seconds + " s");
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
        while (true) {
            System.out.println(Server.mailList.toString() + Server.reqList.toString() + Server.numberList.toString());
            gr.run();
            sr.run();
            list = FXCollections.observableArrayList();
            stt.setCellValueFactory(new PropertyValueFactory<info, String>("stt"));
            email.setCellValueFactory(new PropertyValueFactory<info, String>("email"));
            request.setCellValueFactory(new PropertyValueFactory<info, String>("req"));
            response.setCellValueFactory(new PropertyValueFactory<info, String>("res"));
            if (list != null) {
                loadDataFromFile();
                table_view.setItems(list);
            }
        }
    }
    public void loadDataFromFile() {
        for (int i = 0; i < Server.reqList.size(); i++) {
            info taskInfo = new info((i + 1) + "", Server.mailList.get(i), Server.nameReqList.get(i), Server.resList.get(i));
            list.add(taskInfo);
        }
    }
    public void handleExit(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
}