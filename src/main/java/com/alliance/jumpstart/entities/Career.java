package com.alliance.jumpstart.entities;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerQualification> qualifications;

    protected Career() {
    }

    public Career(String position) {
        this.position = position;
        this.qualifications = new ArrayList<>();
    }

    public List<CareerQualification> getQualifications() {
        return this.qualifications;
    }

    public void addQualification(String description) {
        qualifications.add(new CareerQualification(description));
    }

    public String getPosition() {
        return this.position;
    }

    public Long getId() {
        return this.id;
    }
}