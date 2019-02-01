package com.alliance.jumpstart;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.repository.CareersRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication

@EntityScan(basePackageClasses = { 
		JumpstartApplication.class,
		Jsr310JpaConverters.class 
})
@EnableTransactionManagement
public class JumpstartApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}



	public static void main(String[] args) {
		SpringApplication.run(JumpstartApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CareersRepository repository) {
		return (args) -> {
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
		};
	}

}
