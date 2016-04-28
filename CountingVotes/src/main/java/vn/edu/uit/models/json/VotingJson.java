package vn.edu.uit.models.json;

import java.util.Date;

import vn.edu.uit.models.Voting;

public class VotingJson {

	private long id;
	private String name;
	private String version;

	private String startTime;
	private String endTime;
	
	private int numOfCandidates;
	private int numOfBallots;
	
	public VotingJson(Voting voting) {
		this.setId(voting.getId());
		this.setName(voting.getName());
		this.setVersion(voting.getVersion());
		this.setStartTime(voting.getStartTimeString());
		this.setEndTime(voting.getEndTimeString());
	}
	
	

	
	
	
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getNumOfCandidates() {
		return numOfCandidates;
	}

	public void setNumOfCandidates(int numOfCandidates) {
		this.numOfCandidates = numOfCandidates;
	}

	public int getNumOfBallots() {
		return numOfBallots;
	}

	public void setNumOfBallots(int numOfBallots) {
		this.numOfBallots = numOfBallots;
	}










	public long getId() {
		return id;
	}










	public void setId(long id) {
		this.id = id;
	}
}
