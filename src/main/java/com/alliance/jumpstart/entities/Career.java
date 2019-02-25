package com.alliance.jumpstart.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Customer
 */
@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String position;
    private Long numberOfViews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerQualification> qualifications;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Applicant> applicants;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerResponsibility> responsibilities;
    private LocalDateTime dateCreated;

    protected Career() {
    }

    /**
     * @return the numberOfViews
     */
    public Long getNumberOfViews() {
        return numberOfViews;
    }

    /**
     * @param numberOfViews the numberOfViews to set
     */
    public void addNumberOfViews(Long numberOfViews) {
        this.numberOfViews += numberOfViews;
    }

    public Career(String position, LocalDateTime dateCreated) {
        this.position = position;
        this.qualifications = new ArrayList<>();
        this.responsibilities = new ArrayList<>();
        this.dateCreated = dateCreated;
        this.numberOfViews = Long.valueOf(0);
    }

    public List<CareerQualification> getQualifications() {
        return this.qualifications;
    }

    public List<CareerResponsibility> getResponsibilities() {
        return this.responsibilities;
    }

    public void addQualification(String description) {
        qualifications.add(new CareerQualification(description));
    }

    public void addResponsibility(String description) {
        responsibilities.add(new CareerResponsibility(description));
    }

    public void addApplicant(Applicant a) {
        applicants.add(a);
    }

    public List<Applicant> getApplicants() {
        return this.applicants;
    }

    public String getPosition() {
        return this.position;
    }

    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    public Long getId() {
        return this.id;
    }
}