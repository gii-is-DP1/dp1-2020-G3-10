package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.repository.ReproductorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReproductorService {

	@Autowired
	private ReproductorRepository reproductorRepo;

	@Transactional
	public void saveReproductor(Reproductor Reproductor) throws DataAccessException {

		System.out.println();
		reproductorRepo.save(Reproductor);
	}

	@Transactional(readOnly = true)
	public Optional<Reproductor> findReproductorById(int id) throws DataAccessException {
		return Optional.of(reproductorRepo.findById((id)).get());
	}

	
	public Iterable<Reproductor> findAllReproductor() throws DataAccessException {
		return reproductorRepo.findAll();
	}

	@Transactional()
	public void delete(int ReproductorId) {
		reproductorRepo.deleteById(ReproductorId);

	}

}
