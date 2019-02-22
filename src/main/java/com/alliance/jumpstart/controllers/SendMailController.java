package com.alliance.jumpstart.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alliance.jumpstart.entities.SendMailToApplicant;
import com.alliance.jumpstart.service.impl.MailService;


@RestController
//@Controller
public class SendMailController {

	@Autowired
	private MailService notificationService;

	@Autowired
	private SendMailToApplicant user;

	/**
	 * 
	 * @return
	 */
	
	
	
	@RequestMapping("send-mail")
	public String send() {

		/*
		 * Creating a User with the help of User class that we have declared. Setting
		 * the First,Last and Email address of the sender.
		 */
		user.setFirstName("Mukul");
		user.setLastName("Jaiswal");
		user.setEmailAddress("unloveddelavictoria@gmail.com");

		/*
		 * Here we will call sendEmail() for Sending mail to the sender.
		 */
		try {
			notificationService.sendEmail(user);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your mail has been send to the user.";
	}

	/**
	 * 
	 * @return
	 * @throws MessagingException
	 */
	
}
