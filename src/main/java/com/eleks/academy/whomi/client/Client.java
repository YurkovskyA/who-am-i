package com.eleks.academy.whomi.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class Client {
    private static String inputMessage;

    public static void main(String args[]) throws Exception {

        Socket clientSocket = new Socket("localhost", 888);

        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        PrintStream ps = new PrintStream(clientSocket.getOutputStream());

        String str;
        
        String playerName = "";
        
        boolean isName = true;

        System.out.println("Enter your name:");

        while (!(str = kb.readLine()).equals("exit")) {
            dos.writeBytes((isName ? "" : "Player: " + playerName) + (isName ? "" : ". ") + str + "\n");	
            if (isName){ 
            	playerName = str; 	
            	isName = false;
            	}
        }    
        dos.close();
        br.close();
        kb.close();
        clientSocket.close();
    }
}
