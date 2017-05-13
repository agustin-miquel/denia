package com.amr.denia.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

/**
 * Body of the page
 * @author amr
 */
@Entity
public class Body {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Page page;
    @NotNull
	private String h1;
    @Type(type="text")
	private String h2;
	private Boolean showSections;
	private Boolean showContact;
	private Boolean showMap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getH1() {
		return h1;
	}
	public void setH1(String h1) {
		this.h1 = h1;
	}
	public String getH2() {
		return h2;
	}
	public void setH2(String h2) {
		this.h2 = h2;
	}
	public Boolean getShowSections() {
		return showSections;
	}
	public void setShowSections(Boolean showSections) {
		this.showSections = showSections;
	}
	/**
	 * setter from String
	 */
	public void setShowSections(String showSections) {
		this.showSections = new Boolean(showSections);
	}
	public Boolean getShowContact() {
		return showContact;
	}
	public void setShowContact(Boolean showContact) {
		this.showContact = showContact;
	}
	/**
	 * setter from String
	 */
	public void setShowContact(String showContact) {
		this.showContact = new Boolean(showContact);
	}
	public Boolean getShowMap() {
		return showMap;
	}
	public void setShowMap(Boolean showMap) {
		this.showMap = showMap;
	}
	/**
	 * setter from String
	 */
	public void setShowMap(String showMap) {
		this.showMap = new Boolean(showMap);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id != other.id)
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Body [id=" + id + "]";
	}

}
