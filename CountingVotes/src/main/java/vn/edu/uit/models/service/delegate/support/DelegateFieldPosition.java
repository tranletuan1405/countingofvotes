package vn.edu.uit.models.service.delegate.support;

public class DelegateFieldPosition {

	private int column;
	private int cell;
	
	public DelegateFieldPosition(int column, int cell) {
		super();
		this.column = column;
		this.cell = cell;
	}
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getCell() {
		return cell;
	}
	public void setCell(int cell) {
		this.cell = cell;
	}
	
	
}
