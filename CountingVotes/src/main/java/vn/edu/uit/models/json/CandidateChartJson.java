package vn.edu.uit.models.json;

public class CandidateChartJson {

	private String label; //Name
	private long y; //Total vote
	
	
	public CandidateChartJson(){
		
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	
	
}
