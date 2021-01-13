package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<Comentario> findByClientId(int id) throws DataAccessException{
		return comentarioRepository.findByClientId(id);
	}
	
	@Transactional 
	public void saveComment(Comentario comentario) {
		/*
		comentario.setCliente(clienteService.findClienteById(comentario.getCliente().getId()));
		comentario.setPelicula(peliculaService.findPeliculaById(comentario.getPelicula().getId()));
		
		Cliente cliente = comentario.getCliente();
		Collection<Comentario> comentariosCliente = cliente.getComentarios();
		comentariosCliente.add(comentario);
		cliente.setComentarios(comentariosCliente);
		
		Pelicula pelicula = comentario.getPelicula();
		Collection<Comentario> comentariosPelicula = pelicula.getComentarios();
		comentariosPelicula.add(comentario);
		pelicula.setComentarios(comentariosPelicula);
		
    	
    	this.comentarioRepository.save(comentario);
    	this.clienteService.saveCliente(cliente);
    	this.peliculaService.savePelicula(pelicula);
    	*/
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

}
