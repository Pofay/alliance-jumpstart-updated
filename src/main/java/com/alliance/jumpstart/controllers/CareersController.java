package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.JobHiring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.services.StorageService;
import com.alliance.jumpstart.services.ApplicantService;
import com.alliance.jumpstart.services.FileService;
import com.alliance.jumpstart.services.JobHiringService;
import com.alliance.jumpstart.viewmodels.ApplicantDetails;
import com.alliance.jumpstart.viewmodels.ApplicantFormDetails;

import com.alliance.jumpstart.controllers.DownloadFileController;
import com.alliance.jumpstart.entities.FileInfo;

import com.alliance.jumpstart.entities.FileModel;

import com.alliance.jumpstart.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CareersController {

	public String vals;
	
	private CareersRepository repository;
	private StorageService service;
	private ApplicantsRepository applicantRepo;
	
	@Autowired
	FileService fileService;
	

	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private JobHiringService jobService;
	
	@Autowired
	private ApplicantService applicantService;

	@Autowired
	private ApplicantsRepository applicantRepository;
	
	@Autowired
	private JobHiringRepository jobRepository;
	


	@Autowired
	public CareersController(CareersRepository repository, StorageService service, ApplicantsRepository applicantRepo) {
		this.repository = repository;
		this.applicantRepo = applicantRepo;
		this.service = service;
	}

	@GetMapping(value = "/admindashboard")
	public String adminDashboard(Model model) {
		JobHiring jh =new JobHiring();
		model.addAttribute("satusList", jh);
		return "dashboard/admindashboard";
	}

	@GetMapping(value = "/advertisement")
	public String advertisement(Model model) {

		Iterable<JobHiring> task = jobService.findAll();
		model.addAttribute("allJob", task);
		model.addAttribute("editTask", task);

		return "dashboard/advertisement";
	}

	@GetMapping(value = "/reportanalytics")
	public String reportAnalytics(Model model) {
		return "dashboard/reportanalytics";
	}

	@GetMapping(value = "/backtologin")
	public String backToLogin(Model model) {
		return "dashboard/login";
	}

	/*@GetMapping(value = "/resumebank")
	public String resumeBank(Model model) {

		List<ApplicantDetails> applicants = StreamSupport.stream(applicantRepo.findAll().spliterator(), false)
				.map(a -> {
					ApplicantDetails d = new ApplicantDetails();

					String resumePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resumes/")
							.path(a.getResumeFile()).toUriString();

					System.out.println(a.getAppliedPosition());
					String stat="New";	
					d.setId(a.getId());
					d.setAppliedPosition(a.getAppliedPosition());
					d.setEmail(a.getEmail());
					d.setFullName(a.getFullName());
					d.setResumeDownloadPath(resumePath);
					d.setStat(stat);
					return d;
				}).collect(Collectors.toList());

		model.addAttribute("applicants", applicants);
		return "dashboard/resumebank";
	}*/
	
	@GetMapping(value="/resumebank")
    public String resumeBank(Model model) {
    	List<FileInfo> fileInfos = fileRepository.findAll().stream().map(
				fileModel ->	{
					
					Long id = fileModel.getId();
					String applicantname = fileModel.getApplicantName();
					String email = fileModel.getEmail();
					String position = fileModel.getPosition();
					String message = fileModel.getMessage();
					String filename = fileModel.getName();
					String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
	                        "downloadFile", fileModel.getName().toString()).build().toString();
					return new FileInfo(id,applicantname,email,position,message,filename, url); 
				} 
			)
			.collect(Collectors.toList());
	
		model.addAttribute("files", fileInfos);
		return "dashboard/resumebank";
    }

	@RequestMapping(value = "/careers", method = RequestMethod.GET)
	public String careers(Model model) {
		Iterable<JobHiring> careers = jobRepository.findAll();
		model.addAttribute("careers", careers);
		return "careers/careers";
	}
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public String sendEmail(@RequestParam(value = "id") Long id, Model model) {
		
		FileModel fileModel; fileModel = fileService.findById(id);
		
		//FileModel fileModel = new FileModel();
		//fileModel.setId(id);
		
		model.addAttribute("app", fileModel);
		System.out.println(""+id);
		return "dashboard/sendemail";
	}
	

	@RequestMapping(value = "/updatejob", method = RequestMethod.GET)
	public String updateJob(@RequestParam(value = "id") int id, Model model) {
		JobHiring c = jobService.findById(id);

		model.addAttribute("updatejob", c);

		return "dashboard/editJob";
	}

	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String applyNow(@RequestParam(value = "id") int id, Model model) {
		JobHiring c = jobRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cannot find resource with id"));

		model.addAttribute("career", c);
		model.addAttribute("formDetails", new ApplicantFormDetails());
	
		return "careers/application";
	}

	
	@RequestMapping(value ="/careers/applyNow", method = RequestMethod.POST)
    public String uploadMultipartFile(HttpServletResponse response,@RequestParam("position")String position ,@RequestParam("name")String name, @RequestParam("email")String email,
    		@RequestParam("message")String message, @RequestParam("files") MultipartFile[] files, Model model) {
    	List<String> fileNames = new ArrayList<String>();
    	
    	
    	
		try {
			List<FileModel> storedFile = new ArrayList<FileModel>();
			
			
			
			for(MultipartFile file: files) {
				FileModel fileModel = fileRepository.findByName(file.getOriginalFilename());
				
				
				
				if(fileModel != null) {
					// update new contents
					
					fileModel.setPic(file.getBytes());
					fileModel.setApplicantName(file.toString());
				}else {
					if(file.getOriginalFilename().contains(".") && file.getOriginalFilename().contains("docx") ) {
						vals = name+"."+"docx";
					}
					
					if(file.getOriginalFilename().contains(".") && file.getOriginalFilename().contains("doc") ) {
						vals = name+"."+"doc";
					}
					
					if(file.getOriginalFilename().contains(".") && file.getOriginalFilename().contains("pdf") ) {
						vals = name+"."+"pdf";
					}
					
					fileModel = new FileModel(name,email,position,message,vals, file.getContentType(), file.getBytes());
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
		
		
		
		return "redirect:/";
    }
	
	
	/*@RequestMapping(value = "/careers/applyNow", method = RequestMethod.POST)
	public String createApplicant(@RequestParam(value = "id") int id, @RequestParam(value = "file_cv") MultipartFile cv,
			@ModelAttribute ApplicantFormDetails details,RedirectAttributes redirectAttributes) {
		
		JobHiring jh = jobRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cannot find resource with id"));


		service.store(cv, LocalDateTime.now()).onSuccess((fileName) -> {
			Applicant ap = new Applicant(details.getFullName(), details.getEmail(), details.getMessage(), fileName,
					jh.getJobPosition());
		
	
		//applicantService.saveApplicant(app);
		jh.addApplicant(ap);
		jobService.save(jh);
		
		
		
			
			redirectAttributes.addFlashAttribute("sendApplication", "success");
		}).onFailure((o) -> redirectAttributes.addFlashAttribute("sendError", "fail"));

		return "redirect:/";
	}*/
}