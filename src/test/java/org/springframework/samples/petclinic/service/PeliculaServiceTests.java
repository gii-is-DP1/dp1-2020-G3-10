package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

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
		List<Pelicula> peliculas =  this.peliculaService.findPeliculas();
		Integer tamaño = peliculas.size();
		Pelicula p = this.peliculaService.findPeliculaById(3);
		peliculas.remove(p);
		assertThat(peliculas.size()==tamaño-1);
	}
	
	@Test
	@Transactional
	public void shouldInsertPelicula() {
		Pelicula pelicula = new Pelicula();
		pelicula.setNombre("pelicula1");
		pelicula.setPrecio(12.50);
		pelicula.setAgno(2023);
		pelicula.setFechaSalida(LocalDate.now());
		pelicula.setDirector("director");
		pelicula.setDuracion(2.5);
		pelicula.setEdicion(2);
		pelicula.setFormato(Formato.DVD);
		pelicula.setImagen("https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp");
		pelicula.setDescripcion("Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.");
		
		
		this.peliculaService.savePelicula(pelicula);
		assertThat(pelicula.getId()!=null && pelicula.getId()!=0);
		
	}
	
	@Test
	@Transactional
	public void shouldEditPelicula() {
		Pelicula p = this.peliculaService.findPeliculaById(1);
		String nombreAntiguo= p.getNombre();
		String nuevoNombre= nombreAntiguo + "AAA";
		
		p.setNombre(nuevoNombre);
		this.peliculaService.savePelicula(p);
		assertThat(p.getNombre() == nuevoNombre);
	}
	
	@Test
	@Transactional
	public void shouldDeletePelicula() {
		int id = 1;
		Pelicula p = this.peliculaService.findPeliculaById(id);
		Integer size=  this.peliculaService.findPeliculas().size();
		this.peliculaService.delete(p);
		Integer sizeNew = this.peliculaService.findPeliculas().size();
		assertThat((size != sizeNew) && this.peliculaService.findPeliculaById(id)==null);
	}
	
	
}
