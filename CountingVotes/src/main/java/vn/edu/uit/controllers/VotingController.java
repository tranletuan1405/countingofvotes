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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.VotingJson;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
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
	private ObjectMapper mapper;

	@RequestMapping(value = "/list")
	public ModelAndView loadVotingPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("voting");
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Congress congress = congressService.fetch(id);
		long attendees = delegateService.getNumOfAttendees(id);
		
		model.addObject("attendees", attendees);
		model.addObject("congress", congress);
		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		
		return model;
	}

	// ======================request body==========================
	@RequestMapping(value = "/table")
	@ResponseBody
	public String loadVotingList(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);

		if (id > 0) {
			Congress congress = congressService.fetch(id);
			List<Voting> votings = new ArrayList<Voting>(congress.getVotings());
			List<VotingJson> votingJson = new ArrayList<VotingJson>();
			for (int i = 0; i < votings.size(); i++) {
				votingJson.add(new VotingJson(votings.get(i)));
			}
			ListHolder<VotingJson> json = new ListHolder<VotingJson>();
			json.setData(votingJson);
			return mapper.writeValueAsString(json);
		}

		return "";
	}
	
	@RequestMapping(value = "create_voting", method = RequestMethod.POST)
	@ResponseBody
	public String createVoting(@RequestParam("name") String name, HttpServletRequest request) {

		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Congress congress = congressService.fetch(id);
		
		Voting voting = new Voting();
		voting.setName(name);
		voting.setCongress(congress);
		
		congress.getVotings().add(voting);
		boolean result = congressService.update(congress);

		if (result)
			return "success";
		else
			return "error";
	}

}
