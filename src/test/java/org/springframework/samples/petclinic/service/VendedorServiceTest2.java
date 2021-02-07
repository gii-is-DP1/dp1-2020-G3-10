
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VendedorServiceTest2 {

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

	@Test // Un nuevo vendedor con DNI no válido no se puede crear
	void testNegativoCrearVendedorDNI() {

		Vendedor vendedor = this.creaVendedorCorrecto();
		vendedor.setDni("123456789Y");

		Assertions.assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.vendedorService.save(vendedor);
		});

	}

	@Test // Un nuevo vendedor menor de edad no se puede crear
	void testNegativoCrearVendedorMenor() {

		Vendedor vendedor = this.creaVendedorCorrecto();
		vendedor.setFechaNacimiento(LocalDate.parse("2020-01-01"));

		Assertions.assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.vendedorService.save(vendedor);
		});

	}

}
