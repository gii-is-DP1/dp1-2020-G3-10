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

	
	void save(Pelicula pelicula) throws DataAccessException;
	
	List<Pelicula> findAll() throws DataAccessException;

	Pelicula findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void delete(Pelicula p) throws DataAccessException;


}
