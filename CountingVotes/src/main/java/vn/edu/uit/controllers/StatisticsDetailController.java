package vn.edu.uit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.CandidateJson;
import vn.edu.uit.models.service.ballot.BallotService;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.unit.UnitService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "statistics_detail/**")
public class StatisticsDetailController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsDetailController.class);

	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CongressService congressService;
	
	@Autowired
	private DelegateService delegateService;
	
	@Autowired
	private BallotService ballotService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private ObjectMapper mapper;

	@RequestMapping(value = { "/", "/this" })
	public ModelAndView loadStatisticsDetail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("statistics_detail");
		Congress congress = congressService.fetch(congressId);
		Voting voting = votingService.fetch(votingId);
		long attendees = delegateService.getNumOfAttendees(congressId);
		long totalDelegate = delegateService.getTotalDelegate(congressId);
		long totalUnit = unitService.getTotalUnit(congressId);
		long validBallots = ballotService.count(votingId);
		
		model.addObject("validBallots", validBallots);
		model.addObject("totalUnit", totalUnit);
		model.addObject("totalDelegate", totalDelegate);
		model.addObject("attendees", attendees);
		model.addObject("congress", congress);
		model.addObject("voting", voting);
		return model;
	}
	
	
	
	/*==================== RESPONSE BODY =================*/
	@RequestMapping(value = "/candidate_table", produces = { "aplication/json; charset=UTF-8"})
	@ResponseBody
	public String loadCandidateTable(HttpServletRequest request) throws JsonProcessingException{
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		long totalBallot = ballotService.count(votingId);
		
		List<CandidateJson> candidates = candidateService.getStatisticsVoting(votingId, totalBallot);
		ListHolder<CandidateJson> json = new ListHolder<CandidateJson>();
		json.setData(candidates);
		
		return mapper.writeValueAsString(json);
	}
	
	
}
