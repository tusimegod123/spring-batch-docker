package com0.miu.edu.security;


import com0.miu.edu.filter.MyAutheticationFilter;
import com0.miu.edu.filter.MyAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // To tell Spring how to look users.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // Configure the token approach disabling the default Spring approach.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Set the root of the login page customized
        MyAutheticationFilter myAutheticationFilter = new MyAutheticationFilter(authenticationManagerBean());
        myAutheticationFilter.setFilterProcessesUrl("/api/login");
        // Set the access areas for the users.
        http.csrf().disable(); // Session ID Cookies based approach
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/refresh-token/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/users/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET,"/load").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/users/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/roles/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/add-roles/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/api/users/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        // WebSecurityConfigurerAdapter inside of this class we have a bean of the Authentication Manager that we can make use of this
        http.addFilter(myAutheticationFilter);
        // Set this filter before of any other ones coz we need to intercept any request before any other filters
        http.addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // We set here what is for. That specific class
    }

    // Here we call the Authentication Manager from the class we are extending
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
