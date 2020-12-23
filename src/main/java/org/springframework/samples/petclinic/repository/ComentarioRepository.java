package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer>{
	//"SELECT comentario FROM Comentario comentario WHERE comentario.cliente.id =:id
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.cliente.id =:id")
	public List<Comentario> findByClientId(@Param("id") int id);
//SELECT comentario FROM Comentario comentario WHERE CLIENTE_ID =:id
}
