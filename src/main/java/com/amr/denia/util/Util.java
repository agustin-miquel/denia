package com.amr.denia.util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General utilities
 * @author amr
 */
public class Util {
	
	public static final String MAIL_HOST = "localhost";

	static final Logger logger = LoggerFactory.getLogger(Util.class);

	/**
	 * Build a line with the specified indentation
	 * 
	 * @param text
	 * @param indent
	 * @return
	 */
	public static String line(String text, int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("\t");
		}
		return sb.toString() + text + "\n";
	}

	// Default: 0 indentation
	public static String line(String text) {
		return line(text, 0);
	}

	public static String line() {
		return line("");
	}

	/**
	 * Send an email thru the local mail server (Postfix)
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 * @return
	 */
	public static void sendEmail(String from, String to, String subject, String body) throws MessagingException {
		// Setup mail server
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", MAIL_HOST);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(from));

		// Set To: header field of the header.
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// Set Subject: header field
		message.setSubject(subject);

		// Now set the actual message
		message.setText(body);

		// Send message
		Transport.send(message);
	}

	/**
	 * Check if a resource exists
	 * 
	 * @param resource
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws Exception
	 */
	public static boolean resourceExists(String resource) {
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) new URL(resource).openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("HEAD");
			return con.getResponseCode() == HttpURLConnection.HTTP_OK;
			
		} catch (IOException e) {
			logger.debug("Error in resourceExists(" + resource + "):" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Insert a line in the images json file
	 * 
	 * @param filePath
	 * @param text
	 * @throws IOException
	 */
	public static void jsonInsert(String filePath, String text) throws IOException {
		String sep = System.getProperty("line.separator");
		
		String newRecord = "{ " + text + " }";
		Path path = Paths.get(filePath);
		byte[] bytes = Files.readAllBytes(path);
		String current = new String(bytes, "UTF-8");
		
		String newContent = current.substring(0, current.indexOf("]") - 1) +
							"," + sep + "\t" + newRecord + sep + "]";
	    Files.write(Paths.get(filePath), newContent.getBytes(), StandardOpenOption.WRITE);
	}
	
	/**
	 * Return the name of the first file in a specified folder when:
	 *  1. The file name begins with the specified string
	 *  2. The file is an image (according to its mime type)
	 *   
	 * @param folder
	 * @param fileNameBeginning
	 * @return the file name or an empty String when not found or exception 
	 */
	public static String getImageFile(String folder, String fileNameBeginning) {
		try {
			for (File file : new File(folder).listFiles()) {
				if (!file.getName().startsWith(fileNameBeginning)) continue;
				
				String mime = Files.probeContentType(file.toPath());
				
				if (mime != null && mime.startsWith("image")) {
					return file.getName();
				}
			}
		} catch(Exception e) {}

		return "";
	}

	/**
	 * Build an html tag with the provided text
	 * @param text
	 * @return
	 */
	public static String tag(String text) {
		return "<" + text + ">";
	}

	/**
	 * Build an html end tag with the provided text
	 * @param text
	 * @return
	 */
	public static String endtag(String text) {
		return "</" + text + ">";
	}
	
	// COOKIES --------------------------------------------------------------
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		boolean found = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue(value);
					response.addCookie(cookie);
					found = true;
					break;
				}
			}
		}
		if (!found) {
			Cookie myCookie = new Cookie(name, value);
			response.addCookie(myCookie);
		}		
	}
	
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
				    cookie.setMaxAge(0);
				    cookie.setValue(null);
				    cookie.setValue(null);
				    response.addCookie(cookie);
				}
			}
		}
	}
	
	public static List<String> getCookies(HttpServletRequest request) {
		List<String> values = new ArrayList<>();
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				values.add(cookie.getName() + " : " + cookie.getValue());
			}
		}
		return values;
	}

}
