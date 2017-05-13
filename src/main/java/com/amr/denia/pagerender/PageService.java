package com.amr.denia.pagerender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amr.denia.domain.entity.Contact;
import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.repository.ContactRepository;
import com.amr.denia.domain.repository.PageRepository;
import com.amr.denia.util.Util;

/**
 * Client's Page rendering services
 * @author amr
 */
@Service
public class PageService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	PageRepository pageRepository;

	@Value("${mail_from}")
	private String mailFrom;

	static final Logger logger = LoggerFactory.getLogger(PageController.class);
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dh = new SimpleDateFormat("hh:mm");

	/**
	 * Send a contact mail and store a record in the database 
	 * @param pageName
	 * @param name
	 * @param email
	 * @param phone
	 * @param description
	 */
	void contact(String pageName, String name, String email, String phone, String description) {
		Date date = new Date();
		
		// Build the message body:
		String msgBody = "Un cliente de " + pageName + " ha solicitado contactar:\r\n" +
						 "Fecha: " + df.format(date) + "\r\n" +
						 "Hora: " + dh.format(date) + "\r\n" +
						 "Nombre: " + name + "\r\n" +
						 "Email: " + email + "\r\n" +
						 "Telefono: " + phone + "\r\n" +
						 "Asunto: " + description;

		String to = pageRepository.findByPageName(pageName).getEmail();
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		String subject = pageName + ": contacto solicitado";

		// Send the message:
		String result;
		try {
			logger.info("Sending mail to : " + to + " from: " + mailFrom);
			Util.sendEmail(mailFrom, to, subject, msgBody);
			result = "Ok";
		} catch (Exception e) {
			result = "Error: " + e.getMessage();
			logger.error("Error sending mail (" + e.getClass().getName() + "): " + e.getMessage());
		}

		// Database record:
		Page page = pageRepository.findByPageName(pageName);
		Contact contact = new Contact();
		contact.setPage(page);
		contact.setContactDate(date);
		contact.setUserName(loggedUser);
		contact.setSentTo(to);
		contact.setResult(result);
		contact.setName(name);
		contact.setEmail(email);
		contact.setPhone(phone);
		contact.setDescription(description);
		contactRepository.save(contact);
	}
}
