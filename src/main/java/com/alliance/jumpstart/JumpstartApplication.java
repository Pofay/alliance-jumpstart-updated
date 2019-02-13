package com.alliance.jumpstart;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.services.StorageService;

import com.alliance.jumpstart.JumpstartApplication;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

@EntityScan(basePackageClasses = { 
		JumpstartApplication.class,
		Jsr310JpaConverters.class 
})
@EnableTransactionManagement
public class JumpstartApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(JumpstartApplication.class, args);
	}


    @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");

	    }
	
	@Bean
	public CommandLineRunner demo(CareersRepository repository, StorageService service) {

		return (args) -> {
			service.deleteAll();

			// save a couple of customers
			/*
			 * repository.save(new Customer("Jack", "Bauer")); repository.save(new
			 * Customer("Chloe", "O'Brian")); repository.save(new Customer("Kim", "Bauer"));
			 * repository.save(new Customer("David", "Palmer")); repository.save(new
			 * Customer("Michelle", "Dessler"));
			 */

			Career c1 = new Career("Senior Technical Specialist");
			c1.addQualification("A degree holder of Computer Science, Computer Engineering or Information Technology");
			c1.addQualification("Must have at least 6 years working experience in software development");

			Career c2 = new Career("Associate Technical Specialist");
			c2.addQualification("A degree holder of Computer Science, Computer Engineering or Information Technology");
			c2.addQualification("Must have at least 4 years working experience in software development");

			repository.save(c1);
			repository.save(c2);
			service.init();
		};
	}
	

}
