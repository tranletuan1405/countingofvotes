package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "counting/**")
public class CountingController {

	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private DelegateService delegateService;

	@Autowired
	private ObjectMapper mapper;
	
	@RequestMapping(value = "/")
	public ModelAndView loadCountingPage(HttpServletRequest request){
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		Voting voting = votingService.fetch(votingId);
		ModelAndView model = new ModelAndView("counting");
		
		long totalCandidate = candidateService.getTotalCandidate(votingId);
		long attendees = delegateService.getNumOfAttendees(congressId);
		long totalDelegate = delegateService.getTotalDelegate(congressId);
		
		//Redirect if candidate list is empty
		if(totalCandidate <= 0) {
			model.setViewName("redirect:/voting_detail/" + votingId);
			return model;
		}
		
		model.addObject("attendees", attendees);
		model.addObject("totalCandidate", totalCandidate);
		model.addObject("totalDelegate", totalDelegate);
		model.addObject("voting", voting);
		
		return model;
	}
	
	
	//==================RESPONSE BODY===================
	@RequestMapping(value = "/ballot_canidates", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String getCandidateTable(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
		List<DelegateJson> data = new ArrayList<DelegateJson>();
		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();

		for (int i = 0; i < candidates.size(); i++) {
			data.add(new DelegateJson(candidates.get(i)));
		}

		json.setData(data);
		return mapper.writeValueAsString(json);

	}
}
