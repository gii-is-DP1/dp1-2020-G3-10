package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.TipoMerchandasing;
import org.springframework.samples.petclinic.repository.MerchandasingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchandasingService {

	@Autowired
	private MerchandasingRepository merchandasingRepository;

//	@Transactional
//	public void saveMerchandasing(final Merchandasing merchandasing) throws DataAccessException {
//		this.merchandasingRepository.save(merchandasing);
//	}
//
//	@Transactional
//	public void delete(Merchandasing merchandasing) {
//		merchandasingRepository.deleteById(merchandasing.getId());
//	}
//
//	@Transactional
//	public Iterable<Merchandasing> findAll() {
//		return this.merchandasingRepository.findAll();
//	}

	@Transactional
	public void saveMerchandasing(@Valid Merchandasing merchandasing) throws DataAccessException {
		merchandasingRepository.save(merchandasing);

	}

	@Transactional(readOnly = true)
	public List<Merchandasing> findMerchandasings() throws DataAccessException {
		return merchandasingRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Merchandasing findMerchandasingById(int id) throws IllegalArgumentException {
		Optional<Merchandasing> merch = this.merchandasingRepository.findById(id);
		
		if (merch.isPresent()) {
			return merch.get();
		} else {
			throw new IllegalArgumentException("El merchandising no existe");

		}

	}

	@Transactional
	public void deleteMerchandasing(int merchandasingId) throws DataAccessException {
		Optional<Merchandasing> merch = this.merchandasingRepository.findById(merchandasingId);
		
		if (merch.isPresent()) {
			merchandasingRepository.deleteById(merchandasingId);
		}else {
			throw new IllegalArgumentException("El merchandising que desea borrar no existe");
		}
		
		

	}

	public void delete(Merchandasing m) {
		Optional<Merchandasing> merch = this.merchandasingRepository.findById(m.getId());
		if (merch.isPresent()) {
			merchandasingRepository.delete(m);
		}else {
			throw new IllegalArgumentException("El merchandising que desea borrar no existe");
		}
	}

	public Collection<TipoMerchandasing> getTipos() {
		Collection<TipoMerchandasing> tipos = new ArrayList<TipoMerchandasing>();
		for (TipoMerchandasing t : TipoMerchandasing.values()) {
			tipos.add(t);
		}
		return tipos;
	}

}
