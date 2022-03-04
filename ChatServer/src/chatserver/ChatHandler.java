/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
// class player
/**
 *
 * @author ahmed
 */
public class ChatHandler extends Thread {
    DataInputStream dIn;
    PrintStream dOut;
    static Vector<ChatHandler> clients = new Vector<ChatHandler>();
    ////Vector

    public ChatHandler(Socket s) throws IOException {
        try {
            dIn = new DataInputStream(s.getInputStream());
            dOut = new PrintStream(s.getOutputStream());
            clients.add(this);
            start();
        }catch(IOException e){
            s.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String str = dIn.readLine();
                displayMsg(str);
            } catch (IOException e) {

            }
        }
    }

    public void displayMsg(String string) {
        for (ChatHandler ch : clients) {
            ch.dOut.println(string + " " + ch.getId());
        }
    }

}
