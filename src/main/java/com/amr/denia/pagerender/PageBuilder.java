package com.amr.denia.pagerender;

import static com.amr.denia.util.Util.endtag;
import static com.amr.denia.util.Util.tag;
import static com.amr.denia.util.Util.line;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amr.denia.Application;
import com.amr.denia.domain.entity.Address;
import com.amr.denia.domain.entity.Body;
import com.amr.denia.domain.entity.Head;
import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.entity.Section;
import com.amr.denia.domain.repository.PageRepository;
import com.amr.denia.domain.repository.SectionRepository;
import com.amr.denia.util.Util;

/**
 * Functions to build the client's page. 
 * @author amr
 */
@Service
public class PageBuilder {

	@Autowired
    ServletContext servletContext; 

	@Autowired
	PageRepository pageRepository;

	@Autowired
	SectionRepository sectionRepository;

	// PAGE values:
	
	public static String DOCTYPE = "<!DOCTYPE html>";
	public static String HTML = "<html lang='es-ES' prefix='og: http://ogp.me/ns#'>";
	public static String ENDHTML = "</html>";

	// HEAD values:
	
	static String IMAGES = "resources/pages/";
	static String SOCIAL_NETWORKS_IMAGE = "social-networks-image.jpg";
	
	static String HEAD = "head";

	static String CHARSET = "<meta charset='UTF-8'/>";
	static String VIEWPORT = "<meta name='viewport' content='width=device-width,initial-scale=1'/>";
	static String ICON = "<link rel='shortcut icon' href='favicon.ico'/>";
	static String JS = "<script src='/denia/resources/common/pages.js'></script>";
	
	static String TITLE = "title";
	static String DESC = "<meta name='description' content='";
	
	static String ROBOTS = "<meta name='robots' content='index,follow,noodp'/>";
	static String NO_ROBOTS = "<meta name='robots' content='noindex,nofollow,noodp'/>";
	static String CANONICAL = "<link rel='canonical' href='";
	static String SHORTLINK = "<link rel='shortlink' href='";

	static String OG_LOCALE = "<meta property='og:locale' content='es_ES'/>";
	static String OG_TYPE = "<meta property='og:type' content='website'/>";
	static String OG_TITLE = "<meta property='og:title' content='";
	static String OG_DESC = "<meta property='og:description' content='";
	static String OG_URL = "<meta property='og:url' content='";
	static String OG_SITE = "<meta property='og:site_name' content='";
	static String OG_IMAGE = "<meta property='og:image' content='";

	static String TW_CARD = "<meta name='twitter:card' content='summary'/>";
	static String TW_TITLE = "<meta name='twitter:title' content='";
	static String TW_DESC = "<meta name='twitter:description' content='";
	static String TW_SITE = "<meta name='twitter:site' content='";
	static String TW_URL = "<meta name='twitter:url' content='";
	static String TW_IMAGE = "<meta name='twitter:image' content='";
	static String TW_CREATOR = "<meta name='twitter:creator' content='";

	static String CSS = "<link rel='stylesheet' href='resources/common/pages.css' type='text/css' media='all'/>";
	static String FONT_CSS = "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:400,400i,700'/>";

	static String END = "'/>";

	// BODY VALUES:
	
	public static final String LOGO = "logo";
	public static String CONTACT_PAGE = "/contact";
	
	static String BODY = "body";
	static String PAGE_DIV = "<div id='page'>";
	static String BANNER = "<header id='banner' role='banner'>";
	static String LOGOMOBILE = "<a href='/denia'><img id='logo' class='mobileonly' src='";
	static String LOGOPC = "<a href='/denia'><img id='logo' class='pconly' src='"; 
	static String CONTACT = "<a href='#contact' class='button'>";
	static String BUTTONWRAP = "<div class='button-wrap'>";

	static String MAIN = "<section id='main' role='main'>";
	static String H1 = "h1";
	static String H2 = "h2";

	static String BLOQUES = "<div id='blocks'>";

	static String CONTACTBLOCK = "<div id='contact'>";
	static String CONTACT_TITLE = "<h4 class='section-title'><span>Solicitud de Presupuesto:</span></h4>";
	static String CONTACT_FORM = "<form id='contactForm' action='contact' method='post'>";
	static String CONTACT0 = "<input type='hidden' name='pageName' value='";
	static String CONTACT1 = "<input type='text' name='name' placeholder='Nombre' required/>";
	static String CONTACT2 = "<input type='email' name='email' placeholder='Correo electrónico'/>";
	static String CONTACT3 = "<input type='text' name='phone' placeholder='Teléfono'/>";
	static String CONTACT4 = "<textarea name='description' placeholder='¿Qué necesitas?' required></textarea>";
	static String SUBMIT = "<button type='submit' class='button' onclick='return validateContactForm();'>Enviar</button>";
	
	static String MAP_TITLE = "<h4 class='section-title'><span>Dónde estamos:</span></h4>";
	
	static String FOOTER = "<footer id='footer' role='contentinfo'>";
	static String HCARD_DIV = "<div class='hcard'>";
	
	static String GOOGLEMAP_APIKEY = "AIzaSyC5oZfGoLNpLggdTCyCQrrDDE-B2fYNmrs";
	
	static String ENDBANNER = "</header>";
	static String ENDFOOTER = "</footer>";
	static String ENDFORM = "</form>";
	static String ENDMAIN = "</section>";
	static String ENDLINK = "</a>";
	static String ENDDIV = "</div>";
	static String ENDPAGE = ENDDIV;
	static String ENDBLOQUES = ENDDIV;
	static String ENDCONTACTBLOCK = ENDDIV;
	static String ENDTAG = "'>";
	
	/**
	 * Build and return the content of the web page.
	 * @param page the name of the page
	 * @return the content of the page
	 */
	public String getContent(String url, String pageName) {
		
		// Retrieve page data from the database:
		Page page = pageRepository.findByPageName(pageName);
		if (page == null) {
			return "Page not found: " + pageName;
		}

		return 	line(DOCTYPE) +
				line(HTML) +
				buildHead(url, page.getHead()) +
				buildBody(page) +
				line(ENDHTML);
	}
	
	// HEAD -----------------------------------------------------------------
	
	private String buildHead(String url, Head head) {
		return  line(tag(HEAD)) +
				line(CHARSET, 1) +
				line(VIEWPORT, 1) +
				line(ICON, 1) +
				
				line(tag(TITLE) + head.getTitle() + endtag(TITLE), 1) +
				line(DESC + head.getDescription() + END, 1) +
				
				doRobots(head) +
				line(CANONICAL + url + END, 1) +
				line(SHORTLINK + url + END, 1) +

				doFacebook(url, head) +
				
				doTweeter(url, head) +

				line(CSS, 1) +
				line(FONT_CSS, 1) +
				line(JS, 1) +
				line(endtag(HEAD));
	}
	
	private String doRobots(Head head) {
		if (head.getIndexed()) {
			return line(ROBOTS, 1);
		} else {
			return line(NO_ROBOTS, 1);
		}
	}
	
	private String doFacebook(String url, Head head) {
		if (!head.getFacebook()) {
			return "";
		} else {
			String s = line(OG_LOCALE, 1) +
					line(OG_TYPE, 1) +
					line(OG_TITLE + head.getTitle() + END, 1) +
					line(OG_DESC + head.getDescription() + END, 1) +
					line(OG_URL + url + END, 1) +
					line(OG_SITE + url + END, 1);
			
			String base = url.substring(0, url.indexOf(head.getPage().getPageName()));
			String image = base + IMAGES + head.getPage().getId() + "/" + SOCIAL_NETWORKS_IMAGE;
			if  (Util.resourceExists(image)) {
				s += line(OG_IMAGE + image + END, 1);
			}
			return s;
		}
	}
	
	private String doTweeter(String url, Head head) {
		if (!head.getTwitter() || head.getTwitterTag() == null || head.getTwitterTag().trim().length() == 0) {
			return ("");
		} else {
			String s = line(TW_CARD, 1) +
					line(TW_TITLE + head.getTitle() + END, 1) +
					line(TW_DESC + head.getDescription() + END, 1) +
					line(TW_URL + url + END, 1) +
					line(TW_SITE + head.getTwitterTag() + END, 1) +
					line(TW_CREATOR + head.getTwitterTag() + END, 1);
			
			String base = url.substring(0, url.indexOf(head.getPage().getPageName()));
			String image = base + IMAGES + head.getPage().getId() + "/" + SOCIAL_NETWORKS_IMAGE;
			if  (Util.resourceExists(image)) {
				s += line(TW_IMAGE + image + END, 1);
			}
			return s;
		}
	}
	
	// BODY ---------------------------------------------------------------------
	
	private String buildBody(Page page) {
		Body body = page.getBody();
		
		return	line(tag(BODY)) +
				
				line(PAGE_DIV, 1) +
				
				doBanner(page) +
				
				line(MAIN, 2) +

				line(tag(H1) + body.getH1() + endtag(H1), 3) +
				line(tag(H2) + body.getH2() + endtag(H2), 3) +

				doSections(page) +

				doContact(page) +

				doMap(page) +

				line(ENDMAIN, 2) +

				doFooter(page) +
				
				line(ENDPAGE, 1) +
				
				line(endtag(BODY));
	}
	
	private String doBanner(Page page) {
		StringBuilder sb = new StringBuilder();

        // Build the path of the uploaded files:
        String uploadFolder = Application.PAGES + page.getId();
        String uploadPath;
        if (servletContext.getRealPath("").endsWith(File.separator)) {
        	uploadPath = servletContext.getRealPath("") + uploadFolder;
        } else {
        	uploadPath = servletContext.getRealPath("") + File.separator + uploadFolder;
        }
		
		String logoName = Util.getImageFile(uploadPath, LOGO);

		String logo = "/" + logoName + "' alt='home' title='Directorio'/></a>";
		
		sb.append(line(BANNER, 2));
		sb.append(line(LOGOPC + Application.PAGES + page.getId() + logo, 3));

		sb.append(doMenu(page));
		
		sb.append(line(LOGOMOBILE + Application.PAGES + page.getId() + logo, 3));
		sb.append(line(ENDBANNER, 2));
		return sb.toString();
	}
	
	private String doMenu(Page page) {
		Body body = page.getBody();

		StringBuilder sb = new StringBuilder();
		sb.append(line("<nav id='navigation' role='navigation'>", 3));
		sb.append(line("<ul id='menu' class='topnav'>", 4));
		sb.append(line("<li class='icon'><a href='javascript:void(0);' onclick='openMenu()'>&#9776;</a></li>", 5));		
				
		if (body.getShowSections()) {
			for (Section section : sectionRepository.findByPage(page)) {
				if (section.getShowSection() && section.getShowInMenu()) {
					String item = section.getTitle();
					if (item != null && item.trim().length() > 0) {
						sb.append(line("<li><a href='#" + item + "'>" + item + "</a></li>", 5));
					}
				}
			}
		}

		if (body.getShowMap()) {
			sb.append(line("<li><a href='#map'>Dónde estamos</a></li>", 5));
		}
		if (body.getShowContact()) {
			sb.append(line("<li><a href='#contact' class='menubutton'>¡Contacta ahora!</a></li>", 5));
		}

		// Login:

		sb.append(line("<li>", 5));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		sb.append(line("<a id='admin' href='/denia/admin/menu' rel='nofollow'>", 6));
		
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			String user = auth.getName();
			sb.append(line("<img src='resources/common/images/login-gr.png' alt='login' title='Administración [" + user + "]'>", 7));
		} else {
			sb.append(line("<img src='resources/common/images/login.png' alt='login' title='Administración'>", 7));
		}
		sb.append(line("<span class='mobileonly'>Administración</span>", 7));
		sb.append(line("</a>", 6));
		sb.append(line("</li>", 5));

		sb.append(line("</ul>", 4));				
		sb.append(line("</nav>", 3));
		return sb.toString();
	}
	
	private String doSections(Page page) {
		Body body = page.getBody();
		
		if (!body.getShowSections()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder("			" + BLOQUES + "\n");
		
		List<Section> sections = sectionRepository.findByPage(page);
		
		for (Section section : sections) {
			if (!section.getShowSection()) {
				continue;
			}
			String sectionId = section.getTitle();
			if (sectionId == null) sectionId = "";
			String text = 
						"				<div id = '" + section.getTitle() + "' class='custom-section'>\n" + 
						"			    	<h4 class='section-title'><span>" + section.getTitle() + "</span></h4>\n" +
						"					" + section.getContent() + "\n" + 
						"				</div>\n";
			sb.append(text);
		}
		sb.append("			" + ENDBLOQUES + "\n");
		return sb.toString();
	}
	
	private String doContact(Page page) {
		Body body = page.getBody();
		
		if (!body.getShowContact()) {
			return "";
		}
		return 	line(CONTACTBLOCK, 3) +
				line(CONTACT_TITLE, 4) +
				line(CONTACT_FORM, 4) +
				line(CONTACT0 + page.getPageName() + ENDTAG, 5) +
				line(CONTACT1, 5) +
				line(CONTACT2, 5) +
				line(CONTACT3, 5) +
				line(CONTACT4, 5) +
				line(BUTTONWRAP, 5) +
				line(SUBMIT, 6) +
				line(ENDDIV, 5) +
				line(ENDFORM, 4) +
				line(ENDCONTACTBLOCK, 3);
	}
	
	private String doMap(Page page) {
		Body body = page.getBody();
		
		if (!body.getShowMap() || page.getAddress() == null) {
			return "";
		}

		String address = (page.getAddress().getStreet() + " " + page.getAddress().getTown()).replaceAll(" ", "%20");
				
		StringBuilder sb = new StringBuilder();
		sb.append(line("<div id='map'>", 3));
		sb.append(line(MAP_TITLE, 4));
		sb.append(line("<iframe ", 4));
		sb.append(line("style=\"border:0; width:100%; height:auto; min-height:350px;\" ", 4));
		sb.append(line("src=\"https://www.google.com/maps/embed/v1/place", 4)); 
		sb.append(line("?key=" + GOOGLEMAP_APIKEY, 4)); 
		sb.append(line(" &q=" + address + "\">", 4));
		sb.append(line("</iframe>", 4));
		
		sb.append(line(ENDDIV, 3));
		return sb.toString();
	}
	
	private String doFooter(Page page) {
		Address address = page.getAddress();
		if (address == null) {
			return "";
		}
		return 	line (FOOTER, 2) +
				line(HCARD_DIV, 3) +
				line(getHCard(address), 4) +
				line(ENDDIV, 3) +
				line(ENDFOOTER, 2);
	}
	
	private String getHCard(Address address) {
		return 	"<a class='url fn org' href='' data-unsp-sanitized='clean'>" + address.getName() + 
				"</a>, <span class='adr'><span class='street-address'>" + address.getStreet() +
				"</span> - Telf.: <a class='tel' href='tel:0034" + address.getPhone() +
				"' target='_blank' data-unsp-sanitized='clean'>" + address.getPhone() +
				"</a> - <span class='locality'>" + address.getTown() +
				"</span> <span class='postal-code'>" + address.getPostalCode() + 
				"</span></span>";
	}
}
