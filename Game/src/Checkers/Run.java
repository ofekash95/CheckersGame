package Checkers;

import java.util.Scanner;
import java.util.Vector;


import Algorithm.AlgoNode;
import Algorithm.AlgoTree;
import Game.Location;

public class Run implements Game.Run{

	private static Scanner _reader = new Scanner(System.in);
	public boolean _compType;
	
	private int _hight;
		
	@Override
	public void startNewGame() {	
		int colorType;
		boolean legalNumber = false;
		while(!legalNumber){
			System.out.println("please choose a number of the color you whould like to play with:\n"
					+ "1) white\n"
					+ "2) dark");
			String game = _reader.next();
			if(isContainsOnlyNumbers(game) && ((colorType = Integer.parseInt(game)) >= 1 && colorType <= 2)){
				switch(colorType){
					case 1:
						_compType = false;
						legalNumber = true;
						break;
					case 2:
						_compType = true;
						legalNumber = true;
						break;
				}
			}
			if(!legalNumber)
				System.out.println("Wrong Input");		
		}
	}
	
	

	@Override
	public Board initBoard(){
		Vector<Piece> whitePieces = new Vector<>();
		Vector<Piece> darkPieces = new Vector<>();
		for(int i = 1; i < 4; i++){
			int j = 1;
			if(i%2 == 0)
				j = 2;
			while(j <= 8){
				whitePieces.addElement(new Piece(true, new Location(i, j)));
				darkPieces.addElement(new Piece(false, new Location(9 - i, 9 - j)));
				j = j + 2;
			}
		}
		return new Board(darkPieces, whitePieces);
	}



	//
	//delete!!!
	//
	//delete!!!
	//
	private Board check(){
		Piece p1 = new Piece(true, new Location(3,5));
		Piece p4 = new Piece(true, new Location(7,1));
		Piece p5 = new Piece(true, new Location(7,3));
	//	Piece p6 = new Piece(true, new Location(2,4));
	//	Piece p7 = new Piece(true, new Location(2,6));
	//	Piece p2 = new Piece(false, new Location(8,4));
		Piece p3 = new Piece(false, new Location(6,8));
	
		Vector<Piece> v1 = new Vector<>();
		Vector<Piece> v2 = new Vector<>();
		
		v1.add(p1);
		v1.add(p4);
		v1.add(p5);
	//	v1.add(p6);
	//	v1.add(p7);
	//	v2.add(p2);
		v2.add(p3);
		
		return new Board(v2, v1);
	}
	
	
	@Override
	public void runTheGame() {
		startNewGame();		
		Board board = initBoard();
		board.printBoard();
		int counter = 0; 
		this._hight = 5;
		while(!board.getDarkPieces().isEmpty() && !board.getWhitePieces().isEmpty()){
			if((counter % 2 == 0 && this._compType) || (counter % 2 == 1 && !this._compType)){ //if the turn of white and computer is white or the turn of dark and computer is dark
				board = (Board) computerTurn(board);
				if(board == null){
					System.out.println("YOUE ARE A LOOZER");
					System.out.println("takes: " + counter + " turns");
					return;
				}
			}
			else{
				board = (Board) playerTurn(board);
				if(board == null){
					System.out.println("Congratulations, you've defeated your computer");
					System.out.println("takes: " + counter + " turns");
					return;
				}				
			}
			
			board.printBoard();
			counter++;
			if(board.getNumOfPieces() < 8)
				this._hight = 5;
			if(board.getNumOfPieces() < 3)
				this._hight = 5;
			
		}
		if((board.getDarkPieces().isEmpty() && _compType) || (board.getWhitePieces().isEmpty() && !_compType))
			System.out.println("YOUE ARE A LOOZER");
		else 
			System.out.println("Congratulations, you've defeated your computer");
		System.out.println("takes: " + counter + " turns");
	}



	@Override
	public Game.Board computerTurn(Game.Board board) {
		boolean nextTurnColor = this._compType;
		AlgoNode root = new AlgoNode(board); //current board
		AlgoTree tree = new AlgoTree(root, this._hight);   
		board.setRecord(this._compType);
		root.setRecord(board.getRecord());
		treeBuilding(root, 0);		
		Game.Board b =  tree.getBestMove();	
		return b;
	}
	
	/**
	 * builds the AlgoTree 
	 * @param node
	 * @param depth
	 */
	public void treeBuilding(AlgoNode node, int depth){
		Vector <Game.Board> nextBoards = new Vector<>();
		if(depth > this._hight)
			return;
		nextBoards = node.getBoard().getAllPlayOptions((node.isCompTurn() && this._compType) || (!node.isCompTurn() && !this._compType)); //compTurn and it white or user turn and comp is dark
		for(Game.Board b : nextBoards){		
			if(node.isCompTurn() && ((this._compType && b.getListByType(false).isEmpty()) || (!this._compType && b.getListByType(true).isEmpty()))) //the comp can win 
				b.serRecord(this._hight - depth + 1);
			else
				b.setRecord(this._compType);
			AlgoNode nextNode = new AlgoNode(b, b.getRecord());
			node.addChild(nextNode);
			treeBuilding(nextNode, depth + 1);
		}
	}


	@Override
	public Game.Board playerTurn(Game.Board board) {
		if(board.getAllPlayOptions(!_compType).isEmpty())
			return null;
		Board currBoard = new Board();
		currBoard.deepCopy(board);
		String path = "X"; //path's length at the beginning needs to be not equal to 0
		String from = "";
		String to = "";
		Location fromLoc = new Location(0, 0);
		Location toLoc;
		boolean isSecondCaptured;
		boolean legalMove = false; //will told us if we reached the next loop because a legal move or because an illegal move
		while(path.length() != 0){			
			isSecondCaptured = true;
			if(!legalMove){
				System.out.println("\nPlease enter the path of your move in this turn\n"
						+ "write it in this way: XX-XX-XX ...\n"
						+ "when the first XX is the beginning point of the piece,\n"
						+ "and the others are the intermediate points till the last one, the end point of the piece.\n"
						+ "the first X will be the row: 1-8, and the second X wil be the column: 1-8\n"
						+ "between each location write '-'\n");
				from = "";
				to = "";
				path = this._reader.next(); //get input from the user
				if(path.length() < 5){ //beginning point and there isn't at list 2 locations
					path = "X"; //to don't get out from the next loop
					System.out.println("Wrong LocationA");
					continue;
				}
				from = nextLocation(path);
				if(from == null){ 
					System.out.println("Wrong LocationB");
					continue;
				}
				fromLoc = ToLocation(from);
				if(!isPieceLocationInList(fromLoc, board.getListByType(!this._compType))){ //if the location doesn't contain a player's piece
					System.out.println("Wrong Move! You must move with your own color");
					continue;
				}
				path = path.substring(3);
				isSecondCaptured = false; //beginning point
			}
			to = nextLocation(path);
				
			if(to == null){
				legalMove = false;
				System.out.println("Wrong LocationC");
				continue;
			}
			
			if(path.length() == 2) //end of path
				path = "";
			else
				path = path.substring(3);
			toLoc = ToLocation(to);
			if(!isSecondCaptured && !currBoard.getPiece(fromLoc).getIsQueen() && ((this._compType && fromLoc.getRow() < toLoc.getRow()) || (!this._compType && fromLoc.getRow() > toLoc.getRow()))){ //check if can move to this direction
				legalMove = false;
				System.out.println("Wrong Move! You cannot move back");
				path = "X"; //for not allow get out from the while loop
				continue;
			}
			
			if(!currBoard.isAvailablePlace(toLoc)){
				legalMove = false;
				System.out.println("Wrong Move! this locatiob isn't available");
				path = "X"; //for not allow get out from the while loop
				continue;
			}
			Board tmpBoard = playTheTurn(currBoard, fromLoc, toLoc, isSecondCaptured);
			if(tmpBoard == null){
				legalMove = false;
				System.out.println("Wrong LocationD");
				path = "X"; //for not allow get out from the while loop
				continue;
			}
			currBoard.deepCopy(tmpBoard);
			fromLoc.deepCopy(toLoc);
			legalMove = true;
		}
		return currBoard;
	}
	
	
	/**
	 * get the next location in the path
	 * @param path
	 * @return
	 */
	private String nextLocation(String path){		
		String nextLocation; 
		if(path.length() < 2) //smaller to be a location
			return null;
		nextLocation = path.substring(0, 2); 
		if(nextLocation.charAt(0) < '1' || nextLocation.charAt(0) > '8') //if illegal row
			return null;
		if(nextLocation.charAt(1) < '1' || nextLocation.charAt(1) > '8') //if illegal column
			return null;
		return nextLocation;
	}
	
	/**
	 * Converts String to Location
	 * @param loc
	 * @return
	 */
	private Location ToLocation(String loc){
		return new Location(Integer.parseInt(loc.charAt(0) + ""), Integer.parseInt(loc.charAt(1) + ""));
	}
	
	/**
	 * move the piece from the location - from, to the location - to
	 * @param currBoard
	 * @param from
	 * @param to
	 * @param isSecondCaptured
	 * @return the new board after this move if succeed or null if not 
	 */
	private Board playTheTurn(Board currBoard, Location from, Location to, boolean isSecondCaptured){
		Board newBoard = new Board();
		Piece p1 = currBoard.getPiece(from);
		Piece p2 = new Piece(!_compType, to, p1.getIsQueen());
		if(!p2.getIsQueen() && ((!_compType && p2.getLocation().getRow() == 8) || (_compType && p2.getLocation().getRow() == 1))) //p2 isn't queen but it reached to the last row
			p2.setIsQueen(true);
		if(!currBoard.isAvailablePlace(to))
			return null;
		if(Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getColumn() - to.getColumn()) == 1){ //regular move
			newBoard.deepCopyExceptOne(currBoard, p1);
			newBoard.addPiece(p2);
			return newBoard;
		}
		if(Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getColumn() - to.getColumn()) == 2){ //capture move
			Location toCapture = getPieceCapturedByLocation(currBoard, p1, isSecondCaptured, to.getRow() > from.getRow(), to.getColumn() > from.getColumn());			
			if(toCapture != null){
				newBoard.deepCopyExceptOne(currBoard, p1);
				newBoard.addPiece(p2);
				newBoard.deletePiece(toCapture);
				return newBoard;
			}	
		}
		return null;
	}
	
	/**
	 * gets the piece which captured by in the specific direction
	 * @param currBoard
	 * @param piece
	 * @param isSecondCaptured
	 * @param rowUp
	 * @param columnUp
	 * @return
	 */
	private Location getPieceCapturedByLocation(Board currBoard, Piece piece, boolean isSecondCaptured, boolean rowUp, boolean columnUp){
		Location location;
		if(piece.getIsWhite() || piece.getIsQueen() || isSecondCaptured){
			if(rowUp && columnUp){
				location = new Location(piece.getLocation().getRow() + 1, piece.getLocation().getColumn() + 1);
				if(currBoard.isCapturedBySpecificType(_compType, location))
					return location;
			}
			if(rowUp && !columnUp){
				location = new Location(piece.getLocation().getRow() + 1, piece.getLocation().getColumn() - 1);
				if(currBoard.isCapturedBySpecificType(_compType, location))
					return location;
			}
		}
		if(!piece.getIsWhite() || piece.getIsQueen()|| isSecondCaptured){
			if(!rowUp && columnUp){
				location = new Location(piece.getLocation().getRow() - 1, piece.getLocation().getColumn() + 1);
				if(currBoard.isCapturedBySpecificType(_compType, location))
					return location;
			}
			if(!rowUp && !columnUp){
				location = new Location(piece.getLocation().getRow() - 1, piece.getLocation().getColumn() - 1);
				if(currBoard.isCapturedBySpecificType(_compType, location))
					return location;
			}
		}
		return null;
	}
	
	/**
	 * checks if in the list there is a piece in the specific location
	 * @param l
	 * @param vector
	 * @return
	 */
	private boolean isPieceLocationInList(Location l, Vector<Game.Piece> vector){
		for(Game.Piece p : vector)
			if(((Piece) p).getLocation().equals(l))
				return true;
		return false;
	}
}
