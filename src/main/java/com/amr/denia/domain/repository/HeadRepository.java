package com.amr.denia.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.Head;

/**
 * Repository functions for Head entity.
 * @author amr
 */
public interface HeadRepository extends CrudRepository<Head, Integer> {

	public Head findByPage_id(int page_id);
}
