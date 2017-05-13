package com.amr.denia.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.Contact;
import com.amr.denia.domain.entity.Page;

/**
 * Repository functions for Contact entity.
 * @author amr
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {
	
	public List<Contact> findByPage(Page page);

}
