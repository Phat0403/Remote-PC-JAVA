// App.java
package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;


public class App extends Application {
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource( "login.fxml"));
        Parent root =  loader.load();

        primaryStage.setScene(new Scene(root, 355, 475));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("LOGIN");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        stage = primaryStage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}

