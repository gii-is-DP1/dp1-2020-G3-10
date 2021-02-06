package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marta Díaz
 */

@Service
public class VideojuegoService {

	private VideojuegoRepository videojuegoRepository;

	public VideojuegoService(VideojuegoRepository videojuegoRepository) {
		this.videojuegoRepository = videojuegoRepository;
	}

	@Transactional
	public void saveVideojuego(@Valid Videojuego v) throws DataAccessException {
		videojuegoRepository.save(v);
	}

	@Transactional(readOnly = true)
	public List<Videojuego> findVideojuegos() throws DataAccessException {
		return videojuegoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Videojuego findVideojuegoById(int id) throws IllegalArgumentException {
		Optional<Videojuego> videojuego = this.videojuegoRepository.findVideojuegoById(id);
		
		if (videojuego.isPresent()) {
			return videojuego.get();
		} else {
			throw new IllegalArgumentException("El videojuego no existe");

		}
	}

	@Transactional
	public void deleteVideojuego(int videojuegoId) throws DataAccessException {
		Optional<Videojuego> videojuego = this.videojuegoRepository.findVideojuegoById(videojuegoId);
		if(videojuego.isPresent()) {
		videojuegoRepository.deleteById(videojuegoId);
		}else {
			throw new IllegalArgumentException("El videojuego que desea borrar no existe");
			
		}
	}

	@Transactional
	public void delete(Videojuego v) throws DataAccessException {
		Optional<Videojuego> videojuego = this.videojuegoRepository.findVideojuegoById(v.getId());
		if(videojuego.isPresent()) {
			videojuegoRepository.delete(v);
		}else {
			throw new IllegalArgumentException("El videojuego que desea borrar no existe");
			
		}
	}

	public Collection<Plataforma> getPlataformas() {
		Collection<Plataforma> plataformas = new ArrayList<Plataforma>();
		for (Plataforma p : Plataforma.values()) {
			plataformas.add(p);
		}
		return plataformas;
	}
}
