package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {
	
private ComentarioRepository comentarioRepository;
	
	@Transactional
	public int commentCount() {
		return (int) comentarioRepository.count();
	}
	
	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository) {
		this.comentarioRepository = comentarioRepository;
	}
	
	@Transactional(readOnly = true)
	public Comentario findCommentById(int id) throws DataAccessException{
		return comentarioRepository.findById(id);
	}
	
	@Transactional public void saveComment(Comentario comentario) {
		comentarioRepository.save(comentario);
	}
	
	@Transactional 
	public void deleteComment(Comentario comentario) {
		comentarioRepository.delete(comentario);
	}
	
	@Transactional
	public Iterable<Comentario> findAll(){
		return comentarioRepository.findAll();
	}

}
