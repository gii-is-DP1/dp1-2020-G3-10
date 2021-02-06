
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pelicula;
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


	@Test
	public void obtenerPeliculas() {

		Vendedor vendedor = this.vendedorService.findVendedorByIdNormal(1);
		System.out.println("IDDDDDDDDDDDD" + vendedor.getId());

		Pelicula pel = this.peliculaService.findPeliculaById(1);
		System.out.println("PELICULAAAAAAAa" + pel.toString());
		Collection<Pelicula> peliculas = new ArrayList<Pelicula>();
		peliculas.add(pel);
		vendedor.setPeliculas(peliculas);

		System.out.println("TIENEEEEEEEEEEE" + vendedor.getPeliculas().toString());

		this.vendedorService.save(vendedor);

		System.out.println(pel);
		Collection<Pelicula> pels = this.vendedorService.obtenerPeliculas(vendedor.getId());
		System.out.println("PELICULAAAAAAAAAA DEL VENDEDOR" + pels.toString());

	}

	@Test
	void obtieneVendedorPorUsuario() {
		User user = this.userService.findUser("marta").get();
		System.out.println("PRUEBA USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + user);
		Vendedor vendedor = new Vendedor();
		vendedor.setUser(user);
		vendedor.setApellidos("prueba");
		vendedor.setMerchandasings(null);
		vendedor.setVacaciones(true);
		vendedor.setPeliculas(null);
		vendedor.setDireccionTienda("Direccion tienda");
		vendedor.setValoracion(20.0);
		vendedor.setFechaNacimiento(LocalDate.of(2020, 5, 12));
		vendedor.setDni("12345678X");
		vendedor.setNombre("nombre prueba");
		vendedor.setTelefono("123456789");
		vendedor.setEmail("email@email.es");

		this.vendedorRepository.save(vendedor);

		Vendedor vendedor2 = this.vendedorRepository.findById2(1);

		System.out.println("PRUEBA Cliente 2222222222222222222222222222222222222222: " + vendedor2.getApellidos());

		Vendedor vendedor1 = this.vendedorRepository.findByUsername("marta");

		System.out.println("PRUEBA: " + vendedor1.getApellidos());

	}

}
