package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.repository.PlataformaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlataformaService {

	@Autowired
	private PlataformaRepository platrepo;

	@Transactional
	public void savePlataforma(Plataforma plataforma) throws DataAccessException {

		System.out.println();
		platrepo.save(plataforma);
	}

	@Transactional(readOnly = true)
	public Optional<Plataforma> findPlataformaById(int id) throws DataAccessException {
		return Optional.of(platrepo.findById((id)).get());
	}

	
	public Iterable<Plataforma> findAllPlataforma() throws DataAccessException {
		return platrepo.findAll();
	}

	@Transactional()
	public void delete(int plataformaId) {
		platrepo.deleteById(plataformaId);

	}

}
