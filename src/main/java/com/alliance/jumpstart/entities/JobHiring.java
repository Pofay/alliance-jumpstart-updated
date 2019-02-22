package com.alliance.jumpstart.entities;



import org.springframework.format.annotation.DateTimeFormat;
import com.alliance.jumpstart.utils.JobHiringCategories;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity()
@Table(name = "task", schema = "tododb")
public class JobHiring {
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Applicant> applicants;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "job_position")
    private String jobPosition;

    

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	@Column(name = "qualification")
    private String qualification;

    @Column(name = "responsibilities")
    private String responsibilities;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "task_date")
    private String nowDate;
    
    public JobHiring( String jobPosition, String qualification, String responsibilities, String nowDate,String status
			) {
		super();
		this.id = id;
		this.jobPosition = jobPosition;
		this.qualification = qualification;
		this.responsibilities = responsibilities;
		this.nowDate = nowDate;
		this.status = status;
		
	}

	@Column(name = "create_date")
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private JobHiringCategories category;

    @Column(name = "status")
    private String status="New";

    @Column(name = "user_id")
    private int userId;


    public JobHiring() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

   public String getTaskDate() {
        return nowDate;
    }

    public void setTaskDate(String taskDate) {
        this.nowDate = taskDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public JobHiringCategories getCategory() {
        return category;
    }

    public void setCategory(JobHiringCategories category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public void addApplicant(Applicant a) {
        applicants.add(a);
    }

    public List<Applicant> getApplicants() {
        return this.applicants;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobHiring addjob = (JobHiring) o;
        return id == addjob.id &&
                userId == addjob.userId &&
                Objects.equals(jobPosition, addjob.jobPosition) &&
                Objects.equals(qualification, addjob.qualification) &&
                Objects.equals(responsibilities, addjob.responsibilities) &&
                Objects.equals(nowDate, addjob.nowDate) &&
                Objects.equals(createDate, addjob.createDate) &&
                category == addjob.category &&
                Objects.equals(status, addjob.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id,jobPosition, qualification, responsibilities, nowDate, createDate, category, status, userId);
    }
}

