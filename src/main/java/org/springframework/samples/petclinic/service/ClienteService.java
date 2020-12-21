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
	private UserRepository userRepository;
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Transactional
	public int clienteCount() {
		return (int) this.clienteRepository.count();
	}
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException {
		
		this.clienteRepository.save(cliente);
		this.userService.saveUser(cliente.getUser());
		this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	
	
	@Transactional(readOnly = true)
	public Cliente findClienteById(int id) throws DataAccessException {
		if(clienteRepository.existsById(id)) {

			return clienteRepository.findById(id).get();
			
		}else {
			
			throw new IllegalArgumentException("El cliente que quiere mostrar no existe.");
			
		}
	}
	
	@Transactional
	public void deleteCliente(int id) throws DataAccessException {
		
		//if(clienteRepository.existsById(id)) {

			Cliente cliente = clienteRepository.findById(id).get();
			System.out.println(cliente);
			clienteRepository.delete(cliente);
			userRepository.delete(cliente.getUser());
			
			
	//	}else {
			
		//	throw new IllegalArgumentException("El cliente que quiere borrar no existe.");
			
		//}
		
	}
}
