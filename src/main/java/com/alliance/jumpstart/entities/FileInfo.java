package com.alliance.jumpstart.entities;

public class FileInfo {
	private String applicantName;
	private String email;
	private String message;
	private String filename;
	private String url;
	
	public FileInfo(String applicantName,String email,String message,String filename, String url) {
		this.applicantName = applicantName;
		this.email = email;
		this.message = message;
		this.filename = filename;
		this.url = url;
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

	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}