package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.repository.PeliculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marta Díaz
 */

@Service
public class PeliculaService {

	@Autowired
	private PeliculaRepository peliculaRepository;

	public PeliculaService(PeliculaRepository peliculaRepository) {
		this.peliculaRepository = peliculaRepository;
	}

	@Transactional
	public void savePelicula(@Valid Pelicula pelicula) throws DataAccessException {
		peliculaRepository.save(pelicula);

	}

	@Transactional(readOnly = true)
	public List<Pelicula> findPeliculas() throws DataAccessException {
		return peliculaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Pelicula findPeliculaById(int id) throws IllegalArgumentException {
		Optional<Pelicula> pelicula = this.peliculaRepository.findById(id);

		if (pelicula.isPresent()) {
			return pelicula.get();
		} else {
			throw new IllegalArgumentException("La pelicula no existe");

		}

	}

	@Transactional
	public void deletePelicula(int peliculaId) throws IllegalArgumentException {
		Optional<Pelicula> pelicula = this.peliculaRepository.findById(peliculaId);
		if(pelicula.isPresent()) {
			peliculaRepository.deleteById(peliculaId);
		}else {
			throw new IllegalArgumentException("La pelicula que desea borrar no existe");
		}
		

	}

	@Transactional
	public void delete(Pelicula p) throws DataAccessException {
		Optional<Pelicula> pelicula = this.peliculaRepository.findById(p.getId());
		if(pelicula.isPresent()) {
			peliculaRepository.delete(p);
		}else {
			throw new IllegalArgumentException("La pelicula que desea borrar no existe");
		}
	}
	
	public Collection<Formato> getFormatos(){
		Collection<Formato> formatos = new ArrayList<Formato>();
		for(Formato f: Formato.values()) {
			formatos.add(f);
		}
		return formatos;
	}
}
