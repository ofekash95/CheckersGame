package Game;

import java.util.Vector;

public interface Piece {
	
	/**
	 * 
	 * @param currBoard the current situation on the board
	 * @return all the options of the specified piece to move 
	 */
	Vector<Game.Board> getAllNextOptions(Game.Board currBoard);
}
