package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Merchandasing;

public interface MerchandasingRepository extends CrudRepository<Merchandasing, Integer> {


	Optional<Merchandasing> findById(int id);
	List<Merchandasing> findAll();
	void deleteById(int id);
	void delete(Merchandasing m);
}
