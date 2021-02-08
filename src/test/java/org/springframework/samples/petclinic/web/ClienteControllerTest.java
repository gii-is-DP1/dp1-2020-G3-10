package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClienteController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ClienteControllerTest {

	private static final int TEST_CLIENTE_ID = 200;
	
	@Autowired
	private ClienteController clienteController;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private ReproductorService reproductorService;
	

	@MockBean
	private PedidoService pedidoService;


	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Cliente javier;
	
	private Authorities authorities;
	
	private User usuario;

	@BeforeEach
	void setup() {

		javier = new Cliente();
		javier.setId(TEST_CLIENTE_ID);
		javier.setNombre("Javier");
		javier.setApellidos("Moreno González");
		javier.setCiudad("Sevilla");
		javier.setCodigoPostal("41012");
		javier.setDni("32097886Y");
		javier.setEmail("antmorgon4@alum.us.es");
		javier.setFechaNacimiento(LocalDate.of(1997, 05, 16));
		javier.setTarjetaCredito("4467574604245771");
		javier.setTelefono("601326967");
		javier.setDireccion("Calle Tarfia 45 10");
		
		usuario = new User();
		usuario.setUsername("pacopruebas");
		usuario.setPassword("password");
		usuario.setEnabled(true);
		
		authorities = new Authorities();
		authorities.setAuthority("cliente");
		authorities.setUser(this.usuario);
		
		Set<Authorities> setAuthorities = new HashSet<Authorities>();
		setAuthorities.add(authorities);
		
		usuario.setAuthorities(setAuthorities);
		
		javier.setUser(usuario);
		
		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(javier);
		given(this.clienteService.findClienteByUserName(usuario.getUsername())).willReturn(javier);
	}

	@WithMockUser(value = "spring")
	@Test

	void testGetFormularioCreacion() throws Exception {
		mockMvc.perform(get("/clientes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cliente"))
				.andExpect(view().name("clientes/createClienteForm"));

	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Se crea un cliente con éxito")
	void testPostFormularioCreacion() throws Exception {
		mockMvc.perform(post("/clientes/{clienteId}/edit", TEST_CLIENTE_ID).with(csrf()).param("nombre", "Paco")
				.param("apellidos", "Pruebas").param("ciudad", "Jerez").param("codigoPostal", "11405")
				.param("dni", "32097886Y").param("email", "pacopruebas@hotmail.es")
				.param("direccion", "Avenida Juan Carlos I Bloque 6 3ºB")
				.param("user", "usuario")
				.param("fechaNacimiento", "1990-01-01").param("tarjetaCredito", "4467574604245771")
				.param("telefono", "956085319"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testPostFormularioCreacionConErrores() throws Exception {
		mockMvc.perform(post("/clientes/new").with(csrf()).param("nombre", "Paco").param("apellidos", "Pruebas")
				.param("dni", "32097886Y").param("email", "pacopruebas@hotmail.es")
				.param("fechaNacimiento", "1990-01-01").param("telefono", "601326967")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("cliente"))
				.andExpect(model().attributeHasFieldErrors("cliente", "codigoPostal"))
				.andExpect(model().attributeHasFieldErrors("cliente", "ciudad"))
				.andExpect(model().attributeHasFieldErrors("cliente", "direccion"))
				.andExpect(model().attributeHasFieldErrors("cliente", "tarjetaCredito"))
				.andExpect(view().name("clientes/createClienteForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetFormularioEdicion() throws Exception {
		mockMvc.perform(get("/clientes/{clienteId}/edit", TEST_CLIENTE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cliente"))
				.andExpect(model().attribute("cliente", hasProperty("nombre", is("Javier"))))
				.andExpect(model().attribute("cliente", hasProperty("apellidos", is("Moreno González"))))
				.andExpect(model().attribute("cliente", hasProperty("ciudad", is("Sevilla"))))
				.andExpect(view().name("clientes/createClienteForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPostFormularioEdicion() throws Exception {
		mockMvc.perform(post("/clientes/{clienteId}/edit", TEST_CLIENTE_ID).with(csrf()).param("nombre", "Paco")
				.param("apellidos", "Pruebas").param("ciudad", "Jerez").param("codigoPostal", "11405")
				.param("dni", "32097886Y").param("email", "pacopruebas@hotmail.es")
				.param("direccion", "Avenida Juan Carlos I Bloque 6 3ºB")
				.param("fechaNacimiento", "1990-01-01").param("tarjetaCredito", "4467574604245771")
				.param("telefono", "956085319")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testPostFormularioEdicionConErrores() throws Exception {
		mockMvc.perform(post("/clientes/{clienteId}/edit", TEST_CLIENTE_ID).with(csrf()).param("nombre", "Paco")
				.param("apellidos", "Pruebas").param("dni", "32097886Y").param("email", "pacopruebas@hotmail.es")
				.param("fechaNacimiento", "1990-01-01").param("telefono", "601326967")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("cliente"))
				.andExpect(model().attributeHasFieldErrors("cliente", "codigoPostal"))
				.andExpect(model().attributeHasFieldErrors("cliente", "ciudad"))
				.andExpect(model().attributeHasFieldErrors("cliente", "direccion"))
				.andExpect(model().attributeHasFieldErrors("cliente", "tarjetaCredito"))
				.andExpect(view().name("clientes/createClienteForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowCliente() throws Exception {
		mockMvc.perform(get("/clientes/{clienteId}", TEST_CLIENTE_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("cliente", hasProperty("nombre", is("Javier"))))
				.andExpect(model().attribute("cliente", hasProperty("apellidos", is("Moreno González"))))
				.andExpect(model().attribute("cliente", hasProperty("ciudad", is("Sevilla"))))
				.andExpect(model().attribute("cliente", hasProperty("codigoPostal", is("41012"))))
				.andExpect(model().attribute("cliente", hasProperty("dni", is("32097886Y"))))
				.andExpect(model().attribute("cliente", hasProperty("email", is("antmorgon4@alum.us.es"))))
				.andExpect(model().attribute("cliente", hasProperty("telefono", is("601326967"))))
				.andExpect(model().attribute("cliente", hasProperty("direccion", is("Calle Tarfia 45 10"))))
				.andExpect(view().name("clientes/clienteDetails"));
	}
	
	
}
