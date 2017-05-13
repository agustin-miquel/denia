package com.amr.denia.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.Body;

/**
 * Repository functions for Body entity.
 * @author amr
 */
public interface BodyRepository extends CrudRepository<Body, Integer> {

	public Body findByPage_id(int page_id);

}
