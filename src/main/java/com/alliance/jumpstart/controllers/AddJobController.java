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
import com.alliance.jumpstart.entities.Task;
import com.alliance.jumpstart.repository.TaskRepository;
import com.alliance.jumpstart.services.TaskService;
import com.alliance.jumpstart.utils.Status;

import java.time.LocalDateTime;


@Controller
@ComponentScan
public class AddJobController {

    private static final Logger logger = LoggerFactory.getLogger(AddJobController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private GlobalController globalController;
    
    @Autowired
    TaskRepository taskrepository;

    
  
    
 
    
    @RequestMapping(value = {"/task/saveTask"}, method = RequestMethod.POST)
    public String saveTodo(@RequestParam("position")String position ,@RequestParam("qualification")String qualification,
    		@RequestParam("responsibilities")String responsibilities,Model model,
    		
                           final RedirectAttributes redirectAttributes) {
    	
    	 Iterable<Task> task = taskService.findAll();
        model.addAttribute("allJob", task);
        logger.info("/task/save");
        try {
           
        	Task t = new Task(position,qualification,responsibilities,LocalDateTime.now());
            taskService.save(t);
            redirectAttributes.addFlashAttribute("msg", "success");
            
           
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
            logger.error("save: " + e.getMessage());
        }

        return "redirect:/advertisement";
    }
    
    
    @RequestMapping(value = {"/task/editTask"}, method = RequestMethod.POST)
    public String editTodo(@ModelAttribute("editTask") Task editTask, Model model) {
        logger.info("/task/editTask");
        
        model.addAttribute("updatejob", new Task());
        try {
            Task task = taskService.findById(editTask.getId());
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
        if (operation.equals("update")) {
            Task task = taskService.findById(id);
            if (task != null) {
                task.setStatus(Status.PASSIVE.getValue());
                taskService.update(task);
                redirectAttributes.addFlashAttribute("msg", "trash");
            } else {
                redirectAttributes.addFlashAttribute("msg", "notfound");
            }
        }
        if (operation.equals("restore")) {
            Task task = taskService.findById(id);
            if (task != null) {
                task.setStatus(Status.ACTIVE.getValue());
                taskService.update(task);
                redirectAttributes.addFlashAttribute("msg", "active");
                redirectAttributes.addFlashAttribute("msgText", "Task " + task.getJobPosition() + " Restored Successfully.");
            } else {
                redirectAttributes.addFlashAttribute("msg", "active_fail");
                redirectAttributes.addFlashAttribute("msgText", "Task Activation failed !!! Task:" + task.getJobPosition());

            }
        } else if (operation.equals("delete")) {
            if (taskService.delete(id)) {
                redirectAttributes.addFlashAttribute("msg", "del");
                redirectAttributes.addFlashAttribute("msgText", " Task deleted permanently");
            } else {
                redirectAttributes.addFlashAttribute("msg", "del_fail");
                redirectAttributes.addFlashAttribute("msgText", " Task could not deleted. Please try later");
            }
        } else if (operation.equals("edit")) {
            Task editTask = taskService.findById(id);
            if (editTask != null) {
                model.addAttribute("editTask", editTask);
                return "edit";
            } else {
                redirectAttributes.addFlashAttribute("msg", "notfound");
            }
        }
        return "redirect:/advertisement";
    }


}
