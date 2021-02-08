package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ReproductorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ReproductorControllerTests {
	
	private static final int TEST_REP_ID = 200;
	
	@Autowired
	private ReproductorController reproductorController;

	@MockBean
	private ReproductorService reproductorService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private ClienteController clienteController;

	@Autowired
	private MockMvc mockMvc;
	
	private Reproductor reproductor;
	
	@BeforeEach
	void setup() {

		reproductor = new Reproductor();
		reproductor.setId(TEST_REP_ID);
		reproductor.setNombre("Consola Prueba");
		reproductor.setDescripcion("Fallando fallando acabé aprobando");
		
		given(this.reproductorService.findReproductorById(TEST_REP_ID)).willReturn(Optional.of(reproductor));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/reproductores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("reproductor"))
				.andExpect(view().name("reproductores/editReproductor"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/reproductores/new").param("nombre", "XBOXONE").param("descripcion", "La mejor consola de Microsoft").with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowReproductores() throws Exception {
		mockMvc.perform(get("/reproductores")).andExpect(status().isOk())
				.andExpect(view().name("reproductores/listadoReproductores"));
	}
	
	
	
}