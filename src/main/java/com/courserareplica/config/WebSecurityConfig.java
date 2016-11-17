package com.courserareplica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
                .apply(stormpath())
                    .and()
                .authorizeRequests().antMatchers("/", "/public/**").permitAll()
                    .and()
                .authorizeRequests().antMatchers("/home/**").authenticated();
	}
}
