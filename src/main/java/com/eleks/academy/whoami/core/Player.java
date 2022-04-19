package com.eleks.academy.whoami.core;

public interface Player {

	String getName();
	
	String getQuestion();
	
	String answerQuestion(String question, String character, String nameAnwerer);
	
	String getGuess();
	
	boolean isReadyForGuess();

	public String answerGuess(String guess, String character, String nameGuesser);
	
	public boolean isLose();
	
}
