package vn.edu.uit.models.json;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Congress;

public class CongressJson {

	private BigInteger id;
	private String name;
	private BigInteger totalUnit;
	private BigInteger totalDelegate;
	private BigInteger totalVoting;
	private String startTime;
	private String endTime;

	public CongressJson() {

	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(BigInteger totalUnit) {
		this.totalUnit = totalUnit;
	}

	public BigInteger getTotalDelegate() {
		return totalDelegate;
	}

	public void setTotalDelegate(BigInteger totalDelegate) {
		this.totalDelegate = totalDelegate;
	}

	public BigInteger getTotalVoting() {
		return totalVoting;
	}

	public void setTotalVoting(BigInteger totalVoting) {
		this.totalVoting = totalVoting;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		try {
			this.startTime = SupportMethods.dateToString(new Date(startTime.getTime()), DataConfig.DATE_TIME_FORMAT);
		} catch (Exception e) {

		}
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		try {
			this.endTime = SupportMethods.dateToString(new Date(endTime.getTime()), DataConfig.DATE_TIME_FORMAT);
		} catch (Exception e) {

		}
	}

}
