package com.microservices.security.config;

import com.microservices.security.filters.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationProvider authenticationProvider;

/*	@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("kanchan").password(passwordEncoder.encode("kumar")).authorities("read").build();
		inMemoryUserDetailsManager.createUser(user);
		auth.userDetailsService(inMemoryUserDetailsManager).passwordEncoder(passwordEncoder);
	}*/

	@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		//http.formLogin();
		//http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().antMatchers("/hellov1").authenticated();
		http.addFilterBefore(new SecurityFilter(), BasicAuthenticationFilter.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
