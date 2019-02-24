package com.alliance.jumpstart.controllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	 * @throws IOException 
	 */
	
	
	
	//@RequestMapping("send-mail")
	//public String send() {

	 @RequestMapping(value = "/sendEmailTo", method = RequestMethod.POST)
	 
	 /*public void sendingEmail(@ModelAttribute("sendingemail") SendMailToApplicant sendingemail, 
	 Model model,HttpServletResponse response) {*/
	    public ModelAndView sendingEmail(@RequestParam("fromSender") String from, 
	    		@RequestParam("to") String to, 
	    		@RequestParam("message") String message,final RedirectAttributes redirectAttributes)
	              {
		
		user.setMessage(message);
		user.setEmailAddress(to);
		
		System.out.println(message);

		/*
		 * Here we will call sendEmail() for Sending mail to the sender.
		 */
		try {
			notificationService.sendEmail(user);
			
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		ModelAndView mav = new ModelAndView("dashboard/resumebank");
		return mav;

	 }
	
}
