package com.eleks.academy.whomi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 888)){

            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

            Scanner scanner = new Scanner(System.in);
            String userInput;
            String response;
            String clientName = "empty";

            ClientRunnable clientRun = new ClientRunnable(socket);


            new Thread(clientRun).start();
           
           do {
               
               if (clientName.equals("empty")) {
                    System.out.println("Enter your name ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
               } 
               else {
                    String message = ( "(" + clientName + ")" + " message : " );
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + " " + userInput);
                    if (userInput.equals("exit")) {
                        //reading the input from server
                        break;
                    }
                }

           } while (!userInput.equals("exit"));
                       
        } catch (Exception e) {
            System.out.println("Exception occured in client main: " + e.getStackTrace());
    }
    }
}

	
/*    private static String inputMessage;

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
*/    

