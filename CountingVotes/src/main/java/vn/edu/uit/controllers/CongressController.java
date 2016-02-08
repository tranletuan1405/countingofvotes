package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.json.CongressJson;
import vn.edu.uit.models.service.congress.CongressService;

@Controller
public class CongressController {

	private static final Logger logger = LoggerFactory.getLogger(CongressController.class);
	public CongressController(){
		logger.info("CongressController");
	}
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CongressService congressService;

	@RequestMapping(value = "/")
	public ModelAndView congress() {
		ModelAndView model = new ModelAndView("congress");
		
		return model;
	}

	@RequestMapping(value = "/congress_table", method = RequestMethod.GET)
	@ResponseBody
	public String congressList() throws JsonProcessingException {

		ListHolder<CongressJson> json = new ListHolder<CongressJson>();
		json.setData(congressService.fetchJson());

		return mapper.writeValueAsString(json);
	}
	
	@RequestMapping(value = "/checking_delegates_file", method = RequestMethod.POST)
	@ResponseBody
	public String checkingDelegatesFile(
			@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			HttpServletRequest request) throws JsonProcessingException{
		
		HttpSession session = request.getSession();
		session.setAttribute("delegatesFile", delegatesFile);
		
		return "success";
	}
	
	@RequestMapping(value = "/delegates_table")
	@ResponseBody
	public String delegatesTable(HttpServletRequest request) throws JsonProcessingException {
		
		HttpSession session = request.getSession();
		MultipartFile file = (MultipartFile) session.getAttribute("delegatesFile");
		if(file == null) return "";
		
		Delegate delegate = new Delegate();
		List<Delegate> list = new ArrayList<Delegate>();
		delegate.setOrdinal("1");
		delegate.setName(file.getOriginalFilename());
		Unit unit = new Unit();
		unit.setShortName("CNPM");
		delegate.setUnit(unit);
		DelegateType type = new DelegateType();
		type.setShortName("ÐBÐN");
		delegate.setType(type);
		
		list.add(delegate);
		
		ListHolder<Delegate> json = new ListHolder<Delegate>();
		json.setData(list);
		
		
		
		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/create_congress", method = RequestMethod.POST)
	public ModelAndView createCongress(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
			@RequestParam(value = "bannerFile", required = false) MultipartFile bannerFile) {
		
		ModelAndView model = new ModelAndView("redirect:/");

		return model;
	}
	
	
}
