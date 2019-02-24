package com.alliance.jumpstart.utils;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.viewmodels.ApplicantDetails;
import com.alliance.jumpstart.viewmodels.SendEmailPayload;
import com.alliance.jumpstart.viewmodels.CareerAdvertisement;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ViewModelMapper {

    public static ApplicantDetails toDetailsViewModel(Applicant a) {
        ApplicantDetails d = new ApplicantDetails();

        String resumePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resumes/")
                .path(a.getResumeFile()).toUriString();

        System.out.println(a.getAppliedPosition());

        d.setId(a.getId());
        d.setAppliedPosition(a.getAppliedPosition());
        d.setEmail(a.getEmail());
        d.setFullName(a.getFullName());
        d.setResumeDownloadPath(resumePath);
        d.setStatus(a.getStatus());
        return d;
    }

    public static CareerAdvertisement toAdvertisementViewModel(Career career) {
        String responsibilities = career.getResponsibilities().stream()
                .map(responsibility -> responsibility.getDescription()).reduce("", (acc, val) -> val + ", " + acc);

        String qualifications = career.getQualifications().stream().map(qualification -> qualification.getDescription())
                .reduce("", (acc, val) -> val + ", " + acc);

        CareerAdvertisement advertisement = new CareerAdvertisement();

        advertisement.setDateCreated(career.getDateCreated());
        advertisement.setId(career.getId());
        advertisement.setPosition(career.getPosition());
        advertisement.setQualifications(qualifications);
        advertisement.setResponsibilities(responsibilities);

        return advertisement;
    }

    public static SendEmailPayload toEmailPayload(Applicant a) {
        SendEmailPayload d = new SendEmailPayload();

        d.setApplicantStatus(a.getStatus());
        d.setEmail(a.getEmail());
        d.setId(a.getId());

        return d;
    }

}