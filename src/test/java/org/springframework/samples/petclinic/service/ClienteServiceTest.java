package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import javax.management.InvalidAttributeValueException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTest {
	
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente createDummyClienteCorrecto1() {
		
		Cliente cliente = new Cliente();
		User user = new User();
		user.setUsername("usuarioDummy");
		user.setPassword("complexPassword");
		
		cliente.setAdmin(false);
		cliente.setCartera(0.0);
		cliente.setDireccion("Direccion Prueba");
		cliente.setApellidos("Dummy Dummy");
		cliente.setNombre("John");
		cliente.setDni("12345678D");
		cliente.setEmail("prueba@email.es");
		cliente.setF_nacimiento(LocalDate.of(1998, 6, 12));
		cliente.setTarjeta_credito("1222 9999 4444 344");
		cliente.setUser(user);
		/*
		cliente.setBandeja(null);
		cliente.setPedidos(null);
		cliente.setPlataformas(null);
		cliente.setPlataformas(null);
		cliente.setVendedor(null);
		*/
		
		return cliente;
	}
	
public Cliente createDummyClienteCorrecto2() {
		
		Cliente cliente = new Cliente();
		User user = new User();
		user.setUsername("usuarioDummy2");
		user.setPassword("complexPassword2");
		
		cliente.setAdmin(false);
		cliente.setCartera(0.0);
		cliente.setDireccion("Direccion Prueba 2");
		cliente.setApellidos("Dummy Dummy 2");
		cliente.setNombre("John 2");
		cliente.setDni("12343678D");
		cliente.setEmail("prueba2@email.es");
		cliente.setF_nacimiento(LocalDate.of(1998, 6, 12));
		cliente.setTarjeta_credito("1222 9999 4444 346");
		cliente.setUser(user);
		
		return cliente;
	}
	
	public Cliente createDummyClienteIncorrecto1() {
		Cliente cliente = new Cliente();
		User user = new User();
		user.setUsername("usuarioDummy");
		user.setPassword("complexPassword");
		
		cliente.setAdmin(false);
		cliente.setCartera(0.0);
		cliente.setApellidos("Dummy Dummy");
		cliente.setNombre("John");
		cliente.setDni("1238D");
		cliente.setEmail("pruebaemail.es");
		cliente.setF_nacimiento(LocalDate.of(1998, 6, 12));
		cliente.setTarjeta_credito("1222 9999 4444 344");
		cliente.setUser(user);
		
		return cliente;
	}

	@Test
	public void testCountWithInitialData() {
		int count = this.clienteService.clienteCount();
		Assertions.assertEquals(count, 2);
	}
	
	@Test
	public void testCreateClienteSuccess() throws InvalidAttributeValueException {

		Cliente cliente = createDummyClienteCorrecto1();
		
		int countCliente = clienteService.clienteCount();

		this.clienteService.saveCliente(cliente);

		Assertions.assertEquals(clienteService.findClienteById(countCliente + 1).getApellidos(), cliente.getApellidos());

	}
	
	@Test
	public void testDeleteCliente() {
		/*
		int countCliente = clienteService.clienteCount();
		
		Cliente cliente = this.createDummyClienteCorrecto2();
		
		clienteService.saveCliente(cliente);
		
		Assertions.assertEquals(this.clienteRepository.existsById(countCliente+1),true);
		
		this.clienteService.deleteCliente(countCliente+1);
		
		Assertions.assertEquals(this.clienteRepository.existsById(countCliente+1),false);
		*/
	}
	
	@Test
	public void testEditClienteSuccess() throws InvalidAttributeValueException {

		Cliente cliente = clienteService.findClienteById(1);
		
		cliente.setNombre("Nombre Editado");

		this.clienteService.saveCliente(cliente);
		
		Cliente clienteEditado = clienteService.findClienteById(1);

		Assertions.assertEquals(clienteEditado.getNombre(), "Nombre Editado");

	}
	
	@Test
	public void TestFindClienteByUnexistingId(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			this.clienteService.findClienteById(129999);
		});
	}
	
	@Test
	public void testFindClienteById() {
		
		Cliente cliente = clienteService.findClienteById(1);
		
		Assertions.assertEquals(cliente.getId(), 1);
		Assertions.assertEquals(cliente.getNombre(), "Ivan");
		Assertions.assertEquals(cliente.getDni(), "12345678X");
		
		
	}
	
}