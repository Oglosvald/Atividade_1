package com.company.servidor.servidorb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandlerB extends Thread {

    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;

    public ClientHandlerB(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {
                received = dataInputStream.readUTF();
                if (received.equals("Exit")) {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                String[] expression = received.split(" ");

                double op1 = Double.parseDouble(expression[0]);
                char operation = expression[1].charAt(0);
                double op2 = 0;
                if (expression.length > 2) {
                    op2 = Double.parseDouble(expression[2]);
                }
                System.out.println(operation);
                switch (operation) {
                    case '^':
                        double result = Math.pow(op1, op2);
                        toreturn = String.valueOf(result);
                        dataOutputStream.writeUTF(toreturn);
                        break;
                    case 'r':
                        toreturn = String.valueOf(Math.sqrt(op1));
                        dataOutputStream.writeUTF(toreturn);
                        break;
                    case '%':
                        toreturn = String.valueOf((op2 / 100) * op1);
                        dataOutputStream.writeUTF(toreturn);
                        break;
                }
            } catch (IOException e) {
                try {
                    this.dataInputStream.close();
                    this.dataOutputStream.close();
                    this.socket.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                e.printStackTrace();
            }
        }

        try {
            this.dataInputStream.close();
            this.dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
