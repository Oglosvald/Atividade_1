package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName("localhost");

        try (
                Scanner scn = new Scanner(System.in);
                Socket servidorA = new Socket(ip, 5556);
                Socket servidorB = new Socket(ip, 5557);

                DataInputStream dataInputServerA = new DataInputStream(servidorA.getInputStream());
                DataOutputStream dataOutputServerA = new DataOutputStream(servidorA.getOutputStream());

                DataInputStream dataInputServerB = new DataInputStream(servidorB.getInputStream());
                DataOutputStream dataOutputServerB = new DataOutputStream(servidorB.getOutputStream());
        ) {

            while (true) {
                String tossed = scn.nextLine();
                String[] expression = tossed.split(" ");

                if (tossed.equals("Exit")) {
                    System.out.println("Closing this connection : " + servidorA);
                    System.out.println("Closing this connection : " + servidorB);
                    dataOutputServerA.writeUTF(tossed);
                    dataOutputServerB.writeUTF(tossed);
                    servidorA.close();
                    servidorB.close();
                    System.out.println("Connection closed");
                    break;
                }

                String received = "";

                if (expression.length >= 2) {
                    char operation = expression[1].charAt(0);
                    if (expression.length == 3) {
                        if (operation == '+' || operation == '-' || operation == '*' || operation == '/') {
                            dataOutputServerA.writeUTF(tossed);
                            received = dataInputServerA.readUTF();
                        } else if (operation == '^' || operation == '%') {
                            dataOutputServerB.writeUTF(tossed);
                            received = dataInputServerB.readUTF();
                        }
                    } else if (expression.length == 2) {
                        if (operation == 'r') {
                            dataOutputServerB.writeUTF(tossed);
                            received = dataInputServerB.readUTF();
                        }
                    } else {
                        System.out.println("Input invalid");
                    }
                } else {
                    System.out.println("Input invalid");
                }

                System.out.println(received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
