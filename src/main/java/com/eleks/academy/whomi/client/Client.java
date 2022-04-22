package com.eleks.academy.whomi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {		
	
		try (Socket socket = new Socket("localhost", 888)) {

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			Scanner scanner = new Scanner(System.in);
			String userInput;
			String response;
			String clientName = "empty";

			ClientRunnable clientRun = new ClientRunnable(socket);

			new Thread(clientRun).start();

			if (clientName.equals("empty")) {
				System.out.println("Enter your name ");
				userInput = scanner.nextLine();
				clientName = userInput;
				output.println(userInput);
			}
				
			if (input.readLine() == "Start the game.");
			 
			do {
				if (input.readLine() == clientName) {
					String message = ("(" + clientName + ")" + " message : ");
					System.out.println(message);
					userInput = scanner.nextLine();
					output.println(message + " " + userInput);
					if (userInput.equals("exit")) {
						break;					
					}

				}
			} while (!input.readLine().equals("exit"));

		} 
			
		catch (Exception e) {
			System.out.println("Exception occured in client main: " + e.getStackTrace());
		}
	}
}
