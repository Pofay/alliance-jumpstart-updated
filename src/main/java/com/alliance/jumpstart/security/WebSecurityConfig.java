package com.alliance.jumpstart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	

    @Autowired
    private UserDetailsService userDS;

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDS);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDS);
        auth.authenticationProvider(authenticationProvider());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDS);
    }

   
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/register", "/user/register","/applicant", "/static/**").permitAll()
                .antMatchers("/", "/login", "/register", "/user/register","/apply", "/webjars/**").permitAll()
                .antMatchers("/", "/careers", "/application","/task/editTask").permitAll()
                .antMatchers("/send-mail","sendEmail","/careers/applyNow","/apply","/updatejob","/task/saveTask", "/static/**","/webjars/**").permitAll()
                
                
        		.antMatchers("/superadmin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                
                .permitAll()
                .successHandler(successHandler)
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().permitAll().and().exceptionHandling().accessDeniedPage("/login");
    }

    
    
    @Override
    protected UserDetailsService userDetailsService() {
        return userDS;
    }
    
  
    
   /* public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/applicant").setViewName("forward:/index.html");
	    registry.addViewController("/applicant").setViewName("forward:/careers.html");
	    registry.addViewController("/applicant").setViewName("forward:/application.html");
	}*/

}
