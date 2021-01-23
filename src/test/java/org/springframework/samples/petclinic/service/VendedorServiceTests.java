package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VendedorServiceTests {

	
	@Autowired
	protected VendedorService vendedorService;

	@Autowired
	protected PeliculaService peliculaService;
	
	
	@Test
	public void obtenerPeliculas(){
		
		Vendedor vendedor = this.vendedorService.findVendedorByIdNormal(1);
		System.out.println("IDDDDDDDDDDDD" + vendedor.getId());
		
		Pelicula pel = this.peliculaService.findPeliculaById(1);
		System.out.println("PELICULAAAAAAAa" + pel.toString());
		Collection<Pelicula> peliculas = new ArrayList<Pelicula>();
		peliculas.add(pel);
		vendedor.setPeliculas(peliculas);
		
		System.out.println(pel);
		Collection<Pelicula> pels = this.vendedorService.obtenerPeliculas(1);
		System.out.println("PELICULA DEL VENDEDOR" + pels.toString());
	
	}
	
}
