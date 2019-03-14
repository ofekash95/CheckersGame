package Tests;

import java.util.Vector;

import Checkers.Board;
import Game.Location;

public class PieceTest {

	public void capturedPiecesOptionsTest(){
		Board board = new Board();
//		board.addPiece(true, new Location(2, 4));
//		board.addPiece(false, new Location(3, 5));
//		board.addPiece(false, new Location(5, 3));
//		board.addPiece(false, new Location(5, 5));
//		board.addPiece(false, new Location(7, 3));
//		board.addPiece(false, new Location(7, 5));
//		board.addPiece(false, new Location(7, 7));
		Vector<Game.Board> boards = new Vector<>();
		boards = board.getWhitePieces().get(0).getAllNextOptions(board);
		for(int i = 0; i < boards.size(); i++){
			System.out.println(i + ") " + ((Board) boards.get(i)).getWhitePieces().get(0).getLocation().getRow() + "," + ((Board) boards.get(i)).getWhitePieces().get(0).getLocation().getColumn());
			System.out.println( ((Board) boards.get(i)).getWhitePieces().get(0).getIsQueen());
		}
	}
}
