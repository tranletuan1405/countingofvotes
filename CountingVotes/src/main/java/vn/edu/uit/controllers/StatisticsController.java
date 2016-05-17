package vn.edu.uit.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.unit.UnitService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "statistics/**")
public class StatisticsController {

	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CongressService congressService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private DelegateService delegateService;
	
	@RequestMapping(value = "/")
	public ModelAndView loadStatistics(HttpServletRequest request){
		ModelAndView model = new ModelAndView("statistics_list");
		HttpSession session = request.getSession();
		long congressId  = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Congress congress = congressService.fetch(congressId);
		
		long totalUnit = unitService.getTotalUnit(congressId);
		long attendees = delegateService.getNumOfAttendees(congressId);
		long totalDelegate = delegateService.getTotalDelegate(congressId);
		
		model.addObject("attendees", attendees);
		model.addObject("totalDelegate", totalDelegate);
		model.addObject("totalUnit", totalUnit);
		model.addObject("congress", congress);
		model.addObject(DataConfig.STATISTICS_ACTIVE, DataConfig.STATISTICS_ACTIVE);
		
		return model;
	}
}
