package vn.edu.uit.models.json;

import java.util.Date;

import vn.edu.uit.models.Voting;

public class VotingJson {

	private long id;
	private String name;
	private int version;

	private String startTime;
	private String endTime;
	
	private long numOfCandidates;
	private int numOfBallots;
	private int numOfValidBallots;
	private long countedBallots;
	
	public VotingJson(Voting voting) {
		this.setId(voting.getId());
		this.setName(voting.getName());
		this.setVersion(voting.getVersion());
		this.setStartTime(voting.getStartTimeString());
		this.setEndTime(voting.getEndTimeString());
	}
	
	public VotingJson(){
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
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

	public long getNumOfCandidates() {
		return numOfCandidates;
	}

	public void setNumOfCandidates(long numOfCandidates) {
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

	public long getCountedBallots() {
		return countedBallots;
	}

	public void setCountedBallots(long countedBallots) {
		this.countedBallots = countedBallots;
	}

	public int getNumOfValidBallots() {
		return numOfValidBallots;
	}

	public void setNumOfValidBallots(int numOfValidBallots) {
		this.numOfValidBallots = numOfValidBallots;
	}


}
