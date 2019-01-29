package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Customer;
import com.alliance.jumpstart.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController{

    private CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }
	
	@RequestMapping("/customers")
	public String index(Model model) {
        Iterable<Customer> customers = repository.findAll();
		model.addAttribute("customers", customers);
		return "customer/index";
	}
}