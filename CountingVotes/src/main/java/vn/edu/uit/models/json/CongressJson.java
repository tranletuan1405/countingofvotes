package vn.edu.uit.models.json;

import java.util.Date;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Congress;

public class CongressJson {

	private long id;
	private String name;
	private int totalUnit;
	private int totalDelegate;
	private int totalVoting;
	private String startTime;
	private String endTime;
	private String logo;
	private String banner;
	
	public CongressJson(Congress congress){
		this.setId(congress.getId());
		this.setName(congress.getName());
		this.setTotalUnit(congress.getUnits().size());
		this.setTotalDelegate(congress.getDelegates().size());
		this.setTotalVoting(congress.getVotings().size());
		this.setStartTime(SupportMethods.dateToString(congress.getStartTime(), DataConfig.DATE_TIME_FORMAT));
		this.setEndTime(SupportMethods.dateToString(congress.getEndTime(), DataConfig.DATE_TIME_FORMAT));
		this.setLogo(congress.getLogo());
		this.setBanner(congress.getBanner());
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
}
