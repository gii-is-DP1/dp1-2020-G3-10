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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTest {
	
	private ClienteService clienteService;
	
	public Cliente createDummyClienteCorrecto1() {
		Cliente cliente = new Cliente();
		User user = new User();
		user.setUsername("usuarioDummy");
		user.setPassword("complexPassword");
		
		cliente.setAdmin(false);
		cliente.setCartera(0.0);
		cliente.setApellidos("Dummy Dummy");
		cliente.setNombre("John");
		cliente.setDni("12345678D");
		cliente.setEmail("prueba@email.es");
		cliente.setF_nacimiento(LocalDate.of(1998, 6, 12));
		cliente.setTarjeta_credito("1222 9999 4444 344");
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
		cliente.setBandeja(null);
	//	cliente.setDeseado(null);
		cliente.setApellidos("Dummy Dummy");
		cliente.setNombre("John");
		cliente.setDni("1238D");
		cliente.setEmail("pruebaemail.es");
		cliente.setF_nacimiento(LocalDate.of(1998, 6, 12));
		cliente.setPedidos(null);
		cliente.setTarjeta_credito("1222 9999 4444 344");
		cliente.setVendedor(null);
		cliente.setUser(user);
		
		return cliente;
	}

	@Test
	public void testCountWithInitialData() {
		int count = this.clienteService.clienteCount();
		Assertions.assertEquals(count, 1);
	}
	
	@Test
	public void testDeleteCliente() {
		
		int countCliente = clienteService.clienteCount();
		
		this.clienteService.deleteCliente(1);
		
		Assertions.assertEquals(this.clienteService.clienteCount(),countCliente - 1);

	}
	
	@Test
	public void testCreateClienteSuccess() throws InvalidAttributeValueException {

		Cliente cliente = createDummyClienteCorrecto1();
		
		int countCliente = clienteService.clienteCount();

		this.clienteService.saveCliente(cliente);

		Assertions.assertEquals(countCliente, countCliente + 1);

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