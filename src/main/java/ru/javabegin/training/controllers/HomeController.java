package ru.javabegin.training.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javabegin.training.services.ProcessInterface;


import java.security.Principal;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private AccessDecisionManager accessDecisionManager;

	@Autowired
	private ProcessInterface process;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied(Principal user){

		ModelAndView model = new ModelAndView();

		if(user!= null){
			model.addObject("errorMsg", user.getName() + " у вас нет доступа к этой странице!");
		}else{
			model.addObject("errorMsg", "У вас нет доступа к этой странице!");
		}

		model.setViewName("/content/accessDenied");
		return model;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String mainPage() {

//		System.out.println(accessDecisionManager);


		logger.info(process.getMessage());
		System.out.println(process.getMessage());

		printUserDetails();

		return "/content/user";

	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage() {

		return "/content/admin";

	}

	private void printUserDetails(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info("password = " + userDetails.getPassword());
		logger.info("username = " + userDetails.getUsername());

		System.out.println("password = " + userDetails.getPassword());
		System.out.println("username = " + userDetails.getUsername());

		for(GrantedAuthority auth : userDetails.getAuthorities()){
			logger.info(auth.getAuthority());

			System.out.println(auth.getAuthority());
		}
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username or password!");
		}

		model.setViewName("login");

		return model;

	}

}
