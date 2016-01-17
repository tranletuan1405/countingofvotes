package vn.edu.uit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CongressController {

	@RequestMapping(value = "/")
	public ModelAndView congress(){
		ModelAndView model = new ModelAndView("congress");
		return model;
	}
	
	
}
