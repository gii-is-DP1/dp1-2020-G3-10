package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Videojuego;

/**
 * @author Marta Díaz
 */

public interface VideojuegoRepository extends Repository<Videojuego, Integer>{

	void save(Videojuego videojuego) throws DataAccessException;
	
	List<Videojuego> findAll() throws DataAccessException;
	
	Optional<Videojuego> findVideojuegoById(int id);

	void deleteById(int id) throws DataAccessException;

	void delete(Videojuego videojuego) throws DataAccessException;
}
