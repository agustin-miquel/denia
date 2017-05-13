package com.amr.denia;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * Spring security configuration
 * @author amr
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Configure security
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        	.antMatchers("/admin/**").authenticated()
        	.anyRequest().permitAll()
    		.and()
    	.formLogin()
        	.permitAll()
	        .loginPage("/login")
	        .successHandler(setAuthenticationSuccessHandler())
	        .failureUrl("/login?error=true")        
	        .and()
        .logout()                         
            .permitAll()
            .logoutSuccessUrl("/login?logout=true")
        	.and()
	   .csrf()
	        .disable();
    }

    /**
     * Set database authentication
     * @param auth
     */
    @Autowired  // injection of the method's parameters
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        // Database:
        auth.jdbcAuthentication().dataSource(dataSource)
        		.passwordEncoder(new Md5PasswordEncoder())
                .usersByUsernameQuery(
                        "select name, password, true from User where name=?")
                .authoritiesByUsernameQuery(
                        "select name, 'ROLE_USER' from User where name=?");
    }

    /**
     * Set login success redirected page
     * @return
     */
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler setAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setDefaultTargetUrl("/admin/menu");
        return auth;
    }
}
