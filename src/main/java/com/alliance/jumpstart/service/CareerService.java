package com.alliance.jumpstart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alliance.jumpstart.entities.Career;
@Service
public class CareerService {
	private List<Career> allCareers = new ArrayList<>();
	public List<Career>allCareers(){
		return allCareers;
	}
	public void addCareer(Career career) {
		allCareers.add(career);
	}
}

