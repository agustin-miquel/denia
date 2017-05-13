package com.amr.denia.pagerender;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.repository.PageRepository;
import com.amr.denia.util.Util;

/**
 * Controller for the rendering of the client's page.
 * @author amr
 */
@Controller
public class PageController {
	
	@Autowired
	PageBuilder pageBuilder;

	@Autowired
	PageRepository pageRepository;

	@Autowired
	PageService pageService;

	@Value("${index_page}")
	private String indexPage;

	public static final String DEFAULT = "default";
	
	/**
	 * Show the Home page (or specific pages, when configured).
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping({ "/" })
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Show specific page if set in the application properties:
		if (indexPage != null && !indexPage.equals(DEFAULT)) {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");

			if (pageRepository.findByPageName(indexPage) == null) {
				out.print("Página no encontrada: " + indexPage);
			}

			String url = request.getRequestURL().toString();
			if (url.endsWith("/"))
				url += indexPage;
			else
				url += "/" + indexPage;

			String pageContent = pageBuilder.getContent(url, indexPage);
			out.println(pageContent);
			return null;
		}

		// Show default index page:
		Iterable<Page> pages = pageRepository.findAll();
		List<Page> pages2 = new ArrayList<>();
		for (Page page : pages) {
			pages2.add(page);
		}
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("pages", pages2);
		return mv;
	}

	/**
	 * Custom web pages.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping({ "/*" })
	public void pages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		// Get the name of the requested page:
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String pageName = "";
		try {
			if (uri.endsWith("/")) {
				uri = uri.substring(0, uri.length() - 1);
			}
			pageName = uri.substring(uri.lastIndexOf("/") + 1);
			
			// Store the name of the page (this will be used in the content manager, to go back to that page) 
			Util.setCookie(request, response, "pageName", pageName);

		} catch (Exception e) {
			out.print("Página no encontrada: " + pageName);
			return;
		}

		// Retrieve the page content:
		String pageContent = pageBuilder.getContent(url, pageName);

		response.setContentType("text/html");
		out.println(pageContent);
	}

	/**
	 * Process the Contact form
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public void contact(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageName = request.getParameter("pageName");
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf("contact")) + pageName;

		Map<String, String[]> params = request.getParameterMap();
		String name = params.get("name")[0]; 
		String email = params.get("email")[0];
		String phone = params.get("phone")[0];
		String description = params.get("description")[0];
		
		pageService.contact(pageName, name, email, phone, description);

		// Show again the page:
		String pageContent = pageBuilder.getContent(url, pageName);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(pageContent);
	}

	// -----------------------------------------------------

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
}
