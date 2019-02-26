package com.alliance.jumpstart.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import com.alliance.jumpstart.jobs.EmailJob;
import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.utils.ViewModelMapper;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.vavr.control.Try;

import org.springframework.web.bind.annotation.PostMapping;

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
            @RequestParam(value = "date") String date, @RequestParam(value = "time") String time,
            @RequestParam(value = "content") String content, @RequestParam(value = "subject") String subject,
            final RedirectAttributes redirectAttributes) {

        LocalDateTime timeToSend = LocalDateTime.parse(String.format("%sT%s", date, time));
        applicantsRepo.findByEmail(email).ifPresent((a) -> {
            a.setStatus(applicantStatus);
            applicantsRepo.save(a);
        });

        JobDetail jobDetail = buildJobDetail(email, subject, content);
        Trigger trig = buildJobTrigger(jobDetail, timeToSend);

        Try<Date> scheduleEmail = Try.of(() -> this.scheduler.scheduleJob(jobDetail, trig));

        if (scheduleEmail.isFailure())
            redirectAttributes.addFlashAttribute("msg", "fail");
        redirectAttributes.addFlashAttribute("msg", "success");
        return "redirect:/advertisement";
    }

    private JobDetail buildJobDetail(String email, String subject, String content) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", email);
        jobDataMap.put("subject", subject);
        jobDataMap.put("body", content);

        return JobBuilder.newJob(EmailJob.class).withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job").usingJobData(jobDataMap).storeDurably().build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, LocalDateTime startAt) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers").withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.atZone(ZoneId.of("Asia/Shanghai")).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()).build();
    }
}