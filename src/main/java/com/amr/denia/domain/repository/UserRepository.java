package com.amr.denia.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.User;

/**
 * Repository functions for User entity.
 * @author amr
 */
public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByName(String name);

}
