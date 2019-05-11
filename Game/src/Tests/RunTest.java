package Tests;

import java.util.Vector;

import Algorithm.AlgoNode;
import Checkers.Board;
import Checkers.Piece;
import Checkers.Run;
import Game.Location;

public class RunTest {
	
	public void playerTurnTest(){
		Board board = new Board();
		board.addPiece(new Piece(true, new Location(3,3), true));
		//board.addPiece(false, new Location(2, 2));
		//board.addPiece(false, new Location(2, 4));
		//board.addPiece(false, new Location(4, 2));
		//Run run = new Run();
		//Board boardA = (Board) run.playerTurn(board);
	}
	
	
	public static void treeBuildingTest(){
		Piece p1 = new Piece(true, new Location(3,5));
		Piece p2 = new Piece(true, new Location(3,7));
		Piece p3 = new Piece(false, new Location(4,6));
	
		Vector<Piece> v1 = new Vector<>();
		Vector<Piece> v2 = new Vector<>();
		
		v1.add(p1);
		v1.add(p2);
		v2.add(p3);
		
		
		Board b = new Board(v2, v1);
		Run r = new Run();
		r._compType = true;
		AlgoNode n = new AlgoNode(b);
		r.treeBuilding(n, 0);
	}
}
