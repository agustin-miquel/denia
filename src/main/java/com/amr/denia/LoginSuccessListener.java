package com.amr.denia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.amr.denia.contentmanager.ManagerController;

/**
 * Log entry when login success
 * @author amr
 */
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
		logger.info("Access to content manager: " + userDetails.getUsername());
	}
}