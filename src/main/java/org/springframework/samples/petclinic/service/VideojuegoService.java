package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
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
	public void saveVideojuego(Videojuego v) throws DataAccessException {
		videojuegoRepository.save(v);
	}

	@Transactional(readOnly = true)
	public List<Videojuego> findVideojuegos() throws DataAccessException {
		return videojuegoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Videojuego findVideojuegoById(int id) throws DataAccessException {
		return videojuegoRepository.findVideojuegoById(id);
	}

	@Transactional
	public void deleteVideojuego(int videojuegoId) throws DataAccessException {
		videojuegoRepository.deleteById(videojuegoId);
	}

	@Transactional
	public void delete(Videojuego v) throws DataAccessException {
		videojuegoRepository.delete(v);
	}
}
