package com.greatlearning.employee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greatlearning.employee.serviceImpl.UserDetailServiceImpl;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailServiceImpl getuserUserDetailsServiceImpl() {
		UserDetailServiceImpl impl = new UserDetailServiceImpl();
		return impl;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProvider() {
		DaoAuthenticationProvider authBuilder = new DaoAuthenticationProvider();
		authBuilder.setUserDetailsService(getuserUserDetailsServiceImpl());
		authBuilder.setPasswordEncoder(passwordEncoder());
		return authBuilder;
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(getDaoAuthenticationProvider());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
				.antMatchers("/employees/getAllEmployees", "/employees/getEmployeeById", "/employees/sort",
						"/employees/sortByName", "/employees/search")
				.hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/employees/addEmployee", "/employees/addAllEmployees", "/employees/deleteById",
						"/employees/addRole", "/employees/addUser", "/employees/edit/{id}", "/employees/getAllUsers")
				.hasAuthority("ADMIN").anyRequest().fullyAuthenticated().and().httpBasic();
	}
}
