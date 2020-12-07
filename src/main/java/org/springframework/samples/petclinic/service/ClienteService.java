package org.springframework.samples.petclinic.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException {
		
		System.out.println();
		clienteRepository.save(cliente);
		this.userService.saveUser(cliente.getUser());
		this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	@Transactional(readOnly = true)
	public Cliente findClienteById(int id) throws DataAccessException {
		
		return clienteRepository.findById(id).get();
	}


}
