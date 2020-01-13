package com.oauth2.server.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin")
        .password(passwordEncoder.encode("nimda"))
        .roles("ADMIN")
        .and()
        .withUser("test")
        .password(passwordEncoder.encode("111111"))
        .roles("CUSTOMER");
	}

	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().disable();
        http.authorizeRequests()
				.antMatchers("/auth/user").access("isAuthenticated()");
		//                .anyRequest()
		//                .authenticated()
		//                .and()
		//                .httpBasic();
    }

	@Override
	public void configure(final WebSecurity web) throws Exception {
	}

}
