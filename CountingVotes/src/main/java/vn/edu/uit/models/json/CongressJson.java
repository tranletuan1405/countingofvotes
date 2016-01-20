package vn.edu.uit.models.json;

import java.util.Date;

import vn.edu.uit.models.Congress;

public class CongressJson {

	private long id;
	private String name;
	private int totalUnit;
	private int totalDelegate;
	private int totalVoting;
	private Date startTime;
	private Date endTime;
	
	public CongressJson(Congress congress){
		this.setId(congress.getId());
		this.setName(congress.getName());
		this.setTotalUnit(congress.getUnits().size());
		this.setTotalDelegate(congress.getDelegates().size());
		this.setTotalVoting(congress.getVotings().size());
		this.setStartTime(congress.getStartTime());
		this.setEndTime(congress.getEndTime());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(int totalUnit) {
		this.totalUnit = totalUnit;
	}

	public int getTotalDelegate() {
		return totalDelegate;
	}

	public void setTotalDelegate(int totalDelegate) {
		this.totalDelegate = totalDelegate;
	}

	public int getTotalVoting() {
		return totalVoting;
	}

	public void setTotalVoting(int totalVoting) {
		this.totalVoting = totalVoting;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
