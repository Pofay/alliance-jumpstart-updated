package com.alliance.jumpstart.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.utils.ViewModelMapper;
import com.alliance.jumpstart.viewmodels.SendEmailPayload;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EmailController {

    private Scheduler scheduler;
    private ApplicantsRepository applicantsRepo;

    @Autowired
    public EmailController(Scheduler scheduler, ApplicantsRepository applicantsRepo) {
        this.scheduler = scheduler;
        this.applicantsRepo = applicantsRepo;
    }

    @GetMapping("/scheduleEmail")
    public String downloadResume(@RequestParam(value = "id") long applicantId, Model model) {

        String endpoint = applicantsRepo.findById(Long.valueOf(applicantId)).map(ViewModelMapper::toEmailPayload)
                .map(emailDetails -> model.addAttribute("emailDetails", emailDetails))
                .map(d -> "dashboard/scheduleEmail")
                .orElseThrow(() -> new RuntimeException("No applicant with id of: " + applicantId));

        return endpoint;
    }

    @PostMapping(value = "/scheduleEmail", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postMethodName(@RequestParam(value = "email") String email,
            @RequestParam(value = "applicantStatus") String applicantStatus, @RequestParam(value = "ccs") String ccs,
            @RequestParam(value = "date") String date, @RequestParam(value = "time") String time) {
        // TODO: process POST request

        LocalDateTime timeToSend = LocalDateTime.parse(String.format("%sT%s", date, time));
        System.out.println(timeToSend.toString());

        return "redirect:/advertisement";
    }

}