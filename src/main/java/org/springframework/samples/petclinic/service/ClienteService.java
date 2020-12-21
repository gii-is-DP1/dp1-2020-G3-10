package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
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
		
		clienteRepository.save(cliente);
		this.userService.saveUser(cliente.getUser());
		this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	@Transactional(readOnly = true)
	public Cliente findClienteById(int id) throws DataAccessException {
		
		return clienteRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public void deleteClienteById (int id) throws DataAccessException {
		
		clienteRepository.deleteById(id);
	}
	
	public void deleteCliente (Cliente cliente) throws DataAccessException {
		
		clienteRepository.delete(cliente);
	}
	
	@Transactional(readOnly = true)
	public Collection<Cliente> findAllCliente() throws DataAccessException {
		
		return (Collection<Cliente>) clienteRepository.findAll();
	}



}
