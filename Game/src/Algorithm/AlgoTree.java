package Algorithm;

public class AlgoTree { //the leaf are compTurn (Maximal nodes)
	
	private AlgoNode _root;
	private int _hight;
	
	private final int _MAX = 777;
	private final int _MIN = -777;
	
	public AlgoTree(AlgoNode root, int hight){
		this._root = root;
		this._hight = hight;
	}
	
	public int getHight(){
		return this._hight;
	}
	
	public AlgoNode getRoot(){
		return this._root;
	}
	
	/**
	 * return the index of the root's child - the index of the best move to do
	 * @return
	 */
	public Game.Board getBestMove(){
		getIt(this._root, this._MIN, this._MAX);
		for(AlgoNode n : this._root.getChildren())
			if(this._root.getRecord() == n.getRecord())
				return n.getBoard();			
		return null;
	}	
	
	private void getIt(AlgoNode node, int min, int max){
		int value = 0;
		int tmpValue = 0;
		if(node.getChildren().isEmpty())
			return;
		if(node.isCompTurn()){
			value = min;
			for(AlgoNode n : node.getChildren()){
				getIt(n, value, max);
				tmpValue = n.getRecord();
				if(tmpValue > value)
					value = tmpValue;
				if(value >= max){
					node.setRecord(max);
					return;
				}
			}
		}
		else{
			value = max;
			for(AlgoNode n : node.getChildren()){
				getIt(n, min, value);
				tmpValue = n.getRecord();
				if(tmpValue < value)
					value = tmpValue;
				if(value <= min){
					node.setRecord(min);
					return;
				}
			}
		}
		node.setRecord(value);
	}
}