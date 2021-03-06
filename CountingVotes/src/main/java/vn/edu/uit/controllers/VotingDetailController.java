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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import vn.edu.uit.models.Parameter;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.json.CandidateJson;
import vn.edu.uit.models.json.CandidateSelectionJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.barcode.BarcodeService;
import vn.edu.uit.models.service.candidate.CandidateService;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.parameter.ParamService;
import vn.edu.uit.models.service.unit.UnitService;
import vn.edu.uit.models.service.voting.VotingService;

@Controller
@RequestMapping(value = "voting_detail/**")
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
	private UnitService unitService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private BarcodeGenerator barcodeGenerator;
	
	@Autowired
	private ParamService paramService;

	// Load Voting Detail
	@RequestMapping(value = "/{id}")
	public ModelAndView loadVotingDetail(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("voting_detail");
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);

		// Load congress
		Congress congress = congressService.fetch(congressId);
		
		long attendees = delegateService.getNumOfAttendees(congressId);
		long totalDelegate = delegateService.getTotalDelegate(congressId);
		long totalUnit = unitService.getTotalUnit(congressId);
		long totalCandidate = candidateService.getTotalCandidate(id);
	
		Voting voting = votingService.fetch(id);
		session.setAttribute(DataConfig.SESSION_VOTING_NAME, id);

		// Create counting rule if the voting has candidates
		if (totalCandidate > 0 && voting.getCountingRule() == null) {
			CountingRule rule = new CountingRule();
			rule.setMaxSelected((int)totalCandidate);
			voting.setCountingRule(rule);

			votingService.persistCountingRule(rule);
			votingService.merge(voting);
		}

		// Check max-selected
		if (voting.getCountingRule() != null
				&& voting.getCountingRule().getMaxSelected() > totalCandidate) {
			voting.getCountingRule().setMaxSelected((int)totalCandidate);
			votingService.merge(voting);
		}
		
		model.addObject("attendees", attendees);
		model.addObject("totalDelegate", totalDelegate);
		model.addObject("totalUnit", totalUnit);
		model.addObject("totalCandidate", totalCandidate);
		model.addObject("congress", congress);
		model.addObject("voting", voting);

		model.addObject(DataConfig.VOTING_ACTIVE, DataConfig.VOTING_ACTIVE);
		return model;
	}

	@RequestMapping(value = "/update_rules", method = RequestMethod.POST)
	public ModelAndView updateCountingRules(@RequestParam("minPercent") int minPercent,
			@RequestParam("minSelected") int minSelected, @RequestParam("maxSelected") int maxSelected,
			@RequestParam("isResidual") boolean isResidual, HttpServletRequest request) {
		HttpSession session = request.getSession();

		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:/voting_detail/" + votingId);

		CountingRule rule = votingService.fetchRule(votingId);
		rule.setMaxSelected(maxSelected);
		rule.setMinPercent(minPercent);
		rule.setMinSelected(minSelected);
		rule.setResidual(isResidual);
		votingService.updateCountingRule(rule);

		return model;
	}

	@RequestMapping(value = "/select_candidates", method = RequestMethod.POST)
	public ModelAndView submitSelectedCandidates(
			@RequestParam(value = "candidates", required = false) Long[] delegateIds, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:/voting_detail/" + votingId);
		if (delegateIds == null)
			return model;
		Voting voting = votingService.fetch(votingId);

		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
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
	
	@RequestMapping(value = "update_num_of_ballots", method = RequestMethod.POST)
	public ModelAndView saveBallotInfo(@RequestParam("totalBallot") int totalBallot,
			@RequestParam("numOfValidBallot") int numOfValidBallot, HttpServletRequest request, RedirectAttributes reAttr) {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		ModelAndView model = new ModelAndView("redirect:/voting_detail/" + votingId);
		
		Voting voting = votingService.fetch(votingId);
		long attendees = delegateService.getNumOfAttendees(congressId);
		
		if(totalBallot <= 0 || numOfValidBallot <= 0){
			reAttr.addFlashAttribute("serverError", "Số phiếu phải lớn hơn 0");
			return model;
		}
		
		if(totalBallot < numOfValidBallot){
			reAttr.addFlashAttribute("serverError", "Số phiếu hợp lệ vừa nhập lớn hơn số phiếu thu vào");
			return model;
		}
		
		if(totalBallot > attendees){
			reAttr.addFlashAttribute("serverError", "Số phiếu thu vào lớn hơn số đại biểu tham dự");
			return model;
		}
		
		voting.setTotalBallot(totalBallot);
		voting.setNumOfValidBallot(numOfValidBallot);
		votingService.update(voting);
		
		return model;
	}

	// =========RESPONSE BODY===========
	@RequestMapping(value = "/candidates", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String loadCandidateTable(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		
		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
		List<DelegateJson> candidateJson = new ArrayList<DelegateJson>();
		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		
		for(int i = 0; i < candidates.size(); i ++){
			candidateJson.add(new DelegateJson(candidates.get(i)));
		}
		
		json.setData(candidateJson);
		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/get_candidate_selection_list", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String getDelegatesNotCandidate(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);

		// Get Delegates
		List<Delegate> delegates = candidateService.getNotCandidate(votingId, congressId);
		List<DelegateJson> delegatesJson = new ArrayList<DelegateJson>();
		for (int i = 0; i < delegates.size(); i++) {
			delegatesJson.add(new DelegateJson(delegates.get(i)));
		}

		// Get Candidates
		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
		List<DelegateJson> candidatesJson = new ArrayList<DelegateJson>();
		for (int i = 0; i < candidates.size(); i++) {
			candidatesJson.add(new DelegateJson(candidates.get(i)));
		}

		CandidateSelectionJson json = new CandidateSelectionJson();
		json.setCandidates(candidatesJson);
		json.setDelegates(delegatesJson);

		return mapper.writeValueAsString(json);
	}
	
	
	@RequestMapping(value = "/get_create_ballot_modal", produces = {"application/text; charset=UTF-8" })
	@ResponseBody
	public ModelAndView getCreateBallotModal() {
		ModelAndView model = new ModelAndView("fragment/create_ballot");
		return model;
	}
	
	@RequestMapping(value = "/submit_code_path")
	@ResponseBody
	public String getSubmitPath() {
	
		Parameter submitCode = paramService.fetch(DataConfig.SUBMIT_CODE_PATH);
		if (submitCode == null || submitCode.getValue().isEmpty()) {
			String path = barcodeGenerator.generateQR(DataConfig.COMMON_DIRECTORY, "submit_code", "submit", 320);
			if (path == null || path.isEmpty()) {
				path = barcodeGenerator.generateQR(DataConfig.COMMON_DIRECTORY, "submit_code", "submit", 320);
			}

			if (submitCode == null)
				submitCode = new Parameter();
			submitCode.setName(DataConfig.SUBMIT_CODE_PATH);
			submitCode.setValue(path);
			paramService.saveOrUpdate(submitCode);
		}
		
		return submitCode.getValue();
	}

	@RequestMapping(value = "/create_codes", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public String createCandidateCodes(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		Congress congress = congressService.fetch(congressId);
		boolean isResidual = votingService.getCountingType(votingId);
		
		String congressPath = congress.getCongressPath();
		String congressKey = congress.getCongressKey();
		String congressIv = congress.getCongressIv();
		TripleDes tDes = new TripleDes(congressKey, congressIv);
		
		List<Delegate> candidates = candidateService.getIsCandidate(votingId, congressId);
		List<CandidateJson> candidatesJson = new ArrayList<CandidateJson>();

		for (int i = 0; i < candidates.size(); i++) {
			Delegate candidate = candidates.get(i);
			if (candidate.getCountingCode() == null || candidate.getCountingCode().getImagePath().isEmpty()) {
				
				Barcode countingCode = new Barcode();
				String content = String.valueOf(candidate.getId());
				String encode = tDes.encryptText(content);
				String imagePath = barcodeGenerator.generateAztec(congressPath, SupportMethods.getUID(), encode, 320);
				
				if(imagePath == null || imagePath.isEmpty()){
					imagePath = barcodeGenerator.generateAztec(congressPath, SupportMethods.getUID(), encode, 320);
				}

				countingCode.setContent(content);
				countingCode.setEncode(encode);
				countingCode.setImagePath(imagePath);
				
				barcodeService.persist(countingCode);
				candidate.setCountingCode(countingCode);
				delegateService.merge(candidate);
			}
			
			candidatesJson.add(new CandidateJson(candidate, isResidual));
		}

		ListHolder<CandidateJson> json = new ListHolder<CandidateJson>();
		json.setData(candidatesJson);
		
		return mapper.writeValueAsString(json);
	}
	
	
	@RequestMapping(value = "save_pattern", method = RequestMethod.POST, produces = {"application/text; charset=UTF-8" })
	@ResponseBody
	public String saveBallotPattern(@RequestParam(value = "pattern", required = true) String pattern,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		Long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);

		if(pattern.isEmpty()) return "Failed";
		
		boolean execute = votingService.savePattern(votingId, pattern);
		
		if(execute){
			return pattern;
		}

		return "Failed";
	}
	
	@RequestMapping(value = "get_pattern", method = RequestMethod.GET, produces = {"application/text; charset=UTF-8" })
	@ResponseBody
	public String getBallotPattern(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		Long votingId = (Long) session.getAttribute(DataConfig.SESSION_VOTING_NAME);
		
		String pattern = votingService.getPattern(votingId);
		
		if(pattern != null && !pattern.isEmpty()){
			return pattern;
		}
		
		return "Failed";
	}
}
