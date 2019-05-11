package Algorithm;

import java.util.Vector;

public class AlgoNode {
	
	private AlgoNode _parent;
	private Vector<AlgoNode> _children;
	private Game.Board _board;
	private boolean _compTurn;
	private int _record;
	
//	public AlgoNode(int record, String name, boolean b){ //for tests
//		this._record = record;
//		this.name = name;
//		this._compTurn = b;
//		this._children = new Vector<>();
//	}
	
	public AlgoNode(Game.Board board) { 
		this._board = board;
		this._parent = null;
		this._compTurn = true;
		this._children = new Vector<>();
	}
	
	public AlgoNode(Game.Board board, int record) { 
		this._board = board;
		this._parent = null;
		this._compTurn = true;
		this._children = new Vector<>();
		this._record = record;
	}
	
	public AlgoNode getParent(){
		return this._parent;
	}
	
	public Vector<AlgoNode> getChildren(){
		return this._children;
	}
	
	public AlgoNode getChildAt(int index){
		if(index >= 0 && index < this._children.size())
			return this._children.get(index);
		return null;
	}
	
	public int getRecord(){
		return this._record;
	}
	
	public void setRecord(int newRecord){
		this._record = newRecord;
	}
	
	public boolean isCompTurn(){
		return this._compTurn;
	}
	
	public boolean hasParent(){
		return this._parent != null; 
	}
	
	/**
	 * add the child, and set to the right parent and to the right type of turn
	 * @param childNode
	 */
	public void addChild(AlgoNode childNode){
		this._children.addElement(childNode);
		childNode._parent = this;
		childNode._compTurn = !this._compTurn;
		
	}
	
	public void setParent(AlgoNode parentNode){
		this._parent = parentNode;
	}
	
	public Game.Board getBoard(){
		return this._board;
	}

//	public void print(int depth) {
//		String s;
//		if(this._parent == null)
//			s = "NULL";
//		else 
//			s = this._parent._record + "";
//		System.out.println("___________________________");
//		System.out.println(s + " : " + this._record);
//		this._board.printBoard();
//		System.out.println("___________________________");
//		for(AlgoNode n : this._children)
//			n.print(depth + 1);
//	}
//
//	public int numOfnodes() {
//		int sum = this._children.size();
//		for(AlgoNode n : this._children){
//			sum += n.numOfnodes();
//		}
//		return sum;
//	}
}
