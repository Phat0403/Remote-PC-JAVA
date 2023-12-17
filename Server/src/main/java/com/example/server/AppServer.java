package com.example.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class AppServer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load SplashScreen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(new Scene(root));
        splashStage.show();

        // Đóng SplashScreen sau vài giây và mở ứng dụng chính
        new Thread(() -> {
            try {
                Thread.sleep(5000); // Đợi 5 giây
                Platform.runLater(() -> {
                    // Đóng SplashScreen
                    splashStage.close();

                    // Mở ứng dụng chính
                    try {
                        showMainStage(primaryStage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showMainStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("server.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 800, 500));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        Server server = loader.getController();
        Thread thread = new Thread(()->{
            server.run();
        });
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
