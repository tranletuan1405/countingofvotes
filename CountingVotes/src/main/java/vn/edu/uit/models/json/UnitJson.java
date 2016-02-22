package vn.edu.uit.models.json;

import vn.edu.uit.models.Unit;

public class UnitJson {

	private long id;
	private String code;
	private String name;
	private String shortName;
	public long numOfDBDN = 0;
	public long numOfDBCD = 0;
	public long numOfDBBC = 0;
	public long numOfDBDK = 0;
	public long total = 0;
	
	public UnitJson() {
		
	}
	
	public UnitJson(Unit unit){
		this.setId(unit.getId());
		this.setCode(unit.getCode());
		this.setName(unit.getName());
		this.setShortName(unit.getShortName());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNumOfDBDN() {
		return numOfDBDN;
	}
	public void setNumOfDBDN(long numOfDBDN) {
		this.numOfDBDN = numOfDBDN;
	}
	public long getNumOfDBCD() {
		return numOfDBCD;
	}
	public void setNumOfDBCD(long numOfDBCD) {
		this.numOfDBCD = numOfDBCD;
	}
	public long getNumOfDBBC() {
		return numOfDBBC;
	}
	public void setNumOfDBBC(long numOfDBBC) {
		this.numOfDBBC = numOfDBBC;
	}
	public long getNumOfDBDK() {
		return numOfDBDK;
	}
	public void setNumOfDBDK(long numOfDBDK) {
		this.numOfDBDK = numOfDBDK;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
}
