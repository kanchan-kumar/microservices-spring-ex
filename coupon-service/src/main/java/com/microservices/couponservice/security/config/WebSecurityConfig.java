package com.microservices.couponservice.security.config;

import com.microservices.couponservice.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;
import java.util.List;

//For Using Normal Auth
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired UserDetailServiceImpl userDetailService;

	@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}

	@Override protected void configure(HttpSecurity http) throws Exception {
		//http.formLogin();
		/*http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/couponapi/coupons/**", "/index", "/showGetCoupons", "/getCoupons", "/couponDetails").
				hasAnyRole("USER", "ADMIN").
				mvcMatchers(HttpMethod.GET, "/createCoupon", "/createResponse", "/showCreateCoupons").
				hasRole("ADMIN").
				mvcMatchers(HttpMethod.POST, "getCoupon").
				hasAnyRole("USER", "ADMIN").
				mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "saveCoupon").
				hasRole("ADMIN").
				mvcMatchers("/", "/login", "/showReg", "/logout", "registerUser").permitAll().
				anyRequest().denyAll().and().csrf().disable().
				logout().logoutSuccessUrl("/");

		http.cors(corsCustomizer -> {
			CorsConfigurationSource configurationSource = (request) -> {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.setAllowedOrigins(Arrays.asList("https://fonts.gstatic.com"));
				corsConfiguration.setAllowedMethods(Arrays.asList("GET"));
				return corsConfiguration;
			};
			corsCustomizer.configurationSource(configurationSource);
		});*/

		/*http.csrf(csrfCustomizer -> {
			csrfCustomizer.ignoringAntMatchers("/couponapi/coupons/**");
			RequestMatcher requestMatcher = new RegexRequestMatcher("/couponapi/coupons/**", "POST");
			requestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
			csrfCustomizer.ignoringRequestMatchers(requestMatcher);
		});*/
	}

	@Bean public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean @Override public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
