package com.eleks.academy.whoami.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.core.Player;
import com.eleks.academy.whoami.core.Turn;

public class RandomGame implements Game {
	
	private Map<String, String> playersCharacter = new HashMap<>();
	private List<Player> players = new ArrayList<>();
	private List<String> availableCharacters;
	private Turn currentTurn;
	private int countPlayers = 0;

	
	private final static String YES = "Yes";
	private final static String NO = "No";
	
	public RandomGame(List<String> availableCharacters) {
		this.availableCharacters = new ArrayList<String>(availableCharacters);
	}

	@Override
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	@Override
	public boolean makeTurn() {
		Player currentGuesser = currentTurn.getGuesser();
		if (currentGuesser.isReadyForGuess())
			return true;
		Set<String> answers;	
		String question = currentGuesser.getQuestion();
		answers = currentTurn.getOtherPlayers().stream()
			.map(player -> player.answerQuestion(question, this.playersCharacter.get(currentGuesser.getName()), currentGuesser.getName()))
			.collect(Collectors.toSet());
		long positiveCount = answers.stream().filter(a -> YES.equalsIgnoreCase(a)).count();
		long negativeCount = answers.stream().filter(a -> NO.equalsIgnoreCase(a)).count();
		return positiveCount > negativeCount;
	}

	@Override
	public void assignCharacters() {
		players.stream().forEach(player -> this.playersCharacter.put(player.getName(), this.getRandomCharacter()));
		
	}
	
	@Override
	public void initGame() {
		this.currentTurn = new TurnImpl(this.players);
		
	}

	@Override
	public boolean isFinished() {
//		return players.isEmpty();
		return this.players.size() == countPlayers;
	}
	
	private String getRandomCharacter() {
		int randomPos = (int)(Math.random() * this.availableCharacters.size()); 
		return this.availableCharacters.remove(randomPos);
	}

	@Override
	public void changeTurn() {
		this.currentTurn.changeTurn();
		countPlayers ++;
	}
	
	@Override
	public boolean guessCharacter() {
		Player currentGuesser = currentTurn.getGuesser();
		if (currentGuesser.isLose()){
//			players.remove(currentGuesser);
			return true;
		}
		String guess = currentGuesser.getGuess();
		Set<String> answers;
		
		answers = currentTurn.getOtherPlayers().stream()
				.map(player -> player.answerGuess(guess, this.playersCharacter.get(currentGuesser.getName()), currentGuesser.getName()))
				.collect(Collectors.toSet());
		long positiveCount = answers.stream().filter(a -> YES.equalsIgnoreCase(a)).count();
		long negativeCount = answers.stream().filter(a -> NO.equalsIgnoreCase(a)).count();
		
		boolean win = positiveCount > negativeCount;
		
		if (win) {
//			players.remove(currentGuesser);
		}
		return win;
	}
	
	public int getCountPlayers()
	{
		return this.players.size();
	}

}
