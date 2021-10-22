package com.company.servidor.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServidorA {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5556);

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("A new client is connected : " + socket);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");
                new ClientHandlerA(socket, dis, dos).start();
            } catch (Exception exception) {
                Objects.requireNonNull(socket).close();
                exception.printStackTrace();
            }
        }
    }
}
