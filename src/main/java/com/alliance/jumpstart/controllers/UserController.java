package com.alliance.jumpstart.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alliance.jumpstart.repository.CareersRepository;

import com.alliance.jumpstart.entities.Career;


import com.alliance.jumpstart.entities.User;

import com.alliance.jumpstart.services.UserService;
import com.alliance.jumpstart.utils.PassEncoding;
import com.alliance.jumpstart.utils.Roles;
import com.alliance.jumpstart.utils.Status;

import com.alliance.jumpstart.entities.JobHiring;

import com.alliance.jumpstart.services.JobHiringService;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    CareersRepository repository;
    
    @Autowired
    GlobalController globalController;

    @Autowired
    JobHiringService taskService;

    @Autowired
    UserService userService;
    
  

    /*@RequestMapping("/")
    public String root(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("root");
        return "login";
    }*/
    
   

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("login");
        return "login";
    }
    
    
    
    

	 /*@GetMapping(value="/")
	    public String index(Model model) {
	    	return "applicant/index";
	    }
	    
	 @RequestMapping(value = "/careers ")
	    public String careers(Model model) {
	       
		 
	        return "applicant/careers";
	    }*/
	
    


   /* @RequestMapping("/home")
    public String home(Model model) {
        Task task =new Task();
        model.addAttribute("reqTask", task);
        model.addAttribute("allTask", taskService.findByUserIdStatus(globalController.getLoginUser().getId(), Status.ACTIVE.getValue()));
        model.addAttribute("allPassiveTask", taskService.findByUserIdStatus(globalController.getLoginUser().getId(), Status.PASSIVE.getValue()));
        logger.info("home");
        return "home";
    }*/
    
    @RequestMapping("/advertisement")
    public String home(Model model) {
        JobHiring task =new JobHiring();
        model.addAttribute("reqTask", task);
        model.addAttribute("allJob", taskService.findAll());
        model.addAttribute("allPassiveTask", taskService.findByUserIdStatus(globalController.getLoginUser().getId(), Status.PASSIVE.getValue()));
        logger.info("advertisement");
        return "advertisement";
    }


    @RequestMapping("/admin")
    public String helloAdmin() {
        logger.info("admin");
        return "admin";
    }
    
    @GetMapping(value = "/login")
    public String logins(Model model) {
        return "dashboard/login";
    }


    @GetMapping(value = "/")
    public String index(Model model) {
        return "careers/index";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("reqUser", new User());
        System.out.print("user name: ");
        logger.info("register");
        return "dashboard/register";
    }
    
    

    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("reqUser") User reqUser, @RequestParam("password")String password,@RequestParam("password_2")String password_2, 
                           final RedirectAttributes redirectAttributes) {

        logger.info("/user/register");
        User user = userService.findByUserName(reqUser.getUsername());
        
        if (!password.equals(password_2)) {
            redirectAttributes.addFlashAttribute("saveUser", "unmatched password");
            return "redirect:/register";
        }
        
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-name");
            return "redirect:/register";
        }
        user = userService.findByEmail(reqUser.getEmail());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        reqUser.setRole(Roles.ROLE_USER.getValue());

        if (userService.save(reqUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }
    
    	


}
