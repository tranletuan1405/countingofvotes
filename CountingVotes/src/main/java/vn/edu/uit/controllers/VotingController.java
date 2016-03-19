package vn.edu.uit.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.edu.uit.extra.DataConfig;

@Controller
@RequestMapping(value = "voting/**")
public class VotingController {

	private static final Logger logger = LoggerFactory.getLogger(VotingController.class);
	
	@RequestMapping(value = "/")
	public ModelAndView loadVotingPage(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		ModelAndView model = new ModelAndView("voting");
		
		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		return model;
	}
	
	@RequestMapping(value = "/votings")
	@ResponseBody
	public String loadVotingList(HttpServletRequest request){
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		
		
		return "";
	}
}
