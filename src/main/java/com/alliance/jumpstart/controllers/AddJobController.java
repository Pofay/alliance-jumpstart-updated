package com.alliance.jumpstart.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.services.JobHiringService;
import com.alliance.jumpstart.utils.Status;

import java.time.LocalDateTime;


@Controller
@ComponentScan
public class AddJobController {

    private static final Logger logger = LoggerFactory.getLogger(AddJobController.class);

    @Autowired
    private JobHiringService taskService;
    @Autowired
    private GlobalController globalController;
    
    @Autowired
    JobHiringRepository taskrepository;

    
  
    
 
    
    @RequestMapping(value = {"/task/saveTask"}, method = RequestMethod.POST)
    public String saveTodo(@RequestParam("position")String position ,@RequestParam("qualification")String qualification,
    		@RequestParam("responsibilities")String responsibilities,Model model,
    		
                           final RedirectAttributes redirectAttributes) {
    	
    	 Iterable<JobHiring> task = taskService.findAll();
        model.addAttribute("allJob", task);
        logger.info("/task/save");
        try {
           
        	JobHiring t = new JobHiring(position,qualification,responsibilities,LocalDateTime.now());
            taskService.save(t);
            redirectAttributes.addFlashAttribute("msg", "success");
            
           
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
            logger.error("save: " + e.getMessage());
        }

        return "redirect:/advertisement";
    }
    
    
    @RequestMapping(value = {"/task/editTask"}, method = RequestMethod.POST)
    public String editTodo(@ModelAttribute("editTask") JobHiring editTask, Model model) {
        logger.info("/task/editTask");
        
        model.addAttribute("updatejob", new JobHiring());
        try {
            JobHiring task = taskService.findById(editTask.getId());
            editTask.setTaskDate(LocalDateTime.now());
            if (!task.equals(editTask)) {
                taskService.update(editTask);
                model.addAttribute("msg", "success");
            } else {
                model.addAttribute("msg", "same");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "fail");
            logger.error("editTask: " + e.getMessage());
        }
        model.addAttribute("editTodo", editTask);
        //return "/dashboard/editJob";
        return "redirect:/advertisement";
    }


    @RequestMapping(value = "/task/{operation}/{id}", method = RequestMethod.GET)
    public String todoOperation(@PathVariable("operation") String operation,
                                @PathVariable("id") int id, final RedirectAttributes redirectAttributes,
                                Model model) {

        logger.info("/task/operation: {} ", operation);
        if (operation.equals("delete")) {
            if (taskService.delete(id)) {
                redirectAttributes.addFlashAttribute("msg", "del");
                redirectAttributes.addFlashAttribute("msgText", " Task deleted permanently");
            } else {
                redirectAttributes.addFlashAttribute("msg", "del_fail");
                redirectAttributes.addFlashAttribute("msgText", " Task could not deleted. Please try later");
            }
        }
        return "redirect:/advertisement";
    }


}
