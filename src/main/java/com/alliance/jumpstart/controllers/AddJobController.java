package com.alliance.jumpstart.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.services.JobHiringService;
import java.time.LocalDateTime;

@Controller
@ComponentScan
public class AddJobController {

    private static final Logger logger = LoggerFactory.getLogger(AddJobController.class);

    @Autowired
    private JobHiringService jobService;

    @Autowired
    JobHiringRepository taskrepository;

    @RequestMapping(value = { "/task/editTask" }, method = RequestMethod.POST)
    public String editTodo(@ModelAttribute("editTask") JobHiring editTask, Model model) {
        logger.info("/task/editTask");

        model.addAttribute("updatejob", new JobHiring());
        try {
            JobHiring task = jobService.findById(editTask.getId());
            editTask.setTaskDate(LocalDateTime.now());
            if (!task.equals(editTask)) {
                jobService.update(editTask);
                model.addAttribute("msg", "success");
            } else {
                model.addAttribute("msg", "same");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "fail");
            logger.error("editTask: " + e.getMessage());
        }
        model.addAttribute("editTodo", editTask);
        return "redirect:/advertisement";
    }

}
