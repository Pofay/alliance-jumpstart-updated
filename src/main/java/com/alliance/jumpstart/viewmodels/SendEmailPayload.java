package com.alliance.jumpstart.viewmodels;

import java.time.LocalDate;
import java.time.LocalTime;

public class SendEmailPayload {

    private String applicantStatus;
    private String email;
    private Long id;
    private String messageBody;
    private String ccs;
    private LocalDate date;
    private LocalTime timeToSend;

    public SendEmailPayload() {
    }

    /**
     * @return the ccs
     */
    public String getCcs() {
        return ccs;
    }

    /**
     * @param ccs the ccs to set
     */
    public void setCcs(String ccs) {
        this.ccs = ccs;
    }

    /**
     * @return the timeToSend
     */
    public LocalTime getTimeToSend() {
        return timeToSend;
    }

    /**
     * @param timeToSend the timeToSend to set
     */
    public void setTimeToSend(LocalTime timeToSend) {
        this.timeToSend = timeToSend;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the messageBody
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody the messageBody to set
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * @return the applicantStatus
     */
    public String getApplicantStatus() {
        return applicantStatus;
    }

    /**
     * @param applicantStatus the applicantStatus to set
     */
    public void setApplicantStatus(String applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}