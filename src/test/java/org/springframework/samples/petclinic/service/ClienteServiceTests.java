package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClienteServiceTests {

	@Autowired
	protected ClienteService clienteService;

	private Cliente Mortadelo = new Cliente("Mortadelo", "Tocino",LocalDate.of(1958, 04, 20), "22551136G", "mortadelo@hotmail.es",
			"601326970", "Barcelona", "15000", "Pensión El Calvario", "4321 4321 4321 4321", 1000.0);
	
	private Cliente Filemon = new Cliente("Filemon", "Pi ",LocalDate.of(1958, 04, 20), "29551932Z", "filemon_pi@hotmail.es",
			"601326971", "Barcelona", "15000", "Pensión El Calvario", "3214 3214 3214 3214", 120.0);
	
	/*
	
	@Test
	void deberiaCrearCliente(){
		
		int numeroClientes = clienteService.findAllCliente().size();
		
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
	
	*/
	

	
}