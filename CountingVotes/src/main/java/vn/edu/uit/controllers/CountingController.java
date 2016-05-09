package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.extra.TripleDes;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.CandidateJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.ballot.BallotService;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "counting/**")
public class CountingController {

	private static final Logger logger = LoggerFactory.getLogger(CountingController.class);
	
	@Autowired
	private CongressService congressService;
	
	@Autowired
	private VotingService votingService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private DelegateService delegateService;
	
	@Autowired
	private BallotService ballotService;

	@Autowired
	private ObjectMapper mapper;
	
	@RequestMapping(value = { "/", "this" })
	public ModelAndView loadCountingPage(HttpServletRequest request){
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		Set<Long> candidates = new HashSet<Long>();
		session.setAttribute(DataConfig.SESSION_SELECTED_CANDIDATES, candidates);
		
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submit_ballot", method = RequestMethod.POST)
	public ModelAndView submitBallot(HttpServletRequest request, RedirectAttributes reAttr){
		ModelAndView model = new ModelAndView("redirect:/counting/this");
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		Set<Long> candidates = (HashSet<Long>) session.getAttribute(DataConfig.SESSION_SELECTED_CANDIDATES);
		Voting voting = votingService.fetch(votingId);
		CountingRule rule = voting.getCountingRule();
		long attendees = delegateService.getNumOfAttendees(congressId);
		long count = ballotService.count(votingId);
		
		if(count == attendees){
			reAttr.addFlashAttribute("serverError", "Tổng số phiếu đã lưu bằng với tổng số phiếu hợp lệ, vui lòng kiểm tra lại!");
			return model;
		}
		
		if(candidates == null || candidates.size() < rule.getMinSelected() || candidates.size() > rule.getMaxSelected()){
			int size = candidates == null ? 0 : candidates.size();
			reAttr.addFlashAttribute("serverError", "Số lượng bầu chọn của phiếu vừa nhập không hợp lệ vui lòng kiểm tra lại, số lượng : " + size);
			return model;
		}
	
		long ballotId = ballotService.submitBallot(voting, candidates);
		if(ballotId > 0){
			reAttr.addFlashAttribute("serverSuccess", "Lưu phiếu thành công, mã phiếu : ");
			reAttr.addFlashAttribute("preBallotId", ballotId);
		}
		else {
			reAttr.addFlashAttribute("serverError", "Lưu phiếu thất bại, vui lòng tách riêng phiếu vừa kiểm để kiểm tra lại sau");
		}
		
		return model;
	}
	
	//==================RESPONSE BODY===================
	@RequestMapping(value = "/get_candidates", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String getCandidateTable(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		boolean isResidual = votingService.getCountingType(votingId);
		
		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
		List<CandidateJson> data = new ArrayList<CandidateJson>();
		ListHolder<CandidateJson> json = new ListHolder<CandidateJson>();

		for (int i = 0; i < candidates.size(); i++) {
			data.add(new CandidateJson(candidates.get(i), isResidual));
		}

		json.setData(data);
		return mapper.writeValueAsString(json);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/select_candidate", method = RequestMethod.POST)
	@ResponseBody
	public String selectCandidate(@RequestParam(value ="encode", required = true) String encode, HttpServletRequest request) throws NumberFormatException, Exception{
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Set<Long> candidates = (HashSet<Long>) session.getAttribute(DataConfig.SESSION_SELECTED_CANDIDATES);
		if(candidates == null) candidates = new HashSet<Long>();
		
		Congress congress = congressService.fetch(congressId);
		TripleDes tDes = new TripleDes(congress.getCongressKey(), congress.getCongressIv());
		long delegateId = Long.valueOf(tDes.decryptText(encode));
		
		long candidateId = candidateService.isExists(delegateId, votingId);
		if (candidateId > 0) {
			candidates.add(candidateId);
			session.setAttribute(DataConfig.SESSION_SELECTED_CANDIDATES, candidates);
		}
		
		return candidateId > 0? String.valueOf(delegateId) : "failed";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deselect_candidate", method = RequestMethod.POST)
	@ResponseBody
	public String deselectCandidate(@RequestParam(value = "encode", required = true) String encode, HttpServletRequest request) throws NumberFormatException, Exception {
		HttpSession session = request.getSession();
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Set<Long> candidates = (HashSet<Long>) session.getAttribute(DataConfig.SESSION_SELECTED_CANDIDATES);
		if(candidates == null) candidates = new HashSet<Long>();
		
		Congress congress = congressService.fetch(congressId);
		TripleDes tDes = new TripleDes(congress.getCongressKey(), congress.getCongressIv());
		long delegateId = Long.valueOf(tDes.decryptText(encode));
		
		long candidateId = candidateService.isExists(delegateId, votingId);
		if (candidateId > 0) {
			candidates.remove(candidateId);
			session.setAttribute(DataConfig.SESSION_SELECTED_CANDIDATES, candidates);
		}
		
		return candidateId > 0 ? String.valueOf(delegateId) : "failed";
	}
	
}
