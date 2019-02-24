package com.alliance.jumpstart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Customer
 */
@Entity
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private String resumeFile;
    private String fullName;
    private String email;
    private String appliedPosition;
    private String status;

    protected Applicant() {
    }

    public Applicant(String fullName, String email, String message, String resumeFile, String position) {
        this.resumeFile = resumeFile;
        this.fullName = fullName;
        this.email = email;
        this.message = message;
        this.appliedPosition = position;
        this.status = "NEW";
    }

    public String getResumeFile() {
        return this.resumeFile;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getId() {
        return this.id;
    }

    public String getAppliedPosition() {
        return this.appliedPosition;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getStatus() {
        return this.status;
    }

}