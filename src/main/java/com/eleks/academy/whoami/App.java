package com.eleks.academy.whoami;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.networking.client.ClientPlayer;
import com.eleks.academy.whoami.networking.server.ServerImpl;
import com.eleks.academy.whoami.networking.server.ServerThread;

public class App {

	public static void main(String[] args) throws IOException {

		ServerImpl server = new ServerImpl(888);

		Game game = server.startGame();
		
		int countClient = 0;

        ArrayList<ServerThread> threadList = new ArrayList<>();
        while(countClient < 2) {
    		var socket = server.waitForPlayer(game);
    		countClient ++;
  
    		ServerThread serverThread = new ServerThread(socket, threadList);
            //starting the thread
            threadList.add(serverThread); 
            serverThread.start();

            //get all the list of currently running thread

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			var playerName = reader.readLine();
			
			server.addPlayer(new ClientPlayer(playerName, socket));
			
        }
			
			boolean gameStatus = true;
	
			game.assignCharacters();
	
			game.initGame();
	
			boolean turnResult;
			boolean makeGuess;
			
			while (!game.isFinished()) {
				do {
					turnResult = game.makeTurn();}
				while (!turnResult);
				do {
					makeGuess = game.guessCharacter();}
				while (!makeGuess);						
	
				game.changeTurn();
			
			}
			
			
	/*		while (gameStatus) {
				boolean turnResult = game.makeTurn();
	
				while (turnResult) {
					turnResult = game.makeTurn();
				}
				game.changeTurn();
				gameStatus = !game.isFinished();
			}
	*/		
	
//			server.stopServer(socket, reader);
		
        
	}

}
