package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.JobHiring;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.responses.AnalyticsDataResponse;
import com.alliance.jumpstart.services.StorageService;
import com.alliance.jumpstart.utils.ViewModelMapper;
import com.alliance.jumpstart.services.AnalyticsService;
import com.alliance.jumpstart.services.JobHiringService;
import com.alliance.jumpstart.viewmodels.ApplicantDetails;
import com.alliance.jumpstart.viewmodels.ApplicantFormDetails;
import com.alliance.jumpstart.viewmodels.CareerAdvertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CareersController {

    private static final Logger logger = LoggerFactory.getLogger(AddJobController.class);

    private CareersRepository careersRepo;
    private StorageService storageService;
    private ApplicantsRepository applicantRepo;
    private JobHiringService taskService;
    private AnalyticsService analyticsService;

    @Autowired
    public CareersController(CareersRepository careersRepo, StorageService storageService,
            ApplicantsRepository applicantRepo, AnalyticsService analyticsService, JobHiringService taskService) {
        this.careersRepo = careersRepo;
        this.applicantRepo = applicantRepo;
        this.storageService = storageService;
        this.analyticsService = analyticsService;
        this.taskService = taskService;
    }

    @GetMapping(value = "/admindashboard")
    public String adminDashboard(Model model) {
        AnalyticsDataResponse response = analyticsService.createAnalyticsResponse();

        model.addAttribute("analytics", response);

        return "dashboard/admindashboard";
    }

    @GetMapping(value = "/advertisement")
    public String advertisement(Model model) {

        List<CareerAdvertisement> advertisements = StreamSupport.stream(careersRepo.findAll().spliterator(), false)
                .map(ViewModelMapper::toAdvertisementViewModel).collect(Collectors.toList());

        model.addAttribute("careerAdvertisements", advertisements);
        model.addAttribute("editTask", advertisements);

        return "dashboard/advertisement";
    }

    @RequestMapping(value = { "/careers/save" }, method = RequestMethod.POST)
    public String saveJobHiring(@RequestParam("position") String position,
            @RequestParam("qualification") String qualification,
            @RequestParam("responsibilities") String responsibilities, Model model,

            final RedirectAttributes redirectAttributes) {

        logger.info("/careers/save");
        try {
            this.createCareer(position, qualification, responsibilities);
            redirectAttributes.addFlashAttribute("msg", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
            logger.error("save: " + e.getMessage());
        }

        return "redirect:/advertisement";
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
                .map(ViewModelMapper::toDetailsViewModel).collect(Collectors.toList());
        model.addAttribute("applicants", applicants);
        return "dashboard/resumebank";
    }

    @RequestMapping(value = "/careers", method = RequestMethod.GET)
    public String careers(Model model) {
        Iterable<Career> careers = careersRepo.findAll();
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
        Career c = careersRepo.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));

        c.addNumberOfViews(Long.valueOf(1));
        careersRepo.save(c);

        model.addAttribute("career", c);
        model.addAttribute("formDetails", new ApplicantFormDetails());

        return "careers/application";
    }

    @RequestMapping(value = "/careers/applyNow", method = RequestMethod.POST)
    public String createApplicant(@RequestParam(value = "id") long id,
            @RequestParam(value = "file_cv") MultipartFile cv, @ModelAttribute ApplicantFormDetails details) {

        Career c = careersRepo.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cannot find resource with id"));

        storageService.store(cv, UUID.randomUUID())
                .onSuccess((fileName) -> this.createAndSaveApplicant(details, c, fileName))
                .onFailure((o) -> System.out.println(o));

        return "redirect:/careers";
    }

    @RequestMapping(value = "/careers/delete/{id}", method = RequestMethod.GET)
    public String deleteCareer(@PathVariable("id") int id, final RedirectAttributes redirectAttributes, Model model) {

        logger.info("/careers/delete/{} ", id);
        this.careersRepo.deleteById(new Long(id));
        redirectAttributes.addFlashAttribute("msg", "del");
        redirectAttributes.addFlashAttribute("msgText", " Career deleted permanently");
        return "redirect:/advertisement";
    }

    private void createAndSaveApplicant(ApplicantFormDetails details, Career c, String fileName) {
        Applicant a = new Applicant(details.getFullName(), details.getEmail(), details.getMessage(), fileName,
                c.getPosition());
        c.addApplicant(a);
        careersRepo.save(c);
    }

    private void createCareer(String position, String qualification, String responsibilities) {
        Career c = new Career(position, LocalDateTime.now());

        Arrays.asList(qualification.split(",")).stream().forEach(c::addQualification);
        Arrays.asList(responsibilities.split(",")).stream().forEach(c::addResponsibility);

        this.careersRepo.save(c);
    }

}
