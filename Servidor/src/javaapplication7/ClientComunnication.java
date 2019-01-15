/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

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
 * @author AlumnoTarde
 */
public class ClientComunnication extends Thread {

    Socket socket;

    public ClientComunnication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        serverClient(socket);

    }

    private static void serverClient(Socket socket) {
        BufferedReader br = null;
        try {
            InputStream is = socket.getInputStream(); //Declaramos la recepción de un input
            InputStreamReader isr = new InputStreamReader(is); //Lo leemos con esta clase
            br = new BufferedReader(isr); //Lo envolvemos para leerlo todo junto
            //Leemos lo que recibe del cliente
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            Scanner sc = new Scanner(System.in);
            String line;

            do {
                line = br.readLine();
                System.out.println("Client said: " + line);

                String[] objects = {"R", "P", "S"};
                int length = objects.length;
                int rand = (int) (Math.random() * length);
                String randomletter = objects[rand];
                System.out.print(objects[rand]);
                System.out.print(" ");
                System.out.println("El servidor ha seleccionado " + randomletter);

                if (line.toString().equalsIgnoreCase("P") && randomletter == "S") {
                    line = "El servidor ha seleccionado " + randomletter + " Has perdido";
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                } else {
                    if (line.toString().equalsIgnoreCase("R") && randomletter == "P") {
                        line = "El servidor ha seleccionado " + randomletter + " Has perdido";
                        bw.write(line);
                        bw.newLine();
                        bw.flush();
                    } else {
                        if (line.toString().equalsIgnoreCase("S") && randomletter == "R") {
                            line = "El servidor ha seleccionado " + randomletter + " Has perdido";
                            bw.write(line);
                            bw.newLine();
                            bw.flush();
                        }else{
                            line = "El servidor ha seleccionado " + randomletter + " Has ganado";
                            bw.write(line);
                            bw.newLine();
                            bw.flush();
                        }
                    }
                }
                bw.write(line);
                bw.newLine();
                bw.flush();
            } while (line != "FIN");

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
