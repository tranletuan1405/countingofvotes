package vn.edu.uit.controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.models.json.Staff;
import vn.edu.uit.models.json.StaffList;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ObjectMapper mapper;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {		
		ModelAndView model = new ModelAndView("demo");
		
		
		return model;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() throws JsonProcessingException{
		StaffList staffs = new StaffList();
		
		List<Staff> list = new ArrayList<Staff>();
		list.add(new Staff("asdasda", 23, "qweqweq"));
		list.add(new Staff("123", 21, "qweqweq"));
		list.add(new Staff("qwe", 21, "qweqweq"));
		list.add(new Staff("qwe", 21, "qweqweq"));
		list.add(new Staff("asdasda", 23, "qweqweq"));
		list.add(new Staff("ads", 23, "qweqweq"));
		list.add(new Staff("asdasda", 23, "qweqweq"));
		list.add(new Staff("sdf", 22, "qweqweq"));
		list.add(new Staff("ghf", 23, "qweqweq"));
		list.add(new Staff("xcz", 22, "qweqweq"));
		list.add(new Staff("sdf", 23, "qweqweq"));
		list.add(new Staff("123qwe", 22, "qweqweq"));
		list.add(new Staff("cv", 12, "qweqweq"));
		list.add(new Staff("wert", 31, "qweqweq"));
		list.add(new Staff("qwe", 23, "qweqweq"));
		list.add(new Staff("asdaasdsda", 23, "qweqweq"));
		list.add(new Staff("gh", 23, "qweqweq"));
		list.add(new Staff("fgh", 32, "qweqweq"));
		list.add(new Staff("asdqweqasda", 33, "tuan"));
		
		staffs.setData(list);
		
		String data = mapper.writeValueAsString(staffs);
		logger.debug(data);
		return data;

	}
	
}
