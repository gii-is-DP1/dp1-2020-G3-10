package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pelicula;

/**
 * @author Marta Díaz
 */

public interface PeliculaRepository extends Repository<Pelicula , Integer>{

	
	void save(Pelicula pelicula);
	List<Pelicula> findAll();
	Optional<Pelicula> findById(int id);
	void deleteById(int id);	
	void delete(Pelicula p);


}
