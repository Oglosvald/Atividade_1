package com.company.servidor.servidorb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorB {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5557);

        while (true) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
                System.out.println("A new client is connected : " + socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");
                new ClientHandlerB(socket, dataInputStream, outputStream).start();
            } catch (Exception exception) {
                socket.close();
                exception.printStackTrace();
            }
        }
    }
}
