package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Merchandasing;

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
	public void saveMerchandasing(Merchandasing merchandasing) throws DataAccessException {
		merchandasingRepository.save(merchandasing);

	}

	@Transactional(readOnly = true)
	public List<Merchandasing> findMerchandasings() throws DataAccessException {
		return merchandasingRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Merchandasing findMerchandasingById(int id) throws DataAccessException {
		return merchandasingRepository.findById(id);
	}

	@Transactional
	public void deleteMerchandasing(int merchandasingId) throws DataAccessException {
		merchandasingRepository.deleteById(merchandasingId);

	}

}
