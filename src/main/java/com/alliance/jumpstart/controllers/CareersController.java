package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.repository.CareersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CareersController {

    private CareersRepository repository;

    @Autowired
    public CareersController(CareersRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping(value="/admindashboard")
    public String adminDashboard(Model model) {
    	return "dashboard/admindashboard";
    }
    
    @GetMapping(value="/advertisement")
    public String advertisement(Model model) {
    	return "dashboard/advertisement";
    }
    
    @GetMapping(value="/reportanalytics")
    public String reportAnalytics(Model model) {
    	return "dashboard/reportanalytics";
    }
    
    @GetMapping(value="/resumebank")
    public String resumeBank(Model model) {
    	return "dashboard/resumebank";
    }
    
    
    @GetMapping(value="/login")
    public String login(Model model) {
    	return "dashboard/login";
    }

    @GetMapping(value="/register")
    public String goToRegister(Model model) {
    	return "dashboard/register";
    }
    
    @GetMapping(value="/backtologin")
    public String backToLogin(Model model) {
    	return "dashboard/login";
    }
    
    @GetMapping(value="/")
    public String index(Model model) {
    	return "index";
    }

    @RequestMapping(value = "/careers", method = RequestMethod.GET)
    public String careers(Model model) {
        Iterable<Career> careers = repository.findAll();
        model.addAttribute("careers", careers);
        return "careers/index";
    }

    @RequestMapping(value ="/careers/applyNow", method = RequestMethod.GET)
    public String applyNow(@RequestParam(value="id") long id, Model model) {
        Career c= repository.findById(Long.valueOf(id))
        .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));
            
        model.addAttribute("career", c);
        return "careers/application";
    }

}