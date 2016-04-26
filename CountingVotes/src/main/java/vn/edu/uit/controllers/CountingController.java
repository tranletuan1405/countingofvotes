package vn.edu.uit.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "counting/**")
public class CountingController {

	@Autowired
	private VotingService votingService;

	@Autowired
	private ObjectMapper mapper;
	
	@RequestMapping(value = "/")
	public ModelAndView loadCountingPage(HttpServletRequest request){
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("counting");
		Voting voting = votingService.fetch(id);
		
		model.addObject("voting", voting);
		
		return model;
	}
}
