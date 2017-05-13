package com.amr.denia.contentmanager;

import static com.amr.denia.util.Util.line;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amr.denia.Application;
import com.amr.denia.domain.entity.Contact;
import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.entity.Section;
import com.amr.denia.domain.repository.ContactRepository;
import com.amr.denia.domain.repository.PageRepository;
import com.amr.denia.domain.repository.SectionRepository;
import com.amr.denia.util.Util;

/**
 * Content Manager controller
 * @author amr
 */
@Controller
@Scope("session")
public class ManagerController {

	@Autowired
    ServletContext servletContext; 

	@Autowired
	ManagerService managerService;

	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	ContactRepository contactRepository;

	public static final String LOGO = "logo";
	
	static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	// PAGE REQUESTS -----------------------------------------------------------------------------
	
	/**
	 * Show Login page
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login");
		
		// Validate the page is ok:
		String pageName = Util.getCookie(request, "pageName");
		if (pageName == null || pageName.trim().length() == 0) {
			mv.addObject("pageName", "");
			mv.addObject("message", "Solo se puede acceder al gestor desde la pagina correspondiente");
		} 
		else {
			Object res = pageRepository.findByPageName(pageName);
			if (res == null) {
				mv.addObject("pageName", "");
				mv.addObject("message", "Solo se puede acceder al gestor desde la pagina correspondiente");
			} else {
				mv.addObject("pageName", pageName);
			}
		}

		mv.addObject("loginfo", getLoginfo());
		return mv;
	}

	/**
	 * Show the general menu
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/menu")
	private ModelAndView generalMenu(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		
		// Build menu:
		List<String[]> menuItems = new ArrayList<>();
		menuItems.add( new String[] {"/denia/admin/content", "Gestión de Contenidos"} );
		menuItems.add( new String[] {"/denia/admin/contacts", "Contactos"} );
		menuItems.add( new String[] {"/denia/admin/password", "Cambio de Contraseña"} );

		ModelAndView mv = new ModelAndView("menu");
		mv.addObject("menuTitle", "Menu General");
		mv.addObject("menuItems", menuItems);
		mv.addObject("menuGeneral", true);
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		return mv;
	}
	
	/**
	 * Show the content management menu
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/content")
	private ModelAndView contentMenu(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		
		List<String[]> menuItems = new ArrayList<>();
		menuItems.add( new String[] {"clientData", "Datos del Cliente"} );
		menuItems.add( new String[] {"pageHeaders", "Cabeceras de la Página"} );
		menuItems.add( new String[] {"pageContent", "Contenido de la Página"} );

		ModelAndView mv = new ModelAndView("menu");
		mv.addObject("menuTitle", "Gestión de Contenidos");
		mv.addObject("menuItems", menuItems);
		mv.addObject("menuGeneral", false);
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		return mv;
	}
	
	/**
	 * Show the contacts screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/contacts")
	private ModelAndView contacts(HttpServletRequest request) throws Exception {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		List<Contact> contacts = contactRepository.findByPage(page);

		ModelAndView mv = new ModelAndView("contacts");
		mv.addObject("contactList", contacts);
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		return mv;
	}

	/**
	 * Show the password change screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/password")
	public ModelAndView password(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");

		ModelAndView mv =  new ModelAndView("password");
		mv.addObject("loginfo", getLoginfo());
		mv.addObject("pageName", pageName);
		return mv;
	}

	/**
	 * Show the client data maintenance screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/clientData")
	public ModelAndView clientData(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		
		ModelAndView mv =  new ModelAndView("clientData");
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * Show the page headers maintenance screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/pageHeaders")
	public ModelAndView pageHeaders(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		
		ModelAndView mv =  new ModelAndView("pageHeaders");
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * Show the page content maintenance screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/pageContent")
	public ModelAndView pageContent(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		
		ModelAndView mv =  new ModelAndView("pageContent");
		mv.addObject("pageName", pageName);
		mv.addObject("loginfo", getLoginfo());
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * Show the sections configuration screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/sectionsConfig")
	public ModelAndView sectionsConfig(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		List<Section> sections = sectionRepository.findByPage(page);
		
		ModelAndView mv =  new ModelAndView("sectionsConfig");
		mv.addObject("loginfo", getLoginfo());
		mv.addObject("sectionList", sections);
		mv.addObject("pageName", pageName);
		return mv;
	}
	
	/**
	 * Show the new section screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/newSection")
	public ModelAndView newSection(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		// Set the fckeditor json file with the list of images:
		String uploadFolder = Application.BASE + Application.PAGES + page.getId();
		String listOfFiles = uploadFolder + "/" + ManagerService.LIST_OF_FILES;
		
		ModelAndView mv =  new ModelAndView("newSection");
		mv.addObject("pageName", pageName);
		mv.addObject("listOfFiles", listOfFiles);
		mv.addObject("loginfo", getLoginfo());
		return mv;
	}
	
	/**
	 * Show the edit section screen
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/admin/editSection")
	public ModelAndView editSection(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		// Set the fckeditor json file with the list of images:
		String uploadFolder = Application.BASE + Application.PAGES + page.getId();
		String listOfFiles = uploadFolder + "/" + ManagerService.LIST_OF_FILES;

		Integer id = new Integer(request.getParameter("section"));

		ModelAndView mv =  new ModelAndView("editSection");
		mv.addObject("pageName", pageName);
		mv.addObject("section", sectionRepository.findOne(id));
		mv.addObject("listOfFiles", listOfFiles);
		mv.addObject("loginfo", getLoginfo());
		return mv;
	}

	// PROCESS REQUESTS -----------------------------------------------------------------------------

	/**
	 * Change the password (and logout)
	 * @param request
	 * @return ModelAndView (password)
	 */
	@RequestMapping("/admin/changePassword") 
	public ModelAndView changePassword(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");

		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String repeatPassword = request.getParameter("repeatPassword");

		ModelAndView mv;
		try {
			managerService.changePassword(currentPassword, newPassword, repeatPassword);
			
			request.logout();

			// Go to login page:
			mv = new ModelAndView("login");
			mv.addObject("passwordChanged", "true");
		    mv.addObject("pageName", pageName);
		} 
		catch(Exception e) {
			mv = new ModelAndView("password");
			mv.addObject("pageName", pageName);
			mv.addObject("error", e.getMessage());
		}
		return mv;
	}	
	
	/**
	 * Update the client data
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/updateClient")
	public ModelAndView updateClient(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		boolean error = false;
		String errorMsg = null;
		try {
			managerService.update(request.getParameterMap(), pageName);
		} 
		catch(Exception e) {
			error = true;
			errorMsg = e.getMessage();
		}

		ModelAndView mv = new ModelAndView("clientData");		
		mv.addObject("pageName", pageName);
		mv.addObject("page", page);
		mv.addObject("loginfo", getLoginfo());

		if (error) {
			mv.addObject("error", errorMsg);
		} else {
			mv.addObject("message", "Actualizado");
		}
		return mv;
	}
	
	/**
	 * Update the page headers
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/updateHeaders")
	public ModelAndView updateHeaders(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		boolean error = false;
		String errorMsg = null;
		try {
			managerService.update(request.getParameterMap(), pageName);
		} 
		catch(Exception e) {
			error = true;
			errorMsg = e.getMessage();
		}

		ModelAndView mv = new ModelAndView("pageHeaders");		
		mv.addObject("pageName", pageName);
		mv.addObject("page", page);
		mv.addObject("loginfo", getLoginfo());

		if (error) {
			mv.addObject("error", errorMsg);
		} else {
			mv.addObject("message", "Actualizado");
		}
		return mv;
	}
	
	/**
	 * Update the page content AND upload the logo image (if selected).
	 * @param request
	 * @return ModelAndView (the calling page: clientData, pageHeaders or pageContent)
	 */
	@RequestMapping("/admin/updateContent")
	public ModelAndView updateContent(HttpServletRequest request) throws Exception {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
        
		// Storage for parameters: content data AND logo image
        Map<String, String[]> parameters = new HashMap<>();
        FileItem logoItem = null;
		
		// Check that this is a file upload request:
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new Exception("no multipart content");
		}
        
        // Parse the request to extract parameters:
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> formItems = upload.parseRequest(request);
		if (formItems != null) {
			for (FileItem item : formItems) {

				// 1. Logo image:
				if (!item.isFormField()) {

					// Check if an image has been selected:
					if (item.getName() == null || item.getName().trim().length() == 0) {
						continue;
					}

					// Store for later process:
					logoItem = item;
				}
				
				// 2. Content data:
				else {
					
			    	String fieldName = item.getFieldName();
			        String[] fieldValue = {item.getString("utf8")};
			        
			        // Control of duplicated booleans:
			        if (fieldValue[0].equals(Boolean.FALSE.toString()) && parameters.containsKey(fieldName)) {
			        	continue;
			        }
		
			        // Store for later process:
			        parameters.put(fieldName, fieldValue);
				}
			}
		}

		boolean error = false;
		String errorMsg = null;
		try {
			// 1.Process the upload:
			if (logoItem != null) {
				managerService.uploadLogo(logoItem, pageName);
			}
			
			// 2. Process the update:
			managerService.update(parameters, pageName);
		} 
		catch(Exception e) {
			error = true;
			errorMsg = "Error: " + e.getMessage();
		}

		ModelAndView mv = new ModelAndView("pageContent");		
		mv.addObject("pageName", pageName);
		mv.addObject("page", page);
		mv.addObject("loginfo", getLoginfo());

		if (error) {
			mv.addObject("error", errorMsg);
		} else {
			mv.addObject("message", "Actualizado");
		}
		return mv;
	}

	/**
	 * Upload images to be used in the Sections.
	 * @param request
	 * @return ModelAndView (sectionsConfig)
	 */
	@RequestMapping("/admin/upload")
	public ModelAndView upload(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		String error = null;
		String message = null;
		try {
			message = managerService.uploadImages(request, page);
		} 
		catch(Exception e) {
			error = e.getMessage();
		}

		// Return the sectionsConfig page:
		ModelAndView mv = new ModelAndView("sectionsConfig");
		if (error != null) {
			mv.addObject("error", error);
		} else {
			mv.addObject("message", message);
		}
		mv.addObject("pageName", pageName);
		mv.addObject("sectionList", sectionRepository.findByPage(page));
		return mv;
	}

	/**
	 * Create a Section
	 * @param request
	 * @return ModelAndView (sectionsConfig)
	 */
	@RequestMapping("/admin/createSection")
	public ModelAndView createSection(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);

		ModelAndView mv = new ModelAndView("sectionsConfig");
		Integer sectionId = null;
		try {
			sectionId = managerService.createSection(request, page);
			mv.addObject("message", "Sección creada");
			logger.info("Section created: " + sectionId);
		}
		catch(Exception e) {
			mv.addObject("error", "Error: " + e.getMessage());
			logger.error("Error creating section " + sectionId + ": " + e.getMessage());
		}
		mv.addObject("pageName", pageName);
		mv.addObject("sectionList", sectionRepository.findByPage(page));
		return mv;
	}
	
	/**
	 * Update a Section
	 * @param request
	 * @return ModelAndView (sectionsConfig)
	 */
	@RequestMapping("/admin/updateSection")
	public ModelAndView updateSection(HttpServletRequest request) {
		String pageName = Util.getCookie(request, "pageName");
		Page page = pageRepository.findByPageName(pageName);
		
		ModelAndView mv = new ModelAndView("sectionsConfig");
		try {
			managerService.updateSection(request, page);
			mv.addObject("message", "Sección modificada");
		} 
		catch(Exception e) {
			mv.addObject("error", "Error: " + e.getMessage());
		}
		mv.addObject("pageName", pageName);
		mv.addObject("sectionList", sectionRepository.findByPage(page));
		return mv;
	}

	/**
	 * Remove a Section.
	 * >>> AJAX call
	 * @param request
	 * @return "ok" or an error message
	 */
	@RequestMapping("/admin/removeSection")
	@ResponseBody
	public String removeSection(HttpServletRequest request) {
		Integer id = null;
		try {
			id = new Integer(request.getParameter("id"));
			sectionRepository.delete(id);
			logger.info("Section removed: " + id);
			return "ok";
		}
		catch(Exception e) {
			logger.error("Error removing section " + id + ": " + e.getMessage());
			return e.getMessage();
		}
	}
	
	/**
	 * Control if a Section must be shown or hidden
	 * >>> AJAX call
	 * @param request
	 * @return "ok" or an error message
	 */
	@RequestMapping("/admin/toggleShowSection")
	@ResponseBody
	public String toggleShowSection(HttpServletRequest request) {
		try {
			Integer id = new Integer(request.getParameter("id"));
			Section section = sectionRepository.findOne(id);
			section.setShowSection(!section.getShowSection());
			sectionRepository.save(section);
			return "ok";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Control if a link to a Section must be included in the menu options 
	 * >>> AJAX call
	 * @param request
	 * @return "ok" or an error message
	 */
	@RequestMapping("/admin/toggleShowInMenu")
	@ResponseBody
	public String toggleShowInMenu(HttpServletRequest request) {
		try {
			Integer id = new Integer(request.getParameter("id"));
			Section section = sectionRepository.findOne(id);
			section.setShowInMenu(!section.getShowInMenu());
			sectionRepository.save(section);
			return "ok";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}

	// ------------------------------------------------------------------------------
	
	/**
	 * Exception management.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleCustomException(Exception e, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("error", e.getClass().getName());
		model.addObject("message", e.getMessage());
		model.addObject("timestamp", new SimpleDateFormat().format(new Date()));
		return model;
	}

	/**
	 * Build the logout button and user info
	 * @param userName
	 * @return
	 */
	private String getLoginfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth instanceof AnonymousAuthenticationToken) {
			return "";
		}
		String user = auth.getName();

		StringBuilder sb = new StringBuilder();
		sb.append(line("<span id='username' class='pconly'>" + user + "</span>", 4));
		sb.append(line("<a href='/denia/logout' rel='nofollow'>", 4));
		sb.append(line("<img src='/denia/resources/common/images/logout.png' alt='logout' title='Desconectar' style='vertical-align:middle;'>", 5));
		sb.append(line("<span class='mobileonly'>Desconectar</span>", 5));
		sb.append(line("</a>", 4));
		return sb.toString();
	}
}
