
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VendedorServiceTests {

	@Autowired
	protected VendedorService		vendedorService;

	@Autowired
	protected PeliculaService		peliculaService;

	@Autowired
	protected VendedorRepository	vendedorRepository;

	@Autowired
	protected UserService			userService;


	private Vendedor creaVendedorCorrecto() {

		Vendedor vendedor = new Vendedor();
		vendedor.setApellidos("carles");
		vendedor.setDireccionTienda("carles street");
		vendedor.setDni("48973312x");
		vendedor.setEmail("guivarpa27@gmail.com");
		vendedor.setFechaNacimiento(LocalDate.parse("1997-06-27"));
		vendedor.setNombre("antonio");
		vendedor.setNombreTienda("carles imc");
		vendedor.setTelefono("956787878");
		vendedor.setVacaciones(true);
		vendedor.setValoracion(500.0);
		vendedor.setVotos(100);

		User user = new User();
		user.setUsername("Pepe");
		user.setPassword("password");
		user.setEnabled(true);
		vendedor.setUser(user);

		return vendedor;

	}

	@Test // Se encuentra el vendedor
	void testPositivoEncontrarVendedorPorId() {

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int tamaño = vendedores.size();

		Vendedor vendedor = this.creaVendedorCorrecto();

		this.vendedorService.save(vendedor);
		Assertions.assertThat(vendedor.getId().longValue()).isNotEqualTo(0);

		Vendedor vendedorModificado = this.vendedorService.findVendedorByIdNormal(vendedor.getId());
		vendedores = this.vendedorService.findAllVendedor();

		Assertions.assertThat(vendedores.size()).isEqualTo(tamaño + 1);
		Assertions.assertThat(vendedorModificado).isEqualTo(vendedor);

	}

	@Test // Se encuentra el vendedor
	void testPositivoEncontrarVendedorPorUser() {

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int tamaño = vendedores.size();

		Vendedor vendedor = this.creaVendedorCorrecto();

		this.vendedorService.save(vendedor);
		Assertions.assertThat(vendedor.getId().longValue()).isNotEqualTo(0);

		Vendedor vendedorModificado = this.vendedorService.findVendedorByUsername(vendedor.getUser().getUsername());
		vendedores = this.vendedorService.findAllVendedor();

		Assertions.assertThat(vendedores.size()).isEqualTo(tamaño + 1);
		Assertions.assertThat(vendedorModificado).isEqualTo(vendedor);

	}

	@Test // Se crea el vendedor
	void testPositivoCrearVendedor() {

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int tamaño = vendedores.size();

		Vendedor vendedor = this.creaVendedorCorrecto();

		this.vendedorService.save(vendedor);
		Assertions.assertThat(vendedor.getId().longValue()).isNotEqualTo(0);

		vendedores = this.vendedorService.findAllVendedor();
		Assertions.assertThat(vendedores.size()).isEqualTo(tamaño + 1);
	}

	@Test // Un vendedor se modifica correctamente
	void testPositivoModificarVendedor() {

		Vendedor vendedor = this.creaVendedorCorrecto();
		this.vendedorService.save(vendedor);
		Assertions.assertThat(vendedor.getId().longValue()).isNotEqualTo(0);

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int found = vendedores.size();

		vendedor.setApellidos("Prueba apellidos");
		this.vendedorService.save(vendedor);

		Vendedor vendedorModificado = this.vendedorService.findVendedorByUsername(vendedor.getUser().getUsername());
		vendedores = this.vendedorService.findAllVendedor();

		Assertions.assertThat(vendedores.size()).isEqualTo(found);
		Assertions.assertThat(vendedor.getId()).isEqualTo(vendedorModificado.getId());
		Assertions.assertThat(vendedorModificado.getApellidos()).isEqualTo("Prueba apellidos");

	}

	@Test // Un nuevo vendedor se elimina correctamente
	void testPositivoEliminarVendedor() {

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int found = vendedores.size();

		Vendedor vendedorpruebas = this.vendedorService.findVendedorByIdNormal(120);
		Assertions.assertThat(vendedores.contains(vendedorpruebas)).isEqualTo(true);

		this.vendedorService.delete(vendedorpruebas);
		vendedores = this.vendedorService.findAllVendedor();
		Assertions.assertThat(vendedores.size()).isEqualTo(found - 1);
		Assertions.assertThat(vendedores.contains(vendedorpruebas)).isEqualTo(false);

	}

	@Test // Un nuevo vendedor se elimina correctamente por id
	void testPositivoEliminarVendedorPorId() {

		Collection<Vendedor> vendedores = this.vendedorService.findAllVendedor();
		int found = vendedores.size();

		Vendedor vendedorpruebas = this.vendedorService.findVendedorByIdNormal(120);
		Assertions.assertThat(vendedores.contains(vendedorpruebas)).isEqualTo(true);

		this.vendedorService.deleteVendedorById(vendedorpruebas.getId());
		vendedores = this.vendedorService.findAllVendedor();
		Assertions.assertThat(vendedores.size()).isEqualTo(found - 1);
		Assertions.assertThat(vendedores.contains(vendedorpruebas)).isEqualTo(false);

	}

}
