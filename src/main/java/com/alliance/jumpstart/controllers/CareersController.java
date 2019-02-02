package com.alliance.jumpstart.controllers;


import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.FileInfo;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.service.CareerService;



import com.alliance.jumpstart.entities.FileModel;
import com.alliance.jumpstart.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CareersController {

    private CareersRepository repository;

    @Autowired
    private CareerService careerService;
    
    
    @Autowired
	FileRepository fileRepository;
    
   
    public CareersController(CareersRepository repository) {
        this.repository = repository;
    }
    
    
   /* public ModelAndView createUser(@Valid Career career, BindingResult result) {
	    ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
        	//logger.info("Validation errors while submitting form.");
        	//mav.setViewName("user-creation");
    	    mav.addObject("career", career);
    	    //mav.addObject("allProfiles", getQualifications());
    	    return mav;
        }		
		careerService.addCareer(career);
	    //mav.addObject("allUsers", careerService.allCareers());
	    //mav.setViewName("user-info");
    	//logger.info("Form submitted successfully.");	    
	    return mav;
    }	*/
    
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
    	List<FileInfo> fileInfos = fileRepository.findAll().stream().map(
				fileModel ->	{
					
					String applicantname = fileModel.getApplicantName();
					String email = fileModel.getEmail();
					String position = fileModel.getPosition();
					String message = fileModel.getMessage();
					String filename = fileModel.getName();
					String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
	                        "downloadFile", fileModel.getName().toString()).build().toString();
					return new FileInfo(applicantname,email,position,message,filename, url); 
				} 
			)
			.collect(Collectors.toList());
	
		model.addAttribute("files", fileInfos);
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
    public String applyNow(@RequestParam(value="id", required=false) Long id, Model model) {
        Career c= repository.findById(Long.valueOf(id))
        .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));
            
        model.addAttribute("career", c);
        return "careers/application";
    }
    
   /* @RequestMapping(value = "/jobhiring", method = RequestMethod.POST)
    public String addJobHiring(@ModelAttribute Career career, Model model) {
    repository.addQualification(career);
    model.addAttribute("persons", repository.getQualification());
    return "result";
    }*/
    
    //@PostMapping("/apply")
    @RequestMapping(value ="/apply", method = RequestMethod.POST)
    public String uploadMultipartFile(@RequestParam("position")String position ,@RequestParam("name")String name, @RequestParam("email")String email,
    		@RequestParam("message")String message, @RequestParam("files") MultipartFile[] files, Model model) {
    	List<String> fileNames = new ArrayList<String>();
    	
    	 Career c= repository.findByPosition(String.valueOf(position));
    		            
    		        model.addAttribute("career", c);
    	
		try {
			List<FileModel> storedFile = new ArrayList<FileModel>();
			
			
			
			for(MultipartFile file: files) {
				FileModel fileModel = fileRepository.findByName(file.getOriginalFilename());
				
				if(fileModel != null) {
					// update new contents
					fileModel.setPic(file.getBytes());
					fileModel.setApplicantName(file.toString());
				}else {
					fileModel = new FileModel(name,email,position,message,file.getOriginalFilename(), file.getContentType(), file.getBytes());
				}
				
				fileNames.add(file.getOriginalFilename());				
				storedFile.add(fileModel);
			}
			
			// Save all Files to database
	    	fileRepository.saveAll(storedFile);
	    	
			model.addAttribute("message", "Files uploaded successfully!");
			model.addAttribute("files", fileNames);
		} catch (Exception e) {
			model.addAttribute("message", "Fail!");
			model.addAttribute("files", fileNames);
		}
		
         return "careers/application";
    }
    
   


}