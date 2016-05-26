package vn.edu.uit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "sign-in/**")
public class SignInController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignInController.class);
	
	@RequestMapping("/")
	public ModelAndView signIn(){
		ModelAndView model = new ModelAndView("sign_in");
		
		return model;
	}
	
	
	@RequestMapping("/error")
	public ModelAndView signInError(RedirectAttributes reAttr){
		ModelAndView model = new ModelAndView("redirect:/sign-in");
		
		return model;
	}

}
