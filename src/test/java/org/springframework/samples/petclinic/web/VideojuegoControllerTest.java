package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

/**
 * Test class for {@link VideojuegoController}
 *
 * @author Marta Díaz
 */
@WebMvcTest(controllers = VideojuegoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class VideojuegoControllerTest {

	private static final int TEST_VIDEOJUEGO_ID = 1;

	@Autowired
	private VideojuegoController videojuegoController;

	@MockBean
	private VideojuegoService videojuegoService;

	@MockBean
	private ProductoService productoService;

	@MockBean
	private PedidoService pedidoService;

	@MockBean
	private VendedorService vendedorService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private ComentarioService comentarioService;

	@Autowired
	private MockMvc mockMvc;

	private Videojuego videojuego;

	@BeforeEach
	void setup() {
		videojuego = new Videojuego();
		videojuego.setNombre("videojuego1");
		videojuego.setPrecio(12.6);
		videojuego.setAgno(2018);
		videojuego.setFechaSalida(LocalDate.now());
		videojuego.setDescripcion("descripcion del videojuego");
		videojuego.setEstudio("film SA");
		videojuego.setPlataforma(Plataforma.PS4);
		videojuego.setImagen("https://media.game.es/COVERV2/3D_L/182/182836.png");
		given(this.videojuegoService.findVideojuegoById(TEST_VIDEOJUEGO_ID)).willReturn(videojuego);
	}

	// GET /videojuegos/{videojuegoId}
	@WithMockUser(value = "spring")
	@Test
	void testShowVideojuego() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/videojuegos/{videojuegoId}", TEST_VIDEOJUEGO_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	// GET /videojuegos
	@WithMockUser(value = "spring")
	@Test
	void testShowVideojuegos() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/videojuegos"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/videojuegos/videojuegosList"));
	}

	// /videojuegos/delete/{videojuegoId}
	@WithMockUser(value = "spring")
	@Test
	void testDeleteVideojuego() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/videojuegos/delete/{videojuegoId}", TEST_VIDEOJUEGO_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}

	// GET /videojuegos/new
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormVideojuego() throws Exception {
		mockMvc.perform(get("/videojuegos/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("videojuego"))
				.andExpect(view().name("/videojuegos/formCreateVideojuegos"));
	}

	// POST /videojuegos/new
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormVideojuegoSuccess() throws Exception {

		this.mockMvc.perform(post("/videojuegos/new", videojuego).with(csrf()).param("nombre", "JUST DANCE 2021")
				.param("precio", "49.95").param("agno", "2012")
				.param("descripcion", "Vuelve el juego que te convierte en una estrella").param("estudio", "Film SA")
				.param("fechaSalida", "2021/09/09")
				.param("imagen", "https://media.game.es/COVERV2/3D_L/182/182836.png	").param("plataforma", "PS4"))
				.andExpect(status().is2xxSuccessful());

	}

	// GET /videojuegos/edit/{videojuegoId}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateFormVideojuego() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/videojuegos/edit/{videojuegoId}", TEST_VIDEOJUEGO_ID))
				.andExpect(status().is2xxSuccessful());
	}

	// POST /videojuegos/edit/{videojuegoId}
	@WithMockUser(value = "spring")
	@Test
	void testProcessEditFormVideojuegoSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/videojuegos/edit/{videojuegoId}", TEST_VIDEOJUEGO_ID)
						.param("nombre", "JUST DANCE 2021").with(csrf()).param("precio", "49.95").param("agno", "2012")
						.param("descripcion", "Vuelve el juego que te convierte en una estrella")
						.param("estudio", "Film SA").param("fechaSalida", "2021/09/09")
						.param("imagen", "https://media.game.es/COVERV2/3D_L/182/182836.png	")
						.param("plataforma", "PS4"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/videojuegos/" + TEST_VIDEOJUEGO_ID));

	}

}
