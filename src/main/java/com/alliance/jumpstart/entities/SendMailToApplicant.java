package com.alliance.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity()
@Table(name = "table_sendemail", schema = "tododb")
public class SendMailToApplicant {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id", unique = true, nullable = false)
	    private int id;
	 	
	 
		

		@Column(name = "emaill_address") 	
	private String emailAddress;
	 	 
	@Column(name = "message") 	 
	private String message;
	
	
	public SendMailToApplicant() {}
	
	public SendMailToApplicant(int id, String emailAddress, String message) {
		super();
		this.id = id;
		this.emailAddress = emailAddress;
		this.message = message;
	}
	
	 public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
