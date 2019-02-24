package com.alliance.jumpstart.entities;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Applicant
 */

@Entity()
@Table(name = "table_applicant", schema = "tododb")
public class Applicant {

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Applicant> applicants;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

	
	 @Column(name = "full_name")
	    private String fullName;
	 @Column(name = "email")
	    private String email;
	 @Column(name = "message")
    private String message;
	 @Column(name = "resume")
    private String resumeFile;
   
    
    @Column(name = "position")
    private String appliedPosition;
    
    @Column(name = "status", nullable = true)
	private String stat;

    protected Applicant() {
    }

    public Applicant(String fullName, String email, String message, String resumeFile, String position) {
        this.resumeFile = resumeFile;
        this.fullName = fullName;
        this.email = email;
        this.message = message;
        this.appliedPosition = position;
        
    }

    public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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

    public String getAppliedPosition(){
        return this.appliedPosition;
    }
    
    public void addApplicants(Applicant a) {
        applicants.add(a);
    }

    public List<Applicant> getApplicants() {
        return this.applicants;
    }
    
    
}