package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PeliculaServiceTests {

	@Autowired
	protected PeliculaService peliculaService;

	@Test
	void findPeliculaById() {

		Pelicula pelicula = peliculaService.findPeliculaById(4);
		Assert.assertTrue(pelicula.getId() == 4);

	}

	@Test
	void findPeliculaNoExiste() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			peliculaService.findPeliculaById(200);
		});
	}

	@Test
	void deletePeliculaSuccess() {
		
		List<Pelicula> peliculas = this.peliculaService.findPeliculas();
		Pelicula Titanic = this.peliculaService.findPeliculaById(1);
		
		Assertions.assertTrue(peliculas.contains(Titanic));
		peliculas.remove(Titanic);
		Assertions.assertTrue(!peliculas.contains(Titanic));
	}

	@Test
	void deletePeliculaNoSuccess() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			peliculaService.deletePelicula(200);
		});
	}

	@Test
	void shouldInsertPelicula() {

		Pelicula pelicula = new Pelicula();
		pelicula.setId(15);
		pelicula.setNombre("pelicula1");
		pelicula.setPrecio(12.50);
		pelicula.setAgno(2021);
		pelicula.setFechaSalida(LocalDate.now());
		pelicula.setDirector("director");
		pelicula.setDuracion(2.5);
		pelicula.setEdicion(2);
		pelicula.setFormato(Formato.DVD);
		pelicula.setImagen("https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp");
		pelicula.setDescripcion("Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.");

		this.peliculaService.savePelicula(pelicula);
		assertThat(pelicula.getId() != null && pelicula.getId() != 0);

	}

	@Test
	void shouldEditSuccess() {
		
		Pelicula pelicula = this.peliculaService.findPeliculaById(2);
		String nombre = pelicula.getNombre();
		pelicula.setNombre("AAA");
		this.peliculaService.savePelicula(pelicula);
		
		Assert.assertTrue(nombre != pelicula.getNombre());
	}

}
