package com.alliance.jumpstart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Customer
 */
@Entity
public class CareerQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    protected CareerQualification() {
    }

    public CareerQualification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}