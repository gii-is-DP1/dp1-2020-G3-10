package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.repository.PeliculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeliculaService {
	
	private PeliculaRepository peliculaRepository;
	
	@Autowired
	private ProductoService productoService;
	
	public PeliculaService(PeliculaRepository peliculaRepository) {
		this.peliculaRepository = peliculaRepository;
	}

//	@Transactional(readOnly = true)
//	public Pelicula findPeliculaById(int id) throws DataAccessException{
//		return this.peliculaRepository.findById(id);
//	}
//	
//	
//	@Transactional
//	public void savePelicula(Pelicula pelicula) throws DataAccessException{
//		peliculaRepository.save(pelicula);
//		productoService.saveProducto(pelicula.getProducto());
//	}
	
	@Transactional(readOnly = true)	
	public Collection<Pelicula> findPeliculas() throws DataAccessException{
		return peliculaRepository.findAll();
	}
}
