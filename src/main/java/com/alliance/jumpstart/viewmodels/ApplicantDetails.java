package com.alliance.jumpstart.viewmodels;

public class ApplicantDetails {

    private String message;
    private String fullName;
    private String email;
    private String resumeFile;

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setResumeFile(String resumeFile) {
        this.resumeFile = resumeFile;
    }

    public String getResumeFile() {
        return this.resumeFile;
    }

}