package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.model.TipoMerchandasing;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ComentarioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ComentarioControllerTests {
	
	private static final int TEST_COMENTARIO_ID = 1;
	
	private static final int TEST_CLIENTE_ID = 1;
	
	private static final int TEST_PELICULA_ID = 1;
	
	private static final int TEST_VIDEOJUEGO_ID = 1;
	
	private static final int TEST_MERCHANDASING_ID = 1;
	
	@Autowired
	private ComentarioController comentarioController;
	
	@MockBean
	private ComentarioService comentarioService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private PeliculaService peliculaService;
	
	@MockBean 
	private VideojuegoService videojuegoService;
	
	@MockBean
	private MerchandasingService merchandasingService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Comentario comentario;
	
	private Cliente cliente;
	
	private Pelicula pelicula;
	
	private User user;
	
	private Authorities authorities;
	
	private Videojuego videojuego;
	
	private Merchandasing merchandasing;
	
	
	@BeforeEach
	void setup() {
		comentario = new Comentario();
		cliente = new Cliente();
		pelicula = new Pelicula();
		user = new User();
		authorities = new Authorities();
		videojuego = new Videojuego();
		merchandasing = new Merchandasing();
		
		user.setPassword("password");
		user.setUsername("username");
		user.setEnabled(true);
		
		authorities.setAuthority("cliente");
		authorities.setUser(user);
		authorities.setId(1);
		
		cliente.setId(TEST_CLIENTE_ID);
		cliente.setApellidos("Apellidos");
		cliente.setCartera(0.0);
		cliente.setCiudad("Sevilla");
		cliente.setCodigoPostal("41740");
		cliente.setDireccion("Reina Mercedes");
		cliente.setDni("32899320M");
		cliente.setEmail("email@gmail.com");
		cliente.setFechaNacimiento(LocalDate.of(1999, 04, 29));
		cliente.setNombre("Nombre");
		cliente.setTarjetaCredito("1234567899887");
		cliente.setTelefono("645215899");
		cliente.setUser(user);
		
		pelicula.setId(TEST_PELICULA_ID);
		pelicula.setAgno(2017);
		pelicula.setDescripcion("Descripcion pelicula");
		pelicula.setDirector("Director pelicula");
		pelicula.setDuracion(50.0);
		pelicula.setEdicion(4);
		pelicula.setFechaSalida(LocalDate.of(2015, 03, 20));
		pelicula.setFormato(Formato.DVD);
		pelicula.setImagen("imagen");
		pelicula.setNombre("Nombre pelicula");
		pelicula.setPrecio(5.0);
		
		videojuego.setId(TEST_VIDEOJUEGO_ID);
		videojuego.setNombre("videojuego1");
		videojuego.setPrecio(12.6);
		videojuego.setAgno(2018);
		videojuego.setFechaSalida(LocalDate.now());
		videojuego.setDescripcion("descripcion del videojuego");
		videojuego.setEstudio("film SA");
		videojuego.setPlataforma(Plataforma.PS4);
		videojuego.setImagen("https://media.game.es/COVERV2/3D_L/182/182836.png");
		
		merchandasing.setNombre("Figura de Vegeta");
		merchandasing.setId(300);
		merchandasing.setFabricante("Bandai");
		merchandasing.setTipo(TipoMerchandasing.FIGURA);
		merchandasing.setPrecio(33.33);
		
		comentario.setId(TEST_COMENTARIO_ID);
		comentario.setTexto("Texto comentario");
		comentario.setTitulo("Titulo comentario");
		comentario.setCliente(cliente);
		comentario.setPelicula(pelicula);
		
		//pelicula.getComentarios().add(comentario);
		//cliente.getComentarios().add(comentario);
		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(cliente);
		given(this.peliculaService.findPeliculaById(TEST_PELICULA_ID)).willReturn(pelicula);
		given(this.comentarioService.findCommentById(TEST_COMENTARIO_ID)).willReturn(comentario);
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteComentario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comentarios/{clienteId}/comentario/{comentarioId}/delete", TEST_CLIENTE_ID, TEST_COMENTARIO_ID))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/comentarios/1"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormComentarioPelicula() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comentarios/{clienteId}/pelicula/{peliculaId}/new", TEST_CLIENTE_ID, TEST_PELICULA_ID))
				.andExpect(MockMvcResultMatchers.status().isOk());
				}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormComentarioVideojuego() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comentarios/{clienteId}/videojuego/{videojuegoId}/new", TEST_CLIENTE_ID, TEST_VIDEOJUEGO_ID))
				.andExpect(MockMvcResultMatchers.status().isOk());
				}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormComentariomMerchandasing() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comentarios/{clienteId}/merchandasing/{merchandasingId}/new", TEST_CLIENTE_ID, TEST_MERCHANDASING_ID))
				.andExpect(MockMvcResultMatchers.status().isOk());
				}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormComentarioPelicula() throws Exception {
		this.mockMvc.perform(post("/comentarios/{clienteId}/pelicula/{peliculaId}/new", TEST_CLIENTE_ID, TEST_PELICULA_ID, comentario).with(csrf())
				.param("texto", "Texto comentario pelicula")
				.param("titulo", "Titulo comentario pelicula")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormComentarioVideojuego() throws Exception {
		this.mockMvc.perform(post("/comentarios/{clienteId}/videojuego/{videojuegoId}/new", TEST_CLIENTE_ID, TEST_VIDEOJUEGO_ID, comentario).with(csrf())
				.param("texto", "Texto comentario pelicula")
				.param("titulo", "Titulo comentario pelicula")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormComentarioMerchandasing() throws Exception {
		this.mockMvc.perform(post("/comentarios/{clienteId}/merchandasing/{merchandasingId}/new", TEST_CLIENTE_ID, TEST_MERCHANDASING_ID, comentario).with(csrf())
				.param("texto", "Texto comentario pelicula")
				.param("titulo", "Titulo comentario pelicula")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowComentarios() throws Exception {
		mockMvc.perform(get("/comentarios/{clienteId}", TEST_CLIENTE_ID)).andExpect(status().isOk());
	}

}