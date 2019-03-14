package Game;

import Checkers.Board;

public interface Run {
	
	
	/**
	 * start a new game
	 */
	void startNewGame();
	
	/**
	 * orders the pieces at the right place in a new board
	 */
	Board initBoard();
	
	void runTheGame();
	
	Game.Board computerTurn(Game.Board board);
	
	Game.Board playerTurn(Game.Board board);
	
	/**
	 * checks if the specific string doesn't contain something that not a number
	 * @param str
	 * @return 
	 */
	default boolean isContainsOnlyNumbers(String str){
		for(int i = 0; i < str.length(); i++)
			if(str.charAt(i) > '9' || str.charAt(i) < '0')
				return false;
		return true;
	}
}