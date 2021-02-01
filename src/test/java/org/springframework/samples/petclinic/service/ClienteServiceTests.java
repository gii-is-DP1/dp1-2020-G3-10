package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;

import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClienteServiceTests {

	@Autowired
	protected ClienteService clienteService;
	@Autowired
	protected ClienteRepository clienteRepository;
	@Autowired
	protected UserService userService;
/*
	private Cliente Mortadelo = new Cliente("Mortadelo", "Tocino",LocalDate.of(1958, 04, 20), "22551136G", "mortadelo@hotmail.es",
			"601326970", "Barcelona", "15000", "Pensión El Calvario", "4321 4321 4321 4321", 1000.0);
	
	private Cliente Filemon = new Cliente("Filemon", "Pi ",LocalDate.of(1958, 04, 20), "29551932Z", "filemon_pi@hotmail.es",
			"601326971", "Barcelona", "15000", "Pensión El Calvario", "3214 3214 3214 3214", 120.0);
*/	

/*	
	private User creaUsuarios(String username, String password, String autoridad) {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAuthorities(null);
		
		Authorities authoritie = new Authorities();
		authoritie.setUser(user);
		authoritie.setAuthority(autoridad);
		
		Set<Authorities> authorities = new HashSet<Authorities>();
		authorities.add(authoritie);
		
		user.setAuthorities(authorities);
		
		return user;
		
	}
	
	@Test
	void deberiaCrearCliente(){
		
		User usuario = creaUsuarios("Mortadelo", "LaTia", "cliente");
		
		int numeroClientes = clienteService.findAllCliente().size();
		
		Mortadelo.setUser(usuario);
		clienteService.saveCliente(Mortadelo);
		
		assertThat(Mortadelo.getId().longValue()).isNotEqualTo(0);

		assertThat(clienteService.findAllCliente().size()).isEqualTo(numeroClientes + 1);

	}

	@Test
	void deberiaMostrarDetalleCliente() {
		
		clienteService.saveCliente(Mortadelo);
		
		assertThat(clienteService.findClienteById(Mortadelo.getId()).getApellidos()).isEqualTo("Tocino");
	}
	
	@Test
	void deberiaActualizarCliente() {
		
		User usuario = creaUsuarios("Mortadelo", "LaTia", "cliente");
		Mortadelo.setUser(usuario);
		
		clienteService.saveCliente(Mortadelo);
		
		Mortadelo = clienteService.findClienteById(Mortadelo.getId());
		
		Mortadelo.setApellidos("Chorizo");
		
		clienteService.saveCliente(Mortadelo);
		assertThat(clienteService.findClienteById(Mortadelo.getId()).getApellidos()).isEqualTo("Chorizo");
		
	}
	
	@Test
	void deberiaEliminarCliente(){
		
		clienteService.saveCliente(Mortadelo);
		clienteService.saveCliente(Filemon);

		int numeroClientes = clienteService.findAllCliente().size();

		clienteService.deleteCliente(Mortadelo);

		assertThat(clienteService.findAllCliente().size()).isEqualTo(numeroClientes - 1);
 
	}
	
	
	
	@Test
	void deberiaEncontrarClientePorUserName(){
		
		String username = "antmorgon4";

		Cliente a =clienteService.findClienteByUserName(username);
		
		Cliente b = clienteService.findClienteById(3);

		assertThat(a).isEqualTo(b);

	}
	*/
	@Test
	void obtieneClientePorUsuario(){
		User user = userService.findUser("marta").get();
		System.out.println("PRUEBA USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: "+user);
		Cliente cliente = new Cliente();
		cliente.setUser(user);
		cliente.setApellidos("prueba");
		cliente.setPedidos(null);
		cliente.setCartera(10.0);
		cliente.setCiudad("Sevilla");
		cliente.setCodigoPostal("10400");
		cliente.setDireccion("prueba");
		cliente.setTarjetaCredito("100000000");
		cliente.setFechaNacimiento(LocalDate.of(2020, 5, 12));
		cliente.setDni("12345678X");
		cliente.setNombre("nombre prueba");
		cliente.setTelefono("123456789");
		cliente.setEmail("email@email.es");
		
		
		
		clienteRepository.save(cliente);
		
		Cliente cliente2 = clienteRepository.findById(1);
		
		System.out.println("PRUEBA Cliente 2222222222222222222222222222222222222222: "+cliente2.getApellidos());
		
		Cliente cliente1 = clienteRepository.findByUsername("marta");
		
		System.out.println("PRUEBA: "+cliente1.getApellidos());

	}

    

    

	
}