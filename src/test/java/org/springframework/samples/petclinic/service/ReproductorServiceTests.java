package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReproductorServiceTests {

	private ReproductorService repService;

	@Autowired
	public ReproductorServiceTests(ReproductorService repService) {
		super();
		this.repService = repService;
	}

	private Reproductor crearReproductorCorrecto() {

		Reproductor r = new Reproductor();
		r.setNombre("Prueba");
		r.setDescripcion("Fallando fallando acabé aprobando");

		return r;
	}

	@Test // Un nuevo reproductor se crea correctamente
	void testPositivoCrearReproductor() {

		Collection<Reproductor> reproductores = this.repService.findAllReproductor();
		int found = reproductores.size();

		Reproductor reproductor = crearReproductorCorrecto();

		this.repService.saveReproductor(reproductor);
		assertThat(reproductor.getId().longValue()).isNotEqualTo(0);

		reproductores = this.repService.findAllReproductor();
		int newfound = reproductores.size();
		assertThat(newfound).isEqualTo(found + 1);
		assertThat(reproductores.contains(reproductor)).isEqualTo(true);

	}

	@Test // Un nuevo reproductor no se crea al ser el nombre demasiado corto
	void testNegativoCrearReproductorNombreCorto() {

		Reproductor reproductor = crearReproductorCorrecto();
		reproductor.setNombre("X");

		assertThrows(javax.validation.ConstraintViolationException.class, () -> {
			this.repService.saveReproductor(reproductor);
		});

	}

	@Test // Un nuevo reproductor no se crea al tener el mismo nombre que otro reproductor
	void testNegativoCrearReproductorDuplicado() {

		Reproductor reproductor = crearReproductorCorrecto();
		this.repService.saveReproductor(reproductor);

		Reproductor reproductor2 = crearReproductorCorrecto();

		assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
			this.repService.saveReproductor(reproductor2);
		});

	}

	@Test // Un nuevo reproductor se elimina correctamente

	void testPositivoEliminarReproductor() {

		Collection<Reproductor> reproductores = this.repService.findAllReproductor();
		int found = reproductores.size();

		this.repService.delete(1);

		int newfound = this.repService.findAllReproductor().size();

		assertThat(newfound).isEqualTo(found - 1);

	}

	@Test // Un nuevo reproductor se elimina correctamente
	void testPositivoModificarReproductor() {

		Collection<Reproductor> reproductores = this.repService.findAllReproductor();
		int found = reproductores.size();

		Reproductor reproductor = crearReproductorCorrecto();

		this.repService.saveReproductor(reproductor);
		reproductor.setNombre("Actualizando Nombre");
		this.repService.saveReproductor(reproductor);

		int newfound = this.repService.findAllReproductor().size();

		assertThat(newfound).isEqualTo(found + 1);
		assertThat(this.repService.findReproductorById(reproductor.getId()).get().getNombre())
				.isEqualTo("Actualizando Nombre");

	}

}
