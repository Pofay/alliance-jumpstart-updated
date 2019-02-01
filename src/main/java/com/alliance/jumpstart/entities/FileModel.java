package com.alliance.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.alliance.jumpstart.audit.DateAudit;
 
@Entity
@Table(name="file_model")
public class FileModel  extends DateAudit {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	 @Column(name = "applicantName")
		private String applicantName;
	 
	 @Column(name = "email")
		private String email;
	 
	 @Column(name = "message")
		private String message;
	 
    @Column(name = "name")
	private String name;
    
    @Column(name = "mimetype")
	private String mimetype;
	
	@Lob
    @Column(name="pic")
    private byte[] pic;
	
	public FileModel(){}
	
	public FileModel(String applicantName,String email,String message,String name, String mimetype, byte[] pic){
		this.applicantName = applicantName;
		this.email = email;
		this.message = message;
		this.name = name;
		this.mimetype = mimetype;
		this.pic = pic;
	}
	
	public String getApplicantName() {
		return this.applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getMimetype(){
		return this.mimetype;
	}
	
	public void setMimetype(String mimetype){
		this.mimetype = mimetype;
	}
	
	public byte[] getPic(){
		return this.pic;
	}
	
	public void setPic(byte[] pic){
		this.pic = pic;
	}
}