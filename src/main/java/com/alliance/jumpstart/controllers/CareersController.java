package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.repository.CareersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CareersController {

    private CareersRepository repository;

    @Autowired
    public CareersController(CareersRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/careers")
    public String index(Model model) {
        Iterable<Career> careers = repository.findAll();
        model.addAttribute("careers", careers);
        return "careers/index";
    }
}