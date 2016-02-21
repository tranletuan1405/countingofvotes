package vn.edu.uit.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.congress.CongressService;

@Controller
@RequestMapping(value = "detail/**")
public class CongressDetailController {

	private static final Logger logger = LoggerFactory.getLogger(CongressDetailController.class);
	
	@Autowired
	private CongressService congressService;
	
	@Autowired
	private ObjectMapper mapper;
	
	@RequestMapping(value = "/{id}")
	public ModelAndView congressDetail(
			@PathVariable("id") long id,
			HttpServletRequest request){
		ModelAndView model = new ModelAndView("congress_detail");
		HttpSession session = request.getSession();
		session.setAttribute("congress_id", id);
		Congress congress = congressService.fetch(id);
		
		model.addObject("congress", congress);

		return model;
	}
	
	@RequestMapping(value = "/delegates")
	@ResponseBody
	public String delegates(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute("congress_id");
		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		List<Delegate> delegates = new ArrayList<Delegate>(congressService.fetch(id).getDelegates());
		
		for (int i = 0; i < delegates.size(); i++) {
			json.getData().add(new DelegateJson(delegates.get(i)));
		}
		
		return mapper.writeValueAsString(json);
	}
	
	@RequestMapping(value = "/units")
	@ResponseBody
	public String units(HttpServletRequest request) {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute("congress_id");
		
		
		return "";
	}
}
