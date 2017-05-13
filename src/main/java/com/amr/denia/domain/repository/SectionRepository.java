package com.amr.denia.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.amr.denia.domain.entity.Page;
import com.amr.denia.domain.entity.Section;

/**
 * Repository functions for Section entity.
 * @author amr
 */
public interface SectionRepository extends CrudRepository<Section, Integer> {

//	@Query("select s from Section s where s.page.id = %?1%")
//	public List<Section> findByPageId(int id);

	public List<Section> findByPage(Page page);
}
