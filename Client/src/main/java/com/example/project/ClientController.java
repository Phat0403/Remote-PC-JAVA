package com.example.project;

import com.example.project.mail.receiveMail;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.project.mail.sendMail;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ClientController {
    private String key;
    private String username;
    private String password;
    @FXML
    private Button buttonsd, buttonsl, buttonres, buttonlog,
            buttonscrshot, buttonzoom, buttonsaveasScr,
            buttonLogTurnOn, buttonLogTurnOff, buttonsaveasLog,
            buttonListPrc, buttonListApp,
            buttonBackAddress, buttonGetAddress, buttonResGet, buttonSaveGetFile,
            buttonKillPrc, buttonStartPrc;
    @FXML
    private Label responsePower, responeScr, label_warning;
    @FXML
    private ImageView scrshot;
    @FXML
    private TextArea LogText;
    @FXML
    private TextField text_startprc;
    @FXML
    private Label text_app, text_prc, text_address, text_keylog;
    private int number = 0;

    public void handleExitImageClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    public void initData(String username, String password, String code) {
        this.username = username;
        this.password = password;
        this.key = code;
        send = new sendMail(to, username, password, subject);
    }

    private String to = "projectmangmaytinh2004@gmail.com";

    private String subject = "";
    private sendMail send;


    public void OnButtonShutdown(ActionEvent event) throws IOException {
        System.out.println(username + password + key);
        if (!buttonsd.isDisabled()) {
            comfirmRequest("1", 1);
            System.out.println("ok");
        }
    }

    public void OnButtonSleep(ActionEvent event) throws IOException {
        if (!buttonsl.isDisabled()) {
            comfirmRequest("4", 4);
        }
    }

    public void OnButtonRestart(ActionEvent event) throws IOException {
        if (!buttonres.isDisable()) {
            comfirmRequest("2", 2);
        }
    }

    public void OnButtonLogout(ActionEvent event) throws IOException {
        if (!buttonlog.isDisable()) {
            comfirmRequest("3", 3);
        }
    }

    public void OnButtonScrshot(ActionEvent event) throws IOException {
        System.out.println("scrshot");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to screenshot?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            number += 1;
            Platform.runLater(() -> {
                buttonscrshot.setDisable(true);
                responeScr.setText("Please wait a few minutes!");
                responeScr.setTextFill(Color.GREEN);
            });

            Thread newThread = new Thread(() -> {
                send.setSubject(key + " " + number);
                send.sendContent("5");
                File oldfile = new File("./Client/src/main/resources/com/example/project/file/screen.png");
                oldfile.delete();

                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username, password);
                    receive.receiveMail();
                    System.out.println(receive.getContent());

                    if (receive.getContent().equals(key + " " + number)) {
                        File file = new File("./Client/src/main/resources/com/example/project/file/screen.png");
                        Image image = new Image(file.toURI().toString());
                        Platform.runLater(() -> {
                            scrshot.setImage(image);
                            buttonscrshot.setDisable(false);
                            buttonzoom.setDisable(false);
                            buttonsaveasScr.setDisable(false);
                            responeScr.setText("Successful!");
                            responeScr.setTextFill(Color.GREEN);
                        });
                        return;
                    }

                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        buttonscrshot.setDisable(false);
                        responeScr.setText("Failed, please send again!");
                        responeScr.setTextFill(Color.RED);
                    });
                }
            });
            newThread.start();
        } else {
            buttonscrshot.setDisable(false);
        }
    }

    public void OnButtonZoom(ActionEvent event) throws IOException {
        if (!buttonzoom.isDisable()) {
            Platform.runLater(() -> {
                File file = new File("./Client/src/main/resources/com/example/project/file/screen.png");
                Image imageshow = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(imageshow);
                imageView.setFitHeight(780);
                imageView.setFitWidth(1420);
                imageView.setPreserveRatio(true);
                Group root = new Group(imageView);
                Scene scene = new Scene(root, 1420, 780);
                Stage stage = new Stage();
                stage.setTitle("ScreenShot");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            });
        }
    }

    public void OnButtonSaveas(ActionEvent event) throws IOException {
        System.out.println("saveas");
        String sourceFilePath = "./Client/src/main/resources/com/example/project/file/screen.png";
        if (!buttonsaveasScr.isDisable()) {
            Platform.runLater(() -> {
                FileDialog fileDialog = new FileDialog(new Frame(), "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);

                String selectedFile = fileDialog.getFile();
                String selectedDirectory = fileDialog.getDirectory();

                // Check if the user canceled the file dialog
                if (selectedFile == null || selectedDirectory == null) {
                    System.out.println("Save operation canceled by the user.");
                    return;
                }

                // Construct the destination path
                Path destinationPath = Path.of(selectedDirectory, selectedFile);

                try {
                    // Copy the file to the chosen destination
                    Files.copy(Path.of(sourceFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File saved successfully to: " + destinationPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error saving file.");
                }
            });
        }
    }

    public void OnButtonLogTurnOn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to keylogger?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            number += 1;
            Platform.runLater(() -> {
                buttonLogTurnOn.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key + " " + number);
                send.sendContent("6");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    buttonLogTurnOff.setDisable(false);
                    text_keylog.setText("Start get keylog!");
                    text_keylog.setTextFill(Color.GREEN);
                });
            });
            newThread.start();
        } else {
            buttonscrshot.setDisable(false);
        }
    }

    public void OnButtonLogTurnOff(ActionEvent event) throws IOException {
        number += 1;
        if (!buttonLogTurnOff.isDisable()) {
            Platform.runLater(() -> {
                buttonLogTurnOff.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key + " " + number);
                send.sendContent("7");
                File oldfile = new File("./Client/src/main/resources/com/example/project/file/keylog.txt");
                oldfile.delete();
                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username, password);
                    receive.receiveMail();
                    System.out.println(receive.getContent());
                    if (receive.getContent().equals(key + " " + number)) {
                        File file = new File("./Client/src/main/resources/com/example/project/file/keylog.txt");
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            StringBuilder content = new StringBuilder();
                            String text;
                            while ((text = br.readLine()) != null) {
                                content.append(text).append("\n");
                            }
                            System.out.println("hop le");
                            Platform.runLater(() -> {
                                LogText.setText(String.valueOf(content));
                                buttonLogTurnOff.setDisable(false);
                                buttonsaveasLog.setDisable(false);
                                buttonLogTurnOn.setDisable(false);
                                text_keylog.setText("");
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        System.out.println("Khong hop le");
                        buttonLogTurnOn.setDisable(false);
                        buttonLogTurnOff.setDisable(true);
                        text_keylog.setText("Failed, send mail again!");
                        text_keylog.setTextFill(Color.RED);
                    });
                }
            });
            newThread.start();
        }
    }

    public void OnButtonSaveasLog(ActionEvent event) throws IOException {
        System.out.println("saveas");
        String sourceFilePath = "./Client/src/main/resources/com/example/project/file/keylog.txt";
        if (!buttonsaveasLog.isDisable()) {
            Platform.runLater(() -> {
                FileDialog fileDialog = new FileDialog(new Frame(), "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);

                // Get the selected file and directory
                String selectedFile = fileDialog.getFile();
                String selectedDirectory = fileDialog.getDirectory();

                // Check if the user canceled the file dialog
                if (selectedFile == null || selectedDirectory == null) {
                    System.out.println("Save operation canceled by the user.");
                    return;
                }

                // Construct the destination path
                Path destinationPath = Path.of(selectedDirectory, selectedFile);

                try {
                    // Copy the file to the chosen destination
                    Files.copy(Path.of(sourceFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File saved successfully to: " + destinationPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error saving file.");
                }
            });
        }
    }

    public void comfirmRequest(String pow, int index) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to " + pow + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            number += 1;
            Platform.runLater(() -> {
                buttonsd.setDisable(true);
                buttonsl.setDisable(true);
                buttonres.setDisable(true);
                buttonlog.setDisable(true);
                responsePower.setText("Please wait a few minutes!");
                responsePower.setTextFill(Color.GREEN);
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key + " " + number);
                send.sendContent(pow);
                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username, password);
                    receive.receiveMail();
                    if (receive.getContent().equals(key + " " + number)) {
                        if (receive.getText().strip().equals("Successful")) {
                            Platform.runLater(() -> {
                                buttonsd.setDisable(false);
                                buttonsl.setDisable(false);
                                buttonres.setDisable(false);
                                buttonlog.setDisable(false);
                                responsePower.setText("Successful!");
                                responsePower.setTextFill(Color.GREEN);
                            });
                        }
                        return;
                    }
                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        buttonsd.setDisable(false);
                        buttonsl.setDisable(false);
                        buttonres.setDisable(false);
                        buttonlog.setDisable(false);
                        responsePower.setText("Failed, please sendmail again!");
                        responsePower.setTextFill(Color.RED);
                    });
                }
            });
            newThread.start();
        } else {
            buttonsd.setDisable(false);
            buttonsl.setDisable(false);
            buttonres.setDisable(false);
            buttonlog.setDisable(false);
            responsePower.setText("");
        }
    }

    //Manager process
    @FXML
    private TableView<TaskInfo.Process> prc_Table;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_ProcessName;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_PID;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_SessionName;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_Session;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_MemUsage;
    private ObservableList<TaskInfo.Process> ProcessList;

    public void OnListPrc(ActionEvent event) {
        number += 1;
        Platform.runLater(() -> {
            buttonListPrc.setDisable(true);
            text_prc.setText("Please wait for a few seconds!");
            text_prc.setTextFill(Color.GREEN);
        });
        Thread thread = new Thread(() -> {
            send.setSubject(key + " " + number);
            send.sendContent("9");

            File oldfile = new File("./Client/src/main/resources/com/example/project/file/listPrc.txt");
            oldfile.delete();
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds

            boolean correctResponseReceived = false;
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                System.out.println(receive.getContent());
                if (receive.getContent().equals(key + " " + number)) {
                    Platform.runLater(() -> {
                        System.out.println("ok");
                        ProcessList = FXCollections.observableArrayList();
                        prc_ProcessName.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_ProcessName"));
                        prc_PID.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_PID"));
                        prc_SessionName.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_SessionName"));
                        prc_Session.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_Session"));
                        prc_MemUsage.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_MemUsage"));
                        loadDataFromFile();
                        prc_Table.setItems(ProcessList);
                        buttonListPrc.setDisable(false);
                        text_prc.setText("");
                    });
                    return;
                }
                try {
                    Thread.sleep(5000); // Wait for 1 second before checking again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived) {
                Platform.runLater(() -> {
                    buttonListPrc.setDisable(false);
                    text_prc.setText("Failed, send mail again!");
                    text_prc.setTextFill(Color.RED);
                });

            }
        });
        thread.start();
    }

    public void loadDataFromFile() {
        File file = new File("./Client/src/main/resources/com/example/project/file/listPrc.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] data = line.split("\\|");
                if (data.length >= 5) {
                    TaskInfo.Process taskInfo = new TaskInfo.Process(data[0], data[1], data[2], data[3], data[4]);
                    ProcessList.add(taskInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnButtonKillPrc() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to " + "kill this process" + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            number += 1;
            Platform.runLater(() -> {
                buttonKillPrc.setDisable(true);
                buttonListPrc.setDisable(true);
                text_prc.setText("Please wait a few minutes!");
                text_prc.setTextFill(Color.GREEN);
            });

            TaskInfo.Process selectedPID = prc_Table.getSelectionModel().getSelectedItem();
            Thread newThread = new Thread(() -> {
                String pid;
                if (selectedPID != null) {
                    pid = selectedPID.getPrc_PID();
                    send.setSubject(key + " " + number);
                    send.sendContent("11" + " " + pid);
                    Platform.runLater(() -> {
                        text_prc.setText("Please, wait for a few sencond");
                    });
                } else {
                    text_prc.setText("Please, choose a process to kill");
                    return;
                }
                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username, password);
                    receive.receiveMail();
                    if (receive.getContent().equals(key + " " + number)) {
                        if (receive.getText().strip().equals("Successfull")) {
                            Platform.runLater(() -> {
                                buttonKillPrc.setDisable(false);
                                buttonListPrc.setDisable(false);
                                text_prc.setText("Kill process successfull!");
                                TaskInfo.Process foundProcess = ProcessList.stream()
                                        .filter(process -> pid.equals(process.getPrc_PID()))
                                        .findFirst()
                                        .orElse(null);

                                if (foundProcess != null) {
                                    ProcessList.remove(foundProcess);
                                    prc_Table.setItems(ProcessList);
                                }
                            });
                        } else {
                            Platform.runLater(() -> {
                                buttonKillPrc.setDisable(false);
                                buttonListPrc.setDisable(false);
                                text_prc.setText("Kill process UnSuccessfull!");
                            });
                        }
                        return;
                    }
                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        buttonKillPrc.setDisable(false);
                        buttonListPrc.setDisable(false);
                        text_prc.setText("Kill process UnSuccessfull!");
                    });
                }
            });
            newThread.start();
        } else {
            buttonKillPrc.setDisable(false);
            buttonListPrc.setDisable(false);
        }
    }

    public void OnButtonStartPrc() {
        number += 1;
        Platform.runLater(() -> {
            buttonStartPrc.setDisable(true);
            text_prc.setText("Please wait a few minutes!");
            text_prc.setTextFill(Color.GREEN);
        });
        Thread newThread = new Thread(() -> {
            send.setSubject(key + " " + number);
            String process = text_startprc.getText();
            if (process != null) {
                send.sendContent("12 " + process);
            }
            else {
                return;
            }
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds
            boolean correctResponseReceived = false;
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                if (receive.getContent().equals(key + " " + number)) {
                    if (receive.getText().strip().equals("successful")) {
                        Platform.runLater(() -> {
                            buttonStartPrc.setDisable(false);
                            text_prc.setText("Run " + process + " successfully!");
                            text_prc.setTextFill(Color.GREEN);
                        });
                    } else {
                        buttonStartPrc.setDisable(false);
                        text_prc.setText("Failed, " + process + " is not exist or started!");
                        text_prc.setTextFill(Color.RED);
                    }
                    return;
                }
                try {
                    Thread.sleep(5000); // Wait for 1 second before checking again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived) {
                Platform.runLater(() -> {
                    buttonStartPrc.setDisable(false);
                    text_prc.setText("Failed, please sendmail again!");
                    text_prc.setTextFill(Color.RED);
                });
            }
        });
        newThread.start();
    }

    //Application
    @FXML
    private TableView<TaskInfo.Application> app_Table;
    @FXML
    private TableColumn<TaskInfo.Application, String> app_Name;
    private ObservableList<TaskInfo.Application> Applist;

    public void OnButtonListApp(ActionEvent event) {
        number += 1;
        Platform.runLater(() -> {
            buttonListApp.setDisable(true);
            text_app.setText("Please wait for a few seconds!");
            text_app.setTextFill(Color.GREEN);
        });
        Thread thread = new Thread(() -> {
            send.setSubject(key + " " + number);
            send.sendContent("10");
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds

            boolean correctResponseReceived = false;
            File oldfile = new File("./Client/src/main/resources/com/example/project/file/listApp.txt");
            oldfile.delete();
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                System.out.println(receive.getContent());
                if (receive.getContent().equals(key + " " + number)) {
                    Platform.runLater(() -> {
                        System.out.println("ok");
                        Applist = FXCollections.observableArrayList();
                        app_Name.setCellValueFactory(new PropertyValueFactory<TaskInfo.Application, String>("app_Name"));
                        loadAppFromFile();
                        app_Table.setItems(Applist);
                        buttonListApp.setDisable(false);
                        text_app.setText("");
                    });
                    return;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived) {
                Platform.runLater(() -> {
                    buttonListApp.setDisable(false);
                    text_app.setText("Failed, send mail again!");
                    text_app.setTextFill(Color.RED);
                });
            }
        });
        thread.start();
    }

    public void loadAppFromFile() {
        File file = new File("./Client/src/main/resources/com/example/project/file/listApp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                TaskInfo.Application taskinfo = new TaskInfo.Application(line);
                Applist.add(taskinfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get file
    @FXML
    private TableView<TaskInfo.address> table_address;
    @FXML
    private TableColumn<TaskInfo.address, String> name_address;
    private ObservableList<TaskInfo.address> Addresslist, AddresslistOld;
    private String path = " ", old_path = " ";
    private String nameFile;

    public void OnButtonBackAddress(ActionEvent event) {
        if (!buttonBackAddress.isDisable()) {
            path = old_path;
            Platform.runLater(() -> {
                if (AddresslistOld != null) {
                    Addresslist = FXCollections.observableArrayList(AddresslistOld);
                } else {
                    Addresslist.clear();
                }
                name_address.setCellValueFactory(new PropertyValueFactory<TaskInfo.address, String>("name_address"));
                table_address.setItems(Addresslist);
                buttonBackAddress.setDisable(true);
                text_address.setText("");
            });
        }
    }

    public void OnButtonGetAddress(ActionEvent event) {
        number += 1;
        Platform.runLater(() -> {
            buttonGetAddress.setDisable(true);
            text_address.setText("Please wait for a few seconds!");
            text_address.setTextFill(Color.GREEN);
        });
        Thread newthread = new Thread(() -> {
            send.setSubject(key + " " + number);

            TaskInfo.address selectedItem = table_address.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                if (path.equals(" ")) {
                    old_path = path;
                    send.sendContent("8");
                } else {
                    Platform.runLater(() -> {
                        label_warning.setText("Please, choose address!");
                        buttonGetAddress.setDisable(false);
                    });
                    return;
                }
            } else {
                old_path = path;
                path = " " + selectedItem.getName_address();
                send.sendContent("8" + path);
                System.out.println("Selected text: " + selectedItem.getName_address());
                Platform.runLater(() -> {
                    label_warning.setText("");
                });
            }
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds

            boolean correctResponseReceived = false;
            File oldfile = new File("./Client/src/main/resources/com/example/project/file/filelists.txt");
            oldfile.delete();
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                System.out.println(receive.getContent());
                if (receive.getContent().equals(key + " " + number + " ok")) {
                    path = " ";
                    Addresslist.clear();
                    AddresslistOld.clear();
                    Platform.runLater(() -> {
                        buttonGetAddress.setDisable(false);
                        buttonBackAddress.setDisable(true);
                        buttonSaveGetFile.setDisable(false);
                        text_address.setText("");
                        name_address.setCellValueFactory(new PropertyValueFactory<TaskInfo.address, String>("name_address"));
                        table_address.setItems(Addresslist);
                        text_address.setText("Send file " + receive.getNameFile());
                    });
                    System.out.println("Nhan file roi");
                    nameFile = receive.getNameFile();
                    // Luu file luon
                    return;
                }
                if (receive.getContent().equals(key + " " + number)) {
                    Platform.runLater(() -> {
                        System.out.println("ok");
                        if (Addresslist != null) {
                            AddresslistOld = FXCollections.observableArrayList(Addresslist);
                        }
                        Addresslist = FXCollections.observableArrayList();
                        name_address.setCellValueFactory(new PropertyValueFactory<TaskInfo.address, String>("name_address"));
                        loadAddressFromFile();
                        table_address.setItems(Addresslist);
                        buttonGetAddress.setDisable(false);
                        buttonBackAddress.setDisable(false);
                        buttonSaveGetFile.setDisable(true);
                        text_address.setText("Please select file or folder!");
                        text_address.setTextFill(Color.GREEN);
                    });
                    return;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived) {
                Platform.runLater(() -> {
                    buttonGetAddress.setDisable(false);
                    text_address.setText("Failed, send mail again!");
                    text_address.setTextFill(Color.RED);
                });
            }
        });
        newthread.start();
    }

    public void loadAddressFromFile() {
        File file = new File("./Client/src/main/resources/com/example/project/file/filelists.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                TaskInfo.address taskinfo = new TaskInfo.address(line);
                Addresslist.add(taskinfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnButtonResGet(ActionEvent event) {
        Addresslist.clear();
        if (AddresslistOld != null) {
            AddresslistOld.clear();
        }
        path = " ";
        Platform.runLater(() -> {
            buttonBackAddress.setDisable(true);
            buttonSaveGetFile.setDisable(true);
            name_address.setCellValueFactory(new PropertyValueFactory<TaskInfo.address, String>("name_address"));
            table_address.setItems(Addresslist);
            text_address.setText("");

        });
    }

    public void OnButtonSaveGetFile(ActionEvent event) {
        System.out.println("saveas");
        String sourceFilePath = "./Client/src/main/resources/com/example/project/file/" + nameFile;
        if (!buttonSaveGetFile.isDisable()) {
            Platform.runLater(() -> {
                FileDialog fileDialog = new FileDialog(new Frame(), "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);

                // Get the selected file and directory
                String selectedFile = fileDialog.getFile();
                String selectedDirectory = fileDialog.getDirectory();

                // Check if the user canceled the file dialog
                if (selectedFile == null || selectedDirectory == null) {
                    System.out.println("Save operation canceled by the user.");
                    return;
                }

                // Construct the destination path
                Path destinationPath = Path.of(selectedDirectory, selectedFile);

                try {
                    // Copy the file to the chosen destination
                    Files.copy(Path.of(sourceFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File saved successfully to: " + destinationPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error saving file.");
                }
            });
        }
    }


}

