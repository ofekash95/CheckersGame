package Checkers;

import java.security.AllPermission;
import java.util.Vector;

import javax.print.attribute.standard.OutputDeviceAssigned;

import Game.Location;

public class Board implements Game.Board{
	
	private Vector<Piece> _darkPieces = new Vector<>();
	private Vector<Piece> _whitePieces = new Vector<>();	
	private int _record;
	
	public Board(){
		this._whitePieces = new Vector<>();
		this._darkPieces = new Vector<>();
	}
	
	public Board(Vector<Piece> _darkPieces, Vector<Piece> _whitePieces){
		this._whitePieces = _whitePieces;
		this._darkPieces = _darkPieces;
		
		
	}

	public Vector<Piece> getDarkPieces() {
		return _darkPieces;
	}

	public void setDarkPieces(Vector<Piece> _darkPieces) {
		this._darkPieces = _darkPieces;
	}

	public Vector<Piece> getWhitePieces() {
		return _whitePieces;
	}

	public void setWhitePieces(Vector<Piece> _whitePieces) {
		this._whitePieces = _whitePieces;
	}
	
	@Override
	public void addPiece(Game.Piece piece) { 
		if(((Piece)piece).getIsWhite()) 
			this._whitePieces.addElement((Piece) piece);
		else
			this._darkPieces.addElement((Piece) piece);	
	}
	
	@Override
	public Boolean deletePiece(Location location) { 
		Boolean isDeleted = null;
		if((isDeleted = deletePieceHelper(this._whitePieces, location)) == null) //if wasn't found a white piece at this location  
			isDeleted = deletePieceHelper(this._darkPieces, location);
		return isDeleted;
	}

	@Override
	public Boolean deletePiece(boolean isGroupA, Location location) { //the meaning of the argument isGroupA is isWhite
		return deletePieceHelper(isGroupA ? this._whitePieces : this._darkPieces, location);
	}
	
	private Boolean deletePieceHelper(Vector<Piece> pieces, Location location){
		for(Piece p : pieces)
			if(p.getLocation().equals(location))
				return pieces.remove(p);
		return null;
	}
	
	@Override
	public boolean isAvailablePlace(Location location) { 
		for(Piece p : this._whitePieces)
			if(p.getLocation().equals(location))
				return false;
		for(Piece p : this._darkPieces)
			if(p.getLocation().equals(location))
				return false;
		return true;
	}

	@Override
	public void deepCopy(Game.Board board) { 
		this._whitePieces.clear();
		this._darkPieces.clear();
		for(Piece p : ((Board)board)._whitePieces)
			this.addPiece(p);
		for(Piece p : ((Board)board)._darkPieces)
			this.addPiece(p);	
	}

	@Override
	public void deepCopyExceptOne(Game.Board board, Game.Piece piece) { 
		this._whitePieces.clear();
		this._darkPieces.clear();
		for(Piece p : ((Board) board)._whitePieces )
			if(!p.getLocation().equals(((Piece)piece).getLocation()))
				this.addPiece(p);
		for(Piece p : ((Board) board)._darkPieces)
			if(!p.getLocation().equals(((Piece)piece).getLocation()))
				this.addPiece(p);
	}
	
	/**
	 * this method checks if the location captured by a piece with a specific type (white or dark)
	 * @param isWhite the type of the pieces we want to know about them
	 * @param location the location we want to check if captured
	 * @return true if there is a piece with the specific type in the specific location
	 */
	public boolean isCapturedBySpecificType(boolean isWhite, Location location){	
		
		if(!location.isLegalLocation())
			return false;
		if(isWhite){
			for(Piece p : this._whitePieces)
				if(p.getLocation().equals(location))
					return true;			
		}
		else{
			for(Piece p : this._darkPieces)
				if(p.getLocation().equals(location))
					return true;								
		}		
		return false;
	}	
	
	/**
	 * 
	 * @param location
	 * @return the piece in the specific location
	 */
	public Piece getPiece(Location location){
		for(Piece p : this._whitePieces)
			if(p.getLocation().equals(location))
				return p;
		for(Piece p : this._darkPieces)
			if(p.getLocation().equals(location))
				return p;
		return null;
	}

	@Override
	public Vector<Game.Piece> getListByType(boolean type) {
		return type ? copyVector(this._whitePieces) : copyVector(this._darkPieces);
	}
	
	private Vector<Game.Piece> copyVector(Vector<Piece> vector){
		Vector<Game.Piece> output = new Vector<>();
		for(Piece p : vector)
			output.addElement((Game.Piece) p);
		return output;
	}

	@Override
	public Vector<Game.Board> getAllPlayOptions(boolean isGroupA) {
		Vector<Game.Board> allOptions = new Vector<>();
		if(isGroupA)
			for(Piece p : this._whitePieces)
				allOptions.addAll(p.getAllNextOptions(this));
		else 
			for(Piece p: this._darkPieces)
				allOptions.addAll(p.getAllNextOptions(this));
		return allOptions;
	}
	
	@Override
	public void serRecord(int multiply){
		this._record = 100*multiply;
	}
	
	@Override
	public void setRecord(boolean isGroupA){
		int numOfWQueen = 0;
		int numOfDQueen = 0;
		for(Piece p : this._whitePieces)
			if(p.getIsQueen())
				numOfWQueen++;
		for(Piece p : this._darkPieces)
			if(p.getIsQueen())
				numOfDQueen++;
		if(isGroupA)
			this._record = this._whitePieces.size() - this._darkPieces.size() + 2*(numOfWQueen - numOfDQueen);
		else
			this._record = this._darkPieces.size() - this._whitePieces.size() + 2*(numOfDQueen - numOfWQueen);
	}
	
	@Override
	public int getRecord(){
		return this._record;
	}
	
	@Override
	public void printBoard() {		
		char[][] table = newInitTable();
		Location loc;
		char c;
		for(Piece p : this._whitePieces){ //init white pieces
			loc = p.getLocation();
			if(p.getIsQueen())
				c = 'W';
			else
				c = 'w';
			table[(8 - loc.getRow())][loc.getColumn() - 1] = c;	
		}
		for(Piece p : this._darkPieces){ //init dark pieces
			loc = p.getLocation();
			if(p.getIsQueen())
				c = 'D';
			else
				c = 'd';
			table[(8 - loc.getRow())][loc.getColumn() - 1] = c;
			
		}
		printBoardHelper(table);
	}
	
	/**
	 * create new table with size of: 8*8, and initializes it
	 * @return
	 */
	private char[][] newInitTable(){
		char[][] table = new char[8][8];
		for(int i = 0; i < table.length; i++){
			for(int j = 0; j < table[i].length; j++){
				table[i][j] = '*';
			}
		}
		return table;
	}
	
	/**
	 * prints the chars
	 * @param table
	 */
	private void printBoardHelper(char[][] table){
		for(int i = 0; i < table.length; i++){
			System.out.println("\n");
			System.out.print(8 - i + "  ");
			for(int j = 0; j < table[i].length; j++)
				System.out.print(table[i][j] + "  ");
		}
		System.out.println("\n\n   1  2  3  4  5  6  7  8");
		System.out.println();
	}	
	
	public int getNumOfPieces(){
		return this._whitePieces.size() + this._darkPieces.size();
	}
}