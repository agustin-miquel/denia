package com.amr.denia.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

/**
 * The configurable sections in the client's page
 * @author amr
 */
@Entity
public class Section {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Page page;
    private String title;
    @NotNull
    @Type(type="text")
    private String content;
	private Boolean showSection;
	private Boolean showInMenu;

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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getShowSection() {
		return showSection;
	}
	public void setShowSection(Boolean showSection) {
		this.showSection = showSection;
	}
	public Boolean getShowInMenu() {
		return showInMenu;
	}
	public void setShowInMenu(Boolean showInMenu) {
		this.showInMenu = showInMenu;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Section other = (Section) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + "]";
	}
}
