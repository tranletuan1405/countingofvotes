package vn.edu.uit.models.json;

import java.util.List;

import vn.edu.uit.models.Delegate;

public class CandidateSelectionJson {

	private List<DelegateJson> delegates;
	private List<DelegateJson> candidates;
	
	
	
	public List<DelegateJson> getDelegates() {
		return delegates;
	}
	public void setDelegates(List<DelegateJson> delegates) {
		this.delegates = delegates;
	}
	public List<DelegateJson> getCandidates() {
		return candidates;
	}
	public void setCandidates(List<DelegateJson> candidates) {
		this.candidates = candidates;
	}
	
	
}
