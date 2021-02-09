package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VideojuegoServiceTests {

	@Autowired
	protected VideojuegoService videojuegoService;

	@Test
	void findVideojuegoById() {
		Videojuego v = this.videojuegoService.findVideojuegoById(4);
		assertThat(v.getId()==4);
	}
	
	@Test
	void findVideojuegoNoExiste() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			videojuegoService.findVideojuegoById(2000);
		});
		}

	@Test
	@Transactional
	public void shouldInsertVideojuego() {
		
		Videojuego videojuego = new Videojuego();
		videojuego.setId(15);
		videojuego.setNombre("ANIMAL CROSSING: NEW HORIZONS");
		videojuego.setPrecio(12.0);
		videojuego.setAgno(2015);
		videojuego.setDescripcion("Descubre los placeres de vivir en una isla desierta y crea tu hogar perfecto");
		videojuego.setEstudio("estudio");
		videojuego.setImagen("https://media.game.es/COVERV2/3D_L/169/169067.png");
		videojuego.setPlataforma(Plataforma.NINTENDO_SWITCH);
		videojuego.setFechaSalida(LocalDate.now());
		this.videojuegoService.saveVideojuego(videojuego);
		assertThat(videojuego.getId() != null && videojuego.getId() != 0);

	}
	
	@Test
	@Transactional
	public void shouldEditVideojuego() {
		Videojuego v = this.videojuegoService.findVideojuegoById(1);
		String nombreAntiguo = v.getNombre();
		String nombreNuevo= nombreAntiguo + "BBB";
		
		v.setNombre(nombreNuevo);
		this.videojuegoService.saveVideojuego(v);
		assertThat(v.getNombre()== nombreNuevo);
		
	}
	
	@Test
	@Transactional
	public void shouldDeleteVideojuego() {
		
		Videojuego videojuego = this.videojuegoService.findVideojuegoById(2);
		Assertions.assertTrue(this.videojuegoService.findVideojuegos().contains(videojuego));
		this.videojuegoService.delete(videojuego);
		Assertions.assertTrue(!this.videojuegoService.findVideojuegos().contains(videojuego));
		
	}
	
	@Test
	void deleteVideojuegoNoSuccess() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			videojuegoService.deleteVideojuego(500);
		});
		}
	
	
	

}
