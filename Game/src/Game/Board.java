package Game;

import java.util.Vector;

public interface Board {

	/**
	 * deletes a piece from the board at the specific location
	 * @param isGroupA this parameter distinguish between two groups of pieces (group A or group B)
	 * @param location the specific location of the piece that we need to delete 
	 * @return true if succeeded false if not and null id not exist a piece at the specific location in the specific type of group
	 */
	Boolean deletePiece(boolean isGroupA, Location location);
	
	
	/**
	 * this method checks if there is now piece at the specific location
	 * @param row, column
	 * @return true if there is now piece at the specific location
	 */
	default boolean isAvailablePlace(int row, int column){
		return isAvailablePlace(new Location(row, column));
	}
	
	/**
	 * this method checks if there is now piece at the specific location
	 * @param location
	 * @return true if there is now piece at the specific location
	 */
	boolean isAvailablePlace(Location location);
	
	/**
	 * copies a board deeply
	 * @param board
	 */
	void deepCopy(Board board);
	
	/**
	 * copies a board deeply except an specific piece 
	 * @param board
	 * @param piece
	 */
	void deepCopyExceptOne(Board board, Game.Piece piece);

	void addPiece(Game.Piece piece);
	
	/**
	 * deletes a piece by it location
	 * @param location
	 * @return true if succeeded false if not and null id not exist a piece at the specific location
	 */
	Boolean deletePiece(Location location);
	
	Vector<Game.Piece> getListByType(boolean type);
	
	/**
	 * 
	 * @param isGroupA
	 * @return all the options to play for the user or computer
	 */
	Vector<Game.Board> getAllPlayOptions(boolean isGroupA);
	
	public int getRecord();
	
	void printBoard();


	void serRecord(int multiply);


	void setRecord(boolean isGroupA);
}
