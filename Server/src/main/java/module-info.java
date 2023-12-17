module com.example.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.github.kwhat.jnativehook;
    requires java.mail;
    requires activation;
    requires org.jsoup;


    opens com.example.server to javafx.fxml;
    exports com.example.server;
}