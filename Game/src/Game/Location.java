package Game;

public class Location {
	
	private int _row;
	private int _column;
	
	public Location(int _row, int _column) {
		this._row = _row;
		this._column = _column;
	}

	public int getRow() {
		return _row;
	}

	public void setRow(int _row) {
		this._row = _row;
	}

	public int getColumn() {
		return _column;
	}

	public void setColumn(int _column) {
		this._column = _column;
	}
	
	public String toString(){
		return this._row + "," + this._column;
	}
	
	public boolean equals(Location location){
		return equals(location._row, location._column);
	}
	
	public boolean equals(int row, int column){
		return this._row == row && this._column == column;
	}
	
	public void deepCopy(Location l){
		this._row = l._row;
		this._column = l._column;
	}
	
	/**
	 * checks if the location is between 1,1 to 8,8
	 * @return
	 */
	public boolean isLegalLocation(){
		return this._row < 9 && this._row > 0 && this._column < 9 && this._column > 0;
	}
}
