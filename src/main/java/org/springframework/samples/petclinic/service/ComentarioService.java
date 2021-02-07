package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
public class ComentarioService {
	
private ComentarioRepository comentarioRepository;
	
	
	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository) {
		this.comentarioRepository = comentarioRepository;
	}
	
	@Transactional(readOnly = true)
	public Comentario findCommentById(int id) throws DataAccessException{
		return comentarioRepository.findById(id);
	}
	
	@Transactional
	public List<Comentario> findByClientId(int id) throws DataAccessException{
		return comentarioRepository.findByClientId(id);
	}
	
	@Transactional 
	@Validated(Default.class)
	public void saveComment(@Valid Comentario comentario) {
		comentarioRepository.save(comentario);
	}
	
	@Transactional 
	public void deleteComment(Comentario comentario) {
		comentarioRepository.delete(comentario);
	}
	
	@Transactional
	public List<Comentario> findAll(){
		return comentarioRepository.findAll();
	}
	
	@Transactional
	public List<Comentario> findComentariosByPeliculaId(int id) throws DataAccessException{
		return comentarioRepository.findComentariosByPeliculaId(id);
	}
	
	@Transactional
	public List<Comentario> findComentariosByVideojuegoId(int id) throws DataAccessException{
		return comentarioRepository.findComentariosByVideojuegoId(id);
	}
	
	@Transactional
	public List<Comentario> findComentariosByMerchandasingId(int id) throws DataAccessException{
		return comentarioRepository.findComentariosByMerchandasingId(id);
	}

}
