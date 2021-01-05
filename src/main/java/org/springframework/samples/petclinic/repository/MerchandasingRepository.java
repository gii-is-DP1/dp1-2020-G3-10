package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Merchandasing;


public interface MerchandasingRepository extends CrudRepository<Merchandasing , Integer> {

//	void save(Merchandasing merchandasing) throws DataAccessException;

	Merchandasing findById(int id) throws DataAccessException;

	List<Merchandasing> findAll() throws DataAccessException;

	void deleteById(int id) throws DataAccessException;
}
