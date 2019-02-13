package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.JobHiring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.services.StorageService;
import com.alliance.jumpstart.services.JobHiringService;
import com.alliance.jumpstart.viewmodels.ApplicantDetails;
import com.alliance.jumpstart.viewmodels.ApplicantFormDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CareersController {

    private CareersRepository repository;
    private StorageService service;
    private ApplicantsRepository applicantRepo;
    @Autowired
    private JobHiringService taskService;

    @Autowired
    public CareersController(CareersRepository repository, StorageService service, ApplicantsRepository applicantRepo) {
        this.repository = repository;
        this.applicantRepo = applicantRepo;
        this.service = service;
    }

    @GetMapping(value = "/admindashboard")
    public String adminDashboard(Model model) {
        return "dashboard/admindashboard";
    }

    @GetMapping(value = "/advertisement")
    public String advertisement(Model model) {

        Iterable<JobHiring> task = taskService.findAll();
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

    @GetMapping(value = "/resumebank")
    public String resumeBank(Model model) {

        List<ApplicantDetails> applicants = StreamSupport.stream(applicantRepo.findAll().spliterator(), false)
                .map(a -> {
                    ApplicantDetails d = new ApplicantDetails();

                    String resumePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resumes/")
                            .path(a.getResumeFile()).toUriString();

                    System.out.println(a.getAppliedPosition());

                    d.setId(a.getId());
                    d.setAppliedPosition(a.getAppliedPosition());
                    d.setEmail(a.getEmail());
                    d.setFullName(a.getFullName());
                    d.setResumeDownloadPath(resumePath);
                    return d;
                }).collect(Collectors.toList());

        model.addAttribute("applicants", applicants);
        return "dashboard/resumebank";
    }

    @RequestMapping(value = "/careers", method = RequestMethod.GET)
    public String careers(Model model) {
        Iterable<Career> careers = repository.findAll();
        model.addAttribute("careers", careers);
        return "careers/careers";
    }

    @RequestMapping(value = "/updatejob", method = RequestMethod.GET)
    public String updateJob(@RequestParam(value = "id") int id, Model model) {
        JobHiring c = taskService.findById(id);

        model.addAttribute("updatejob", c);

        return "dashboard/editJob";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public String applyNow(@RequestParam(value = "id") long id, Model model) {
        Career c = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));

        model.addAttribute("career", c);
        model.addAttribute("formDetails", new ApplicantFormDetails());

        return "careers/application";
    }

    @RequestMapping(value = "/careers/applyNow", method = RequestMethod.POST)
    public String createApplicant(@RequestParam(value = "id") long id,
            @RequestParam(value = "file_cv") MultipartFile cv, @ModelAttribute ApplicantFormDetails details) {

        Career c = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));

        service.store(cv, LocalDateTime.now()).onSuccess((fileName) -> {
            Applicant a = new Applicant(details.getFullName(), details.getEmail(), details.getMessage(), fileName,
                    c.getPosition());
            c.addApplicant(a);
            repository.save(c);
        }).onFailure((o) -> System.out.println(o));

        return "redirect:/careers";
    }
}
