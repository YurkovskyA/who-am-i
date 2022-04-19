package com.eleks.academy.whoami.networking.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.eleks.academy.whoami.core.Player;

public class ClientPlayer implements Player {

	private String name;
	private Socket socket;
	private BufferedReader reader;
	private PrintStream writer;
	private DataOutputStream dos;

	public ClientPlayer(String name, Socket socket) throws IOException {
		this.name = name;
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintStream(socket.getOutputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getQuestion() {
		String question = "";

		try {
			System.out.println("Player: " + name + ". Ask your question.");
			writer.println("Player: " + name + ". Ask your question.");
			question = reader.readLine();
			System.out.println(question);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return question;
	}

	@Override
	public String answerQuestion(String question, String character, String nameAnswerer ) {
		String answer = "";
		
		try {
			writer.println("Player: " + nameAnswerer + ". Asks: " + question + " Character is: "+ character);
			answer = reader.readLine();
			System.out.println(answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer;
	}

	@Override
	public String getGuess() {
		String answer = "";
		
		try {
			writer.println("Write your guess: ");
			answer = reader.readLine();
			System.out.println(answer);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return answer;
	}

	@Override
	public boolean isReadyForGuess() {
		String answer = "";
		
		try {
			System.out.println("Player: " + name + ". Are you ready to guess? ");
			writer.println("Player: " + name + ". Are you ready to guess? ");
			answer = reader.readLine();
			System.out.println(answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer.equals("Yes") ? true : false;
	}

	@Override
	public String answerGuess(String guess, String character, String nameGuesser) {
		String answer = "";
		
		try {
			writer.println("Player: " + nameGuesser + ". Guesses: Am I a " +  guess + "?");
			answer = reader.readLine();
			System.out.println(answer);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return answer;
	}

	@Override
	public boolean isLose() {
		return false;
	}

}
