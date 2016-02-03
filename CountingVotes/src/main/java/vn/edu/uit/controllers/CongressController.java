package vn.edu.uit.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.datatable.CongressDatatable;
import vn.edu.uit.models.service.congress.CongressDao;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;

@Controller
public class CongressController {

	private static final Logger logger = LoggerFactory.getLogger(CongressController.class);

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CongressService congressService;

	@Autowired
	private DelegateService delegateService;

	@RequestMapping(value = "/")
	public ModelAndView congress() {
		ModelAndView model = new ModelAndView("congress");
		/*String filePath = "C:\\Users\\tuantran\\Desktop\\test.docx";
		List<Delegate> delegates = delegateService.getByDocument(filePath);*/
		return model;
	}

	@RequestMapping(value = "/congress_table", method = RequestMethod.GET)
	@ResponseBody
	public String congressList() throws JsonProcessingException {

		//List<Congress> congressList = congressService.fetch(0,0);
		//CongressDatatable data = new CongressDatatable(congressList);
		String filePath = "C:\\Users\\tuantran\\Desktop\\test.docx";
		List<Delegate> delegates = delegateService.getByDocument(filePath);
		
		String json = mapper.writeValueAsString(delegates);
		return json;
	
	}
}
