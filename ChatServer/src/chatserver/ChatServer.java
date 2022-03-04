/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatserver;

import java.io.IOException;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ahmed
 */
public class ChatServer {

    ServerSocket ss;

    public ChatServer() throws IOException {
        try {
            ss = new ServerSocket(5005);
            while (true) {
                Socket s = ss.accept();
                new ChatHandler(s);
            }
        } catch (IOException e) {
            ss.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new ChatServer();
    }

}
