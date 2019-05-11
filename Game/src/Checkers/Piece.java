package Checkers;

import java.util.Vector;
import Game.Location;

public class Piece implements Game.Piece{
	
	private boolean _isWhite;
	private boolean _isQueen;
	private Location _location;
	
	public Piece(boolean _isWhite, Location _location) {
		this._isWhite = _isWhite;
		this._isQueen = false;
		this._location = _location;
	}

	public Piece(boolean _isWhite, Location _location, boolean _isQueen) {
		this._isWhite = _isWhite;
		this._isQueen = false;
		this._location = _location;
		this._isQueen = _isQueen;
	}
	
	public boolean getIsWhite() {
		return _isWhite;
	}

	public void setIsWhite(boolean _isWhite) {
		this._isWhite = _isWhite;
	}

	public boolean getIsQueen() {
		return _isQueen;
	}

	public void setIsQueen(boolean _isQueen) {
		this._isQueen = _isQueen;
	}

	public Location getLocation() {
		return _location;
	}

	public void setLocation(Location _location) {
		this._location = _location;
	}
	
	@Override
	public Vector<Game.Board> getAllNextOptions(Game.Board currBoard){		
		Vector<Game.Board> allOptions = new Vector<>();
		if((this._isWhite || this._isQueen) && this._location.getRow() < 8){ //move up
			//near square
			if(this._location.getColumn() < 8 && currBoard.isAvailablePlace(this._location.getRow() + 1, this._location.getColumn() + 1))
				allOptions.add(nextBoard((Board) currBoard, 1, 1, null));
			if(this._location.getColumn() > 1 && currBoard.isAvailablePlace(this._location.getRow() + 1, this._location.getColumn() - 1))
				allOptions.add(nextBoard((Board) currBoard, 1, -1, null));
		}
		if((!this._isWhite || this._isQueen) && this._location.getRow() > 1){ //move down
			//near square
			if(this._location.getColumn() < 8 &&
					currBoard.isAvailablePlace(this._location.getRow() - 1, this._location.getColumn() + 1))
				allOptions.add(nextBoard((Board) currBoard, -1, 1, null));
			if(this._location.getColumn() > 1 &&
					currBoard.isAvailablePlace(this._location.getRow() - 1, this._location.getColumn() - 1))
				allOptions.add(nextBoard((Board) currBoard, -1, -1, null));
		}
		//move by capture
		Vector<Board> allByCaptured = new Vector<>();
		capturedPiecesOptions((Board)currBoard, false, allByCaptured);
		allOptions.addAll(allByCaptured);
		return allOptions;
	}
	
	/**
	 * this method creates a new board with the next move that we asked for
	 * @param currBoard the current situation on the board
	 * @param rowToAdd how many steps up to move
	 * @param columnToAdd how many steps up to move
	 *  * @param locationToDelete the location of a piece that we want to delete (optionally) 
	 * @return the new board with the next move
	 */
	private Board nextBoard(Board currBoard, int rowToAdd, int columnToAdd, Location locationToDelete){
		Board newBoard = new Board();
		newBoard.deepCopyExceptOne(currBoard, this);
		Piece p = new Piece(this._isWhite, new Location(this._location.getRow() + rowToAdd, this._location.getColumn() + columnToAdd), this._isQueen);
		if(!p._isQueen && ((p._isWhite && p._location.getRow() == 8) || (!p._isWhite && p._location.getRow() == 1))) //p isn't a Queen but it needs to be
			p._isQueen = true;
		newBoard.addPiece(p);
		if(locationToDelete != null) //if the next move also include a captured activity
			newBoard.deletePiece(locationToDelete);
		return newBoard;
	}
	
	/**
	 * adds to allOptions all the captured options of the specific piece at the specific board
	 * @param currBoard 
	 * @param isSecondCaptured true if it more than one captured in a row
	 * @param allOptions
	 */
	private void capturedPiecesOptions(Board currBoard, boolean isSecondCaptured, Vector<Board> allOptions){
		Board newBoard;
		if((this._isWhite || this._isQueen || isSecondCaptured) &&
				this._location.getRow() < 7){	
			if(canCaptured(true, true, currBoard)){ //check if can captured to up right direction
				newBoard = nextBoard(currBoard, 2, 2, new Location(this._location.getRow() + 1, this._location.getColumn() + 1)); //create a new board with the specified move and captured
				newBoard.getPiece(new Location(this._location.getRow() + 2, this._location.getColumn() + 2)).capturedPiecesOptions(newBoard, true, allOptions);
				allOptions.addElement(newBoard);
			}			
			if(canCaptured(false, true, currBoard)){ //check if can captured to up left direction		
				newBoard = nextBoard(currBoard, 2, -2, new Location(this._location.getRow() + 1, this._location.getColumn() - 1)); 
				newBoard.getPiece(new Location(this._location.getRow() + 2, this._location.getColumn() - 2)).capturedPiecesOptions(newBoard, true, allOptions);
				allOptions.addElement(newBoard);
			}
		}
		if((!this._isWhite || this._isQueen || isSecondCaptured) &&
				this._location.getRow() > 2){	
			if(canCaptured(true, false, currBoard)){ //check if can captured to down right direction		
				newBoard = nextBoard(currBoard, -2, 2, new Location(this._location.getRow() - 1, this._location.getColumn() + 1));
				newBoard.getPiece(new Location(this._location.getRow() - 2, this._location.getColumn() + 2)).capturedPiecesOptions(newBoard, true, allOptions);
				allOptions.addElement(newBoard);
			}
			if(canCaptured(false, false, currBoard)){ //check if can captured to down left direction
				newBoard = nextBoard(currBoard, -2, -2, new Location(this._location.getRow() - 1, this._location.getColumn() - 1));
				newBoard.getPiece(new Location(this._location.getRow() - 2, this._location.getColumn() - 2)).capturedPiecesOptions(newBoard, true, allOptions);
				allOptions.addElement(newBoard);
			}
		}
	}
	
	/**
	 * checks if this piece can captured in the specific direction
	 * @param isRight true if we check about captured right. else, left
	 * @param isUp true if we check about captured up. else, down
	 * @param currBoard 
	 * @return true if this piece can captured 
	 */
	private boolean canCaptured(boolean isRight, boolean isUp, Board currBoard){
		return isRight ? canCaptuerdRight(isUp, currBoard) : canCapturedLeft(isUp, currBoard);
	}
	
	/**
	 * checks if this piece can captured right in the specific direction
	 * @param isUp
	 * @param currBoard
	 * @return true if can. means: the piece can move 2 step to the specific direction forward (this location is available and exist),
	 * 	And one step from it in the specific direction there is a piece from different color.
	 */
	private boolean canCaptuerdRight(boolean isUp, Board currBoard){
		Location locationOfCapturedPiece = new Location(isUp ? this._location.getRow() + 1 : 
			this._location.getRow() - 1, this._location.getColumn() + 1);
		return isUp ? this._location.getColumn() < 7 
						&& currBoard.isCapturedBySpecificType(!this._isWhite, locationOfCapturedPiece) 
						&& currBoard.isAvailablePlace(this._location.getRow() + 2, this._location.getColumn() + 2) : 
							this._location.getColumn() < 7 &&
							currBoard.isCapturedBySpecificType(!this._isWhite, locationOfCapturedPiece) &&
							currBoard.isAvailablePlace(this._location.getRow() - 2, this._location.getColumn() + 2);
							
	}
	
	/**
	 * check if this piece can captured left in the specific direction
	 * @param isUp
	 * @param currBoard
	 * @return true if can. means: the piece can move 2 step to the specific direction forward (this location is available and exist),
	 * 	And one step from it in the specific direction there is a piece from different color.
	 */
	private boolean canCapturedLeft(boolean isUp, Board currBoard){
		Location locationOfCapturedPiece = new Location(isUp ? this._location.getRow() + 1 : 
			this._location.getRow() - 1, this._location.getColumn() - 1);
		return isUp ? this._location.getColumn() > 2 &&
				currBoard.isCapturedBySpecificType(!this._isWhite, locationOfCapturedPiece) &&
				currBoard.isAvailablePlace(this._location.getRow() + 2, this._location.getColumn() - 2) : 
					this._location.getColumn() > 2 &&
					currBoard.isCapturedBySpecificType(!this._isWhite, locationOfCapturedPiece) &&
					currBoard.isAvailablePlace(this._location.getRow() - 2, this._location.getColumn() - 2);
	}
	
	/**
	 * check if this piece has a captured option for this move
	 * @param board
	 * @param isSecondCaptured
	 * @return
	 */
	public boolean checkIfMustTOCaptured(Board board, boolean isSecondCaptured){
		if((this._isWhite || this._isQueen || isSecondCaptured) &&
				this._location.getRow() < 7){
			if(canCaptured(true, true, board) || canCaptured(false, true, board))
				return true;
		}
		if((!this._isWhite || this._isQueen || isSecondCaptured) &&
				this._location.getRow() > 2){
			if(canCaptured(true, false, board) || canCaptured(false, false, board))
				return true;
		}
		return false;
	}
	
	/**
	 * check if this piece can become a queen now
	 * @return
	 */
	public boolean CanBeQueen(){
		return (this._isWhite && this._location.getRow() == 8) || (!this._isWhite && this._location.getRow() == 1);
	}
}