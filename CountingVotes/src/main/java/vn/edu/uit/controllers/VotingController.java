package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.VotingJson;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.unit.UnitService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "voting/**")
public class VotingController {

	private static final Logger logger = LoggerFactory.getLogger(VotingController.class);

	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CongressService congressService;
	
	@Autowired
	private DelegateService delegateService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UnitService unitService;

	@Autowired
	private ObjectMapper mapper;

	@RequestMapping(value = "/list")
	public ModelAndView loadVotingPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("voting_list");
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Congress congress = congressService.fetch(id);

		long attendees = delegateService.getNumOfAttendees(id);
		long totalDelegate = delegateService.getTotalDelegate(id);
		long totalUnit = unitService.getTotalUnit(id);
		
		model.addObject("attendees", attendees);
		model.addObject("totalDelegate", totalDelegate);
		model.addObject("totalUnit", totalUnit);
		model.addObject("congress", congress);
		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		
		return model;
	}
	
	@RequestMapping(value = "submit_voting", method = RequestMethod.POST)
	public ModelAndView createVoting(@RequestParam(value = "id", required = false) Long id,
			@RequestParam("name") String name, HttpServletRequest request) {

		ModelAndView model = new ModelAndView("redirect:/voting/list");
		if(name == null || name.isEmpty()) return model;
		
		HttpSession session = request.getSession();
		
		if(id != null && id > 0){
			Voting voting = votingService.fetch(id);
			voting.setName(name);
			votingService.update(voting);
		} else {
			long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
			Congress congress = congressService.fetch(congressId);
			Voting voting = new Voting();
			voting.setName(name);
			voting.setCongress(congress);
			votingService.persist(voting);
		}
		
		return model;

	}
	
	
	// ======================request body==========================
	@RequestMapping(value = "/table")
	@ResponseBody
	public String loadVotingList(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);

		List<Voting> votings = votingService.fetchAll(id);
		List<VotingJson> votingJson = new ArrayList<VotingJson>();
		
		for (int i = 0; i < votings.size(); i++) {
			Voting v = votings.get(i);
			VotingJson vj = new VotingJson(v);
			vj.setNumOfCandidates(candidateService.getNumOfCandidates(v.getId()));
			votingJson.add(vj);
		}
		
		ListHolder<VotingJson> json = new ListHolder<VotingJson>();
		json.setData(votingJson);
		return mapper.writeValueAsString(json);
	}

}
