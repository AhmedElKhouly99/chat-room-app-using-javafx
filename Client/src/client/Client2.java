/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ahmed
 */
public class Client2 extends Application {
    
    
    BorderPane border;
    FlowPane fp;
    TextArea chatBox; 
    TextField typingBox; 
    Button sendBtn;
    Text lable; 
    Scene s;

    Socket sc;
    DataInputStream dIn;
    PrintStream dOut;

    @Override
    public void init() throws IOException{
        chatBox = new TextArea("Chat Messages: \n");
        lable = new Text("Enter Your Message");
        typingBox = new TextField();
        sendBtn = new Button("Send");
        border = new BorderPane();
        border.setCenter(chatBox);
        fp = new FlowPane(lable, typingBox, sendBtn);
        border.setBottom(fp);
        s = new Scene(border, 500, 500);

        try{
            sc = new Socket("127.0.0.1", 5005);
            dIn = new DataInputStream(sc.getInputStream());
            dOut = new PrintStream(sc.getOutputStream());
        } catch (IOException e) {
            sc.close();
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String reply = dIn.readLine();
                        chatBox.appendText(reply+"\n");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        });
        th.start();

    }

    @Override
    public void start(Stage primaryStage) {

        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                dOut.println(typingBox.getText());
                typingBox.setText("");
            }
        });
        
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(s);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((event) -> {            
            Platform.exit();
            System.exit(0);
        });
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
