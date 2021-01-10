package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Pelicula;

public interface ComentarioRepository extends Repository<Comentario, Integer>{
	
	void save(Comentario comentario) throws DataAccessException;
	
	List<Comentario> findAll() throws DataAccessException;

	Comentario findById(int id) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	void delete(Comentario c) throws DataAccessException;
	
	
	//"SELECT comentario FROM Comentario comentario WHERE comentario.cliente.id =:id
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.cliente.id =:id")
	public List<Comentario> findByClientId(@Param("id") int id);
//SELECT comentario FROM Comentario comentario WHERE CLIENTE_ID =:id
	
}
