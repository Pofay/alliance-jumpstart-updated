package com.alliance.jumpstart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CareerResponsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    protected CareerResponsibility() {
    }

    public CareerResponsibility(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}