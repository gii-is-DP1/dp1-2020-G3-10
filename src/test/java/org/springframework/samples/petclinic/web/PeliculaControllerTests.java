package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Test class for {@link PeliculaController}
 *
 * @author Marta Díaz
 */

@WebMvcTest(controllers = PeliculaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PeliculaControllerTests {

	private static final int TEST_PELICULA_ID = 1;

	@Autowired
	private PeliculaController peliculaController;

	@MockBean
	private PeliculaService peliculaService;

	@MockBean
	private ProductoService productoService;

	@Autowired
	private MockMvc mockMvc;

	private Pelicula pelicula;

	@BeforeEach
	void setup() {

		pelicula = new Pelicula();
		pelicula.setId(TEST_PELICULA_ID);
		pelicula.setNombre("pelicula1");
		pelicula.setPrecio(12.50);
		pelicula.setAgno(2023);
		pelicula.setDirector("director");
		pelicula.setDuracion(2.5);
		pelicula.setEdicion(2);
		pelicula.setFormato(Formato.DVD);
		pelicula.setImagen("https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp");
		pelicula.setDescripcion(
				"Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.");
		pelicula.setFechaSalida(LocalDate.now());
		given(this.peliculaService.findPeliculaById(TEST_PELICULA_ID)).willReturn(pelicula);

	}

	// //peliculas/{peliculaId}
	@WithMockUser(value = "spring")
	@Test
	void testShowPelicula() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/{peliculaId}", TEST_PELICULA_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/peliculas/peliculaDetails"));

	}

	// /peliculas
	@WithMockUser(value = "spring")
	@Test
	void testShowPeliculas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/peliculas")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/peliculas/PeliculasList"));
	}

	// /peliculas/delete/{peliculaId}
	@WithMockUser(value = "spring")
	@Test
	void testDeletePelicula() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/delete/{peliculaId}", TEST_PELICULA_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("/peliculas/PeliculasList"));
	}

	// GET /peliculas/new
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormPelicula() throws Exception {
		mockMvc.perform(get("/peliculas/new")).andExpect(status().isOk()).andExpect(model().attributeExists("pelicula"))
				.andExpect(view().name("/peliculas/formCreatePeliculas"));
	}

	// GET /peliculas/edit/{peliculaId}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateFormPelicula() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/edit/{peliculaId}", TEST_PELICULA_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/peliculas/formCreatePeliculas"));
	}

	// POST /peliculas/new
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormPeliculaSuccess() throws Exception {

		this.mockMvc.perform(post("/peliculas/new", pelicula).with(csrf()).param("nombre", "nombrePelicula")
				.param("precio", "12").param("agno", "2012").param("director", "director de la pelicula")
				.param("duracion", "2").param("edicion", "3").param("formato", "DVD")
				.param("imagen", "https://static.filmin.es/images/media/31442/1/poster_0_3_720x0.webp")
				.param("descripcion", "descripcion de la pelicula")
				.param("fechaSalida", "15/02/12")).andExpect(status().is3xxRedirection());

	}

	// POST /peliculas/edit/{peliculaId}
		@WithMockUser(value = "spring")
		@Test
		void testProcessEditFormPeliculaSuccess() throws Exception {
			this.mockMvc
					.perform(MockMvcRequestBuilders.post("/peliculas/edit/{peliculaId}", TEST_PELICULA_ID)
							.param("descripcion", "descripcion de la pelicula")
							.param("nombre", "nombrePelicula").with(csrf()).param("precio", "12").param("agno", "2012")
							.param("director", "director de la pelicula").param("duracion", "2").param("edicion", "3")
							.param("formato", "DVD")
							.param("imagen", "https://static.filmin.es/images/media/31442/1/poster_0_3_720x0.webp")
							.param("fechaSalida", "15/02/12"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/peliculas/" + TEST_PELICULA_ID));

		}
	

}
