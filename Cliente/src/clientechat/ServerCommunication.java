/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientechat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class ServerCommunication extends Thread {

    Socket socket;

    public ServerCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        communicateWithServer(socket);
    }

    private static void communicateWithServer(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            InputStream is = socket.getInputStream(); //Declaramos la recepción de un input
            InputStreamReader isr = new InputStreamReader(is); //Lo leemos con esta clase
            BufferedReader br = new BufferedReader(isr); //Lo envolvemos para leerlo todo junto
            Scanner sr = new Scanner(System.in);
            String line, LineFromServer;
            do {
                System.out.println("Select(R)ock, (P)aper or(S)cissors");
                line = sr.nextLine();
                if (line.toString().equalsIgnoreCase("R") || line.toString().equalsIgnoreCase("P") || line.toString().equalsIgnoreCase("S")) {
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                    LineFromServer = br.readLine();
                    System.out.println("Server said " + LineFromServer);
                } else {
                    System.out.println("Please, select a valid option");
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            } while (line != "FIN");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
