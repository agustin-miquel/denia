package com.amr.denia.contentmanager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amr.denia.Application;
import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.entity.Section;
import com.amr.denia.domain.entity.User;
import com.amr.denia.domain.repository.PageRepository;
import com.amr.denia.domain.repository.SectionRepository;
import com.amr.denia.domain.repository.UserRepository;
import com.amr.denia.util.Util;

/**
 * Content Manager services 
 * @author amr
 */
@Service
public class ManagerService {

	@Autowired
    ServletContext servletContext; 
	
	@Autowired
	PageRepository pageRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SectionRepository sectionRepository;

	@Value("${password_length}")
	private Integer passwordLength;

	public static final String LOGO = "logo";

	// File upload settings:
	public static final int MEMORY_THRESHOLD   = 1024 * 1024;
    public static final int MAX_FILE_SIZE      = 1024 * 1024 * 2;
    public static final int MAX_REQUEST_SIZE   = (1024 * 1024 * 2) + 1024;
    
	public static final String LIST_OF_FILES = "images_list.json";
	public static final String IMAGE = "image";

	public final static String PAGE_ENTITY_NAME = "Page";

	static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
	
	/**
	 * Change password.
	 * 
	 * @param currentPassword
	 * @param newPassword
	 * @param repeatPassword
	 * @throws Exception
	 */
	void changePassword(String currentPassword, String newPassword, String repeatPassword) throws Exception {

		// 1. Validate old pwd:
	    Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	    String password = encoder.encodePassword(currentPassword, null);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByName(userName);
		if (!user.getPassword().equals(password)) {
			throw new Exception("La contraseña actual es errónea");
		}
	    
		// 2. Validate new == repeat:
		if (!newPassword.equals(repeatPassword)) {
			throw new Exception("La nueva contraseña no es igual en los dos sitios");
		}
		
		// 3. Validate length:
		if (newPassword.length() < passwordLength) {
			throw new Exception("La contraseña debe tener al menos " + passwordLength + " caracteres");
		}
		
		// Change password:
		String newPasswordEnc = encoder.encodePassword(newPassword, null);
		user.setPassword(newPasswordEnc);
		userRepository.save(user);

		logger.info("Password changed for user " + user.getId() + " - " + user.getName());
	}
	
	/**
	 * Update the database
	 * @param parameters
	 * @throws Exception
	 */
	void update(Map<String, String[]> parameters, String pageName) throws Exception {
		String field = null;
		Class<?> paramClasses[] = {String.class};

		// Retrieve the data:
		Page page = pageRepository.findByPageName(pageName);

		for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
			Object entity;
			
			field = parameter.getKey();
			int pos = field.indexOf(".");
			if (pos == -1) {
				// Non-entity form parameters (eg:navigation)
				continue;
			}
			String entityName = field.substring(0, pos);
			String fieldName = field.substring(pos + 1);

			// Retrieve the entity:
			if (entityName.equals(PAGE_ENTITY_NAME)) {
				// The entity is the page itself:
				entity = page;
			} else {
				// Retrieve the entity:
				String pageClassName = Application.ENTITIES + "." + PAGE_ENTITY_NAME;
				String entityMethodName = "get" + entityName;
				Method entityMethod = Class.forName(pageClassName).getMethod(entityMethodName);
				entity = entityMethod.invoke(page);
			}
			String entityClassName = Application.ENTITIES + "." + entityName;
			Class<?> entityClass = Class.forName(entityClassName);
			
			// Retrieve the new value:
			String fieldValue = parameter.getValue()[0]; 
			
			// Logging of specific changes:
			switch(field) {
			case "Page.email":
			case "Head.title":
			case "Head.description":
				
				// Retrieve old value:
				String getName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method get = entityClass.getMethod(getName);
				Object oldValue = get.invoke(entity);
				
				if (oldValue != null && !oldValue.toString().equals(fieldValue)) {
					logger.info("Updated " + field + " from: " + oldValue + " to: " + fieldValue);
				}
			}
			
			// Update:
			String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method method = entityClass.getMethod(methodName, paramClasses);
			
			Object[] args = {fieldValue};
			
			// SET value on entity:
			method.invoke(entity, args);
		}
		
		// SAVE changes in page:
		pageRepository.save(page);
	}
	
	/**
	 * Upload the logo image.
	 * (executed as part of the UPDATE process)
	 *
	 * @param item
	 * @throws Exception
	 */
	void uploadLogo(FileItem item, String pageName) throws Exception {
		Page page = pageRepository.findByPageName(pageName);

		// Control: only logo upload is implemented:
		if (!item.getFieldName().equals(LOGO)) {
			throw new Exception("Unknown upload: " + item.getFieldName());
		}

        // Build the path to store uploaded files
        String uploadFolder = Application.PAGES + page.getId();
        String uploadPath;
        if (servletContext.getRealPath("").endsWith(File.separator)) {
        	uploadPath = servletContext.getRealPath("") + uploadFolder;
        } else {
        	uploadPath = servletContext.getRealPath("") + File.separator + uploadFolder;
        }
 
		// Check the file is an image:
		if (!item.getContentType().startsWith(IMAGE)) {
			throw new Exception("El fichero " + item.getName() + " no es una imagen");
		}
		
		// Build the name of the file:
		String extension = item.getName().substring(item.getName().lastIndexOf('.'));
		String fileName = item.getFieldName() + extension;
		String filePath = uploadPath + File.separator + fileName;

		// Retrieve the name of the current logo file:
		String currentFile = Util.getImageFile(uploadPath, LOGO);
		
		// Saves the file on disk:
		File storeFile = new File(filePath);
        item.write(storeFile);
        
		// Delete the old logo:
        if (currentFile != null && currentFile.trim().length() > 0 &&
        	!fileName.equalsIgnoreCase(currentFile)) {
        	
        	new File(uploadPath, currentFile).delete();
        }
	}
	
	/**
	 * Upload images for the Sections.
	 * @param request
	 * @throws Exception
	 */
	String uploadImages(HttpServletRequest request, Page page) throws Exception {
		
		// Check that we have a file upload request:
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new Exception("no multipart content");
		}

		// SETTINGS:
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to disk:
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        
        // Set temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        // Sets the maximum allowed size of a single uploaded file:
        upload.setFileSizeMax(MAX_FILE_SIZE);
        
        // Sets the maximum allowed size of a complete request (including form data):
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // Build the path to store uploaded files
        String uploadFolder = Application.PAGES + page.getId();
        String uploadPath;
        if (servletContext.getRealPath("").endsWith(File.separator)) {
        	uploadPath = servletContext.getRealPath("") + uploadFolder;
        } else {
        	uploadPath = servletContext.getRealPath("") + File.separator + uploadFolder;
        }
 
        // parses the request's content to extract file data
		List<FileItem> formItems = upload.parseRequest(request);

		String fileName = "";
		String listOfFilesPath = uploadPath + File.separator + LIST_OF_FILES;
		StringBuilder message = new StringBuilder("Ficheros subidos correctamente: ");

		if (formItems != null && formItems.size() > 0) {
			// iterates over form's fields
			for (FileItem item : formItems) {
				// processes only fields that are not form fields
				if (!item.isFormField()) {

					// Check a file has been selected:
					if (item.getName() == null || item.getName().trim().length() == 0) {
						throw new Exception("No se ha seleccionado ninguna imagen");
					}
					// Check the file is an image:
					if (!item.getContentType().startsWith(IMAGE)) {
						throw new Exception("Error: el fichero " + item.getName() + " no es una imagen");
					}

					fileName = new File(item.getName()).getName();
					fileName = fileName.replaceAll(" ", "");
					String filePath = uploadPath + File.separator + fileName;

					// saves the file on disk
					File storeFile = new File(filePath);
					if (storeFile.exists()) {
						throw new Exception("El fichero ya existe.");
					}
                    item.write(storeFile);
                    
                    // Update the list of files:
                    String relativeFilePath = Application.BASE + uploadFolder + "/" + fileName;  
                    String listEntry = "\"image\": \"" + relativeFilePath + "\"";
                    Util.jsonInsert(listOfFilesPath, listEntry);
                    
	                // Prepare the result message:
	                message.append(fileName + " ");
                } 
            }
        }
		return message.toString();
	}

	/**
	 * Create a new section
	 * @param request
	 * @param page
	 * @return the id of the section
	 */
	Integer createSection(HttpServletRequest request, Page page) {
		Section section = new Section();
		section.setTitle(request.getParameter("title"));
		section.setContent(request.getParameter("content"));
		section.setShowSection(true);
		section.setShowInMenu(false);
		section.setPage(page);
		sectionRepository.save(section);
		
		return section.getId();
	}

	/**
	 * Update a section
	 * @param request
	 * @param page
	 */
	void updateSection(HttpServletRequest request, Page page) {
		Integer id = new Integer(request.getParameter("id"));
		Section section = sectionRepository.findOne(id);
		section.setTitle(request.getParameter("title"));
		section.setContent(request.getParameter("content"));
		sectionRepository.save(section);
	}
}
