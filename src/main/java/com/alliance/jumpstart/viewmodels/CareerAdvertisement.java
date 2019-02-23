package com.alliance.jumpstart.viewmodels;

import java.time.LocalDateTime;

public class CareerAdvertisement {
    private Long id;
    private String position;
    private String qualifications;
    private String responsibilities;
    private LocalDateTime dateCreated;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the dateCreated
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the responsibilities
     */
    public String getResponsibilities() {
        return responsibilities;
    }

    /**
     * @param responsibilities the responsibilities to set
     */
    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    /**
     * @return the qualifications
     */
    public String getQualifications() {
        return qualifications;
    }

    /**
     * @param qualifications the qualifications to set
     */
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }



}