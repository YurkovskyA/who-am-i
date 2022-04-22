package com.eleks.academy.whoami;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.networking.client.ClientPlayer;
import com.eleks.academy.whoami.networking.server.ServerImpl;
import com.eleks.academy.whoami.networking.server.ServerThread;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {

		int enteredCountPlayers = 0; // count of number entered Clients
		int totalCountPlayers = 0; // count of total players in game

		// check count of players
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter count of players 1-4");
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("You should enter integer value from 1 to 4");
			} else {
				totalCountPlayers = scanner.nextInt();
				if (totalCountPlayers > 4) {
					System.out.println("You should enter number from 1 to 4");
				} else {
					scanner.close();
					break;
				}
			}
		}

		ServerImpl server = new ServerImpl(888);

		Game game = server.startGame();

		ArrayList<ServerThread> threadList = new ArrayList<>();

		while (enteredCountPlayers < totalCountPlayers) {
			var socket = server.waitForPlayer(game);
			enteredCountPlayers++;

			ServerThread serverThread = new ServerThread(socket, threadList);
			threadList.add(serverThread);
			serverThread.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			var playerName = reader.readLine();

			server.addPlayer(new ClientPlayer(playerName, socket, serverThread));		
			
			
			if (enteredCountPlayers != totalCountPlayers) {
				serverThread.printToALlClients("Wait for " + (totalCountPlayers - enteredCountPlayers) + " players.");
			}
			
			if (enteredCountPlayers == totalCountPlayers) {
				serverThread.printToALlClients("Start the game");

			}
		}
	
		boolean gameStatus = true;

		game.assignCharacters();

		game.initGame();

		boolean turnResult;
		boolean makeGuess;

		while (!game.isFinished()) {
			do {
				turnResult = game.makeTurn();
			} while (!turnResult);
			do {
				makeGuess = game.guessCharacter();
			} while (!makeGuess);

			game.changeTurn();
		}

	}

}
