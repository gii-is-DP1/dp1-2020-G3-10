package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pelicula;

public interface PeliculaRepository extends Repository<Pelicula , Integer>{

	
	void save(Pelicula pelicula) throws DataAccessException;
	
	Collection<Pelicula> findAll() throws DataAccessException;

//	@Query("SELECT pelicula FROM Pelicula WHERE pelicula.id =:id")
//	public Pelicula findById(@Param("id") int id);

}
