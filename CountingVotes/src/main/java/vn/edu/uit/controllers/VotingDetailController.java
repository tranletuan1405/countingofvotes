package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.CandidateSelectionJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "/voting_detail/**")
public class VotingDetailController {

	private static final Logger logger = LoggerFactory.getLogger(VotingDetailController.class);

	@Autowired
	private CongressService congressService;

	@Autowired
	private VotingService votingService;

	@Autowired
	private DelegateService delegateService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private ObjectMapper mapper;

	// Load Voting Detail
	@RequestMapping(value = "/{id}")
	public ModelAndView loadVotingDetail(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("voting_detail");
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);

		// Load congress
		Congress congress = congressService.fetch(congressId);
		long attendees = delegateService.getNumOfAttendees(congressId);

		Voting voting = votingService.fetch(id);
		session.setAttribute(DataConfig.SESSION_VOTING_NAME, id);

		model.addObject("congress", congress);
		model.addObject("attendees", attendees);
		model.addObject("voting", voting);

		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		return model;
	}

	public ModelAndView submitCountingRules(HttpServletRequest request) {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:voting_detail/" + votingId);

		return model;
	}

	@RequestMapping(value = "/select_candidates", method = RequestMethod.POST)
	public ModelAndView submitSelectedCandidates(
			@RequestParam(value = "candidates", required = true) Long[] delegateIds, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:voting_detail/" + votingId);
		Voting voting = votingService.fetch(votingId);
		
		List<Delegate> oldCandidates = candidateService.getIsCandidate(votingId);
		
		for (int i = 0; i < delegateIds.length; i++) {
			long delegateId = delegateIds[i];

			if (!candidateService.isExists(oldCandidates, delegateId)) {
				Delegate delegate = delegateService.fetch(delegateId);

				Candidate candidate = new Candidate();
				candidate.setDelegate(delegate);
				candidate.setVoting(voting);

				candidateService.persist(candidate);
				
			}
		}
		
		
		
		return model;
	}

	// =========RESPONSE BODY===========
	@RequestMapping(value = "/candidates", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String loadCandidateTable(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		
		List<Candidate> candidates = new ArrayList<Candidate>(candidateService.fetch(votingId));
		List<DelegateJson> candidatesJson = new ArrayList<DelegateJson>();
		for (int i = 0; i < candidates.size(); i++) {
			candidatesJson.add(new DelegateJson(candidates.get(i).getDelegate()));
		}

		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		json.setData(candidatesJson);
		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/get_candidate_selection_list", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String getDelegatesNotCandidate(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);

		// Get Delegates
		List<Delegate> delegates = candidateService.getNotCandidate(votingId);
		List<DelegateJson> delegatesJson = new ArrayList<DelegateJson>();
		for (int i = 0; i < delegates.size(); i++) {
			delegatesJson.add(new DelegateJson(delegates.get(i)));
		}

		// Get Candidates
		List<Delegate> candidates = candidateService.getIsCandidate(votingId);
		List<DelegateJson> candidatesJson = new ArrayList<DelegateJson>();
		for (int i = 0; i < candidates.size(); i++) {
			candidatesJson.add(new DelegateJson(candidates.get(i)));
		}

		CandidateSelectionJson json = new CandidateSelectionJson();
		json.setCandidates(candidatesJson);
		json.setDelegates(delegatesJson);

		return mapper.writeValueAsString(json);
	}
}
