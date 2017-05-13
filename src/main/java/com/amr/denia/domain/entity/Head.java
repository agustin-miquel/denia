package com.amr.denia.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

/**
 * Head of the page
 * @author amr
 */
@Entity
public class Head {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Page page;
    @NotNull
	private String title;
    @Type(type="text")
	private String description;
	private Boolean indexed;
	private Boolean facebook;
	private Boolean twitter;
	private String twitterTag;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIndexed() {
		return indexed;
	}
	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}
	/**
	 * setter from String
	 */
	public void setIndexed(String indexed) {
		this.indexed = new Boolean(indexed);
	}
	public Boolean getFacebook() {
		return facebook;
	}
	public void setFacebook(Boolean facebook) {
		this.facebook = facebook;
	}
	/**
	 * setter from String
	 */
	public void setFacebook(String facebook) {
		this.facebook = new Boolean(facebook);
	}
	public Boolean getTwitter() {
		return twitter;
	}
	public void setTwitter(Boolean twitter) {
		this.twitter = twitter;
	}
	/**
	 * setter from String
	 */
	public void setTwitter(String twitter) {
		this.twitter = new Boolean(twitter);
	}
	public String getTwitterTag() {
		return twitterTag;
	}
	public void setTwitterTag(String twitterTag) {
		this.twitterTag = twitterTag;
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
		Head other = (Head) obj;
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
		return "Head [id=" + id + "]";
	}
}
