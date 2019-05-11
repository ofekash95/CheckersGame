package Algorithm;

import java.util.Scanner;
import Checkers.Run;


public class Main {
	
	private static Scanner reader = new Scanner(System.in);
	private final int numOfGames = 1;
	
	public static void main(String[] args){
		Main m = new Main();
		m.chooseAGame();
	}
	
	
	
	public void chooseAGame(){
		int gameType;
		boolean gotALegalGameNumber = false; //check if the user chose a legal option of game
		boolean playAgain = false;
		System.out.println("Welcome To The Game"); 
		while(!gotALegalGameNumber || playAgain){
			playAgain = false;
			gotALegalGameNumber = false;
			System.out.println("\nplease choose a game or exit:");
			System.out.println("1) checkers game");
			System.out.println("0) EXIT");
			String game = reader.next();
			if(isContainsOnlyNumbers(game) && ((gameType = Integer.parseInt(game)) >= 0 && gameType <= numOfGames)){
				switch(gameType){
					case 0:
						gotALegalGameNumber = true;
						break;
					case 1:
						Checkers.Run checkersGame = new Run();
						checkersGame.runTheGame();
						gotALegalGameNumber = true;
						playAgain = true;
						break;
				}
			}
			if(!gotALegalGameNumber)
				System.out.println("Wrong Input");		
		}
		
		
	}
	
	/**
	 * checks if the specific string doesn't contain something that not a number
	 * @param str
	 * @return 
	 */
	private boolean isContainsOnlyNumbers(String str){
		for(int i = 0; i < str.length(); i++)
			if(str.charAt(i) > '9' || str.charAt(i) < '0')
				return false;
		return true;
	}
}
