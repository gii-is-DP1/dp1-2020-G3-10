package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;

import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClienteServiceTests {

	protected ClienteService clienteService;

	@Autowired
	public ClienteServiceTests(ClienteService clienteService) {
		super();
		this.clienteService = clienteService;
	}

	// Existe un cliente de prueba cargado en la base de datos con usuario
	// clientepruebas e id 200

	private Cliente creaClienteCorrecto() {

		Cliente cliente = new Cliente();
		cliente.setApellidos("Gotera");
		cliente.setCartera(100.0);
		cliente.setCiudad("Madrid");
		cliente.setCodigoPostal("000001");
		cliente.setDireccion("Calle Prueba 123");
		cliente.setDni("32097886Y");
		cliente.setEmail("correodeprueba@hotmail.es");
		cliente.setFechaNacimiento(LocalDate.parse("1997-01-01"));
		cliente.setNombre("Pepe");
		cliente.setTarjetaCredito("4607443899654835");
		cliente.setTelefono("601326967");

		User user = new User();
		user.setUsername("Pepe");
		user.setPassword("password");
		user.setEnabled(true);
		cliente.setUser(user);

		return cliente;

	}

	private Cliente creaClienteCorrecto(String username, String password) {

		Cliente cliente = new Cliente();
		cliente.setApellidos("Gotera");
		cliente.setCartera(100.0);
		cliente.setCiudad("Madrid");
		cliente.setCodigoPostal("000001");
		cliente.setDireccion("Calle Prueba 123");
		cliente.setDni("32097886Y");
		cliente.setEmail("correodeprueba@hotmail.es");
		cliente.setFechaNacimiento(LocalDate.parse("1997-01-01"));
		cliente.setNombre("Pepe");
		cliente.setTarjetaCredito("4607443899654835");
		cliente.setTelefono("601326967");

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEnabled(true);
		cliente.setUser(user);

		return cliente;

	}

	@Test // Los clientes se encuentran correctamente
	void testPositivoEncontrarClientes() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente cliente = creaClienteCorrecto();
		Cliente cliente2 = creaClienteCorrecto("Chiquito", "Jaaaarl");

		this.clienteService.saveCliente(cliente);
		this.clienteService.saveCliente(cliente2);

		assertThat(cliente.getId().longValue()).isNotEqualTo(0);
		assertThat(cliente2.getId().longValue()).isNotEqualTo(0);

		List<Cliente> clientesModificado = (List<Cliente>) this.clienteService.findAllCliente();

		assertThat(clientesModificado.size()).isEqualTo(found + 2);
		assertThat(clientesModificado.contains(cliente)).isEqualTo(true);
		assertThat(clientesModificado.contains(cliente2)).isEqualTo(true);

	}

	@Test // El cliente se crea encuentra correctamente
	void testPositivoEncontrarClientePorId() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente cliente = creaClienteCorrecto();

		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		Cliente clienteModificado = this.clienteService.findClienteById(cliente.getId());
		clientes = this.clienteService.findAllCliente();

		assertThat(clientes.size()).isEqualTo(found + 1);
		assertThat(clienteModificado).isEqualTo(cliente);

	}

	@Test // El cliente se crea encuentra correctamente
	void testPositivoEncontrarClientePorUsername() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente cliente = creaClienteCorrecto();

		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		Cliente clienteModificado = this.clienteService.findClienteByUserName(cliente.getUser().getUsername());
		clientes = this.clienteService.findAllCliente();

		assertThat(clientes.size()).isEqualTo(found + 1);
		assertThat(clienteModificado).isEqualTo(cliente);

	}

	@Test // Un nuevo cliente se crea correctamente
	void testPositivoCrearCliente() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente cliente = creaClienteCorrecto();

		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		clientes = this.clienteService.findAllCliente();
		assertThat(clientes.size()).isEqualTo(found + 1);

	}

	@Test // Un nuevo cliente con DNI no válido no se puede crear
	void testNegativoCrearClienteDNI() {

		Cliente cliente = creaClienteCorrecto();
		cliente.setDni("123456789Y");

		assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.clienteService.saveCliente(cliente);
		});
		
	}

	@Test // Un nuevo cliente menor de edad no se puede crear
	void testNegativoCrearClienteMenor() {

		Cliente cliente = creaClienteCorrecto();
		cliente.setFechaNacimiento(LocalDate.parse("2020-01-01"));

		assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.clienteService.saveCliente(cliente);
		});

	}

	@Test // Un cliente se modifica correctamente
	void testPositivoModificarCliente() {

		Cliente cliente = creaClienteCorrecto();
		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		cliente.setApellidos("Prueba prueba");
		this.clienteService.saveCliente(cliente);

		Cliente clienteModificado = this.clienteService.findClienteByUserName(cliente.getUser().getUsername());
		clientes = this.clienteService.findAllCliente();

		assertThat(clientes.size()).isEqualTo(found);
		assertThat(cliente.getId()).isEqualTo(clienteModificado.getId());
		assertThat(clienteModificado.getApellidos()).isEqualTo("Prueba prueba");

	}

	@Test //Un cliente no puedo modificar sus datos porque son inválidos
	void testNegativoModificarCliente() {

		Cliente cliente = creaClienteCorrecto();
		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);
		cliente.setDni("123456789Y");

		assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.clienteService.saveCliente(cliente);
		});
		

	}

	@Test // Un nuevo cliente se elimina correctamente
	void testPositivoEliminarCliente() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente clientepruebas = this.clienteService.findClienteById(200);
		assertThat(clientes.contains(clientepruebas)).isEqualTo(true);

		this.clienteService.deleteCliente(clientepruebas);
		clientes = this.clienteService.findAllCliente();
		assertThat(clientes.size()).isEqualTo(found - 1);
		assertThat(clientes.contains(clientepruebas)).isEqualTo(false);

	}

	@Test // Un nuevo cliente se elimina correctamente usando su id
	void testPositivoEliminarCrearClientePorId() {

		Collection<Cliente> clientes = this.clienteService.findAllCliente();
		int found = clientes.size();

		Cliente clientepruebas = this.clienteService.findClienteById(200);
		assertThat(clientes.contains(clientepruebas)).isEqualTo(true);

		this.clienteService.deleteClienteById(clientepruebas.getId());
		clientes = this.clienteService.findAllCliente();
		assertThat(clientes.size()).isEqualTo(found - 1);
		assertThat(clientes.contains(clientepruebas)).isEqualTo(false);

	}

}