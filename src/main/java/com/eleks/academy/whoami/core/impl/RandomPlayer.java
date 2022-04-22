/*package com.eleks.academy.whoami.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.eleks.academy.whoami.core.Player;
import com.eleks.academy.whoami.networking.server.ServerThread;

public class RandomPlayer implements Player {

	private String name;
	private List<String> availableQuestions;
	private List<String> availableGuesses;
	
	public RandomPlayer(String name, List<String> availableQuestions, List<String> availableGuesses) {
		this.name = name;
		this.availableQuestions = new ArrayList<String>(availableQuestions);
		this.availableGuesses = new ArrayList<String>(availableGuesses);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getQuestion() {
		String question = availableQuestions.remove(0);
		System.out.println("Player: " + name + ". Asks: " + question);
		
		return question;
	}

	@Override
	public String answerQuestion(String question, String character, String nameAnswerer) {
		String answer = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + nameAnswerer + ". Answers: " + answer);
		return answer;
	}
	

	@Override
	public String answerGuess(String guess, String character, String nameGuesser) {
		String answer = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + nameGuesser + ". Answers: " + answer);
		return answer;
	}

	@Override
	public String getGuess() {
		int randomPos = (int)(Math.random() * this.availableGuesses.size()); 
		String guess = this.availableGuesses.remove(randomPos);
		System.out.println("Player: " + name + ". Guesses: Am I " + guess);
		return guess;
	}

	@Override
	public boolean isReadyForGuess() {
		return availableQuestions.isEmpty();
	}

	@Override
	public boolean isLose() {
		return availableGuesses.isEmpty();
	}
	
}
*/