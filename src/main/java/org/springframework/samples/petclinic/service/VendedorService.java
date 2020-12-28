
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository	vendedorRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Transactional
	public int conteoPedido() {
		return (int) this.vendedorRepository.count();

	}

	@Transactional
	public Iterable<Vendedor> findAll() {
		return this.vendedorRepository.findAll();

	}

	//Te devuelve un Optional

	@Transactional(readOnly = true)
	public Optional<Vendedor> findVendedorById(final int id) {
		return this.vendedorRepository.findById(id);

	}

	//Te devuelve un Vendedor

	public Vendedor finVendedorByIdNormal(final int id) throws DataAccessException {

		return this.vendedorRepository.findById(id).get();

	}

	@Transactional
	public void delete(final Vendedor vendedor) {
		this.vendedorRepository.delete(vendedor);

	}

	@Transactional
	public void save(final Vendedor vendedor) {
		this.vendedorRepository.save(vendedor);
		this.userService.saveUser(vendedor.getUser());
		this.authoritiesService.saveAuthorities(vendedor.getUser().getUsername(), "vendedor");

	}

	@Transactional(readOnly = true)
	public void deleteVendedorById(final int id) throws DataAccessException {

		this.vendedorRepository.deleteById(id);
	}

}
