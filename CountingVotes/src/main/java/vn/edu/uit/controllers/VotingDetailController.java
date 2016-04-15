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

import vn.edu.uit.extra.BarcodeGenerator;
import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.extra.TripleDes;
import vn.edu.uit.models.Barcode;
import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.CandidateJson;
import vn.edu.uit.models.json.CandidateSelectionJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.barcode.BarcodeService;
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
	private BarcodeService barcodeService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private BarcodeGenerator barcodeGenerator;

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

		// Create counting rule if the voting has candidates
		if (voting.getCandidates().size() > 0 && voting.getCountingRule() == null) {
			CountingRule rule = new CountingRule();
			rule.setMaxSelected(voting.getCandidates().size());
			voting.setCountingRule(rule);

			votingService.persistCountingRule(rule);
			votingService.merge(voting);
		}

		// Check max-selected
		if (voting.getCountingRule() != null
				&& voting.getCountingRule().getMaxSelected() > voting.getCandidates().size()) {
			voting.getCountingRule().setMaxSelected(voting.getCandidates().size());
			votingService.merge(voting);
		}

		model.addObject("congress", congress);
		model.addObject("attendees", attendees);
		model.addObject("voting", voting);

		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		return model;
	}

	@RequestMapping(value = "/update_rules", method = RequestMethod.POST)
	public ModelAndView updateCountingRules(@RequestParam("minPercent") int minPercent,
			@RequestParam("maxSelected") int maxSelected, HttpServletRequest request) {
		HttpSession session = request.getSession();

		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:/voting_detail/" + votingId);

		CountingRule rule = votingService.fetchRule(votingId);
		rule.setMaxSelected(maxSelected);
		rule.setMinPercent(minPercent);
		votingService.updateCountingRule(rule);

		return model;
	}

	@RequestMapping(value = "/select_candidates", method = RequestMethod.POST)
	public ModelAndView submitSelectedCandidates(
			@RequestParam(value = "candidates", required = false) Long[] delegateIds, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:/voting_detail/" + votingId);
		if (delegateIds == null)
			return model;
		Voting voting = votingService.fetch(votingId);

		List<Delegate> candidates = candidateService.getIsCandidate(votingId);
		List<Long> newCandidateIds = new ArrayList<Long>();

		for (int i = 0; i < delegateIds.length; i++) {
			long delegateId = delegateIds[i];
			newCandidateIds.add(delegateId);

			// insert new candidate
			if (!candidateService.isExists(candidates, delegateId)) {
				Delegate delegate = delegateService.fetch(delegateId);
				candidates.add(delegate);

				Candidate candidate = new Candidate();
				candidate.setDelegate(delegate);
				candidate.setVoting(voting);

				candidateService.persist(candidate);
			}
		}

		// delete unselected candidate
		for (int i = 0; i < candidates.size(); i++) {
			Delegate candidate = candidates.get(i);
			if (!newCandidateIds.contains(candidate.getId())) {
				candidateService.delete(candidate.getId());
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
		List<CandidateJson> candidatesJson = new ArrayList<CandidateJson>();
		for (int i = 0; i < candidates.size(); i++) {
			candidatesJson.add(new CandidateJson(candidates.get(i)));
		}

		ListHolder<CandidateJson> json = new ListHolder<CandidateJson>();
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

	@RequestMapping(value = "/create_ballot", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String createCandidateCodes(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);

		String congressPath = congressService.getCongressPath(congressId);
		
		List<Candidate> candidates = candidateService.fetch(votingId);
		List<CandidateJson> candidatesJson = new ArrayList<CandidateJson>();

		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidate = candidates.get(i);
			if (candidate.getCountingBarcode() == null || candidate.getCountingBarcode().getImagePath().isEmpty()) {
				
				Barcode delegateCode = candidate.getDelegate().getHashCode();
				Barcode countingCode = new Barcode();
				String content = delegateCode.getContent();
				String encode = delegateCode.getEncode();
				String imagePath = barcodeGenerator.generateAztec(congressPath, SupportMethods.getUID(), encode, 200);

				countingCode.setContent(content);
				countingCode.setEncode(encode);
				countingCode.setImagePath(imagePath);
				
				barcodeService.persist(countingCode);
				candidate.setCountingBarcode(countingCode);
				candidateService.merge(candidate);
			}
			
			candidatesJson.add(new CandidateJson(candidate));
		}

		ListHolder<CandidateJson> json = new ListHolder<CandidateJson>();
		json.setData(candidatesJson);
		
		return mapper.writeValueAsString(json);
	}
}
