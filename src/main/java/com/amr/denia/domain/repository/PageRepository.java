package com.amr.denia.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.Page;

/**
 * Repository functions for Page entity.
 * @author amr
 */
public interface PageRepository extends CrudRepository<Page, Integer> {

	public Page findByPageName(String pageName);
}
