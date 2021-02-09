
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = VendedorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class VendedorControllerTests {

	private static final int	TEST_VENDEDOR_ID	= 121;

	@Autowired
	private VendedorController	vendedorController;

	@MockBean
	private VendedorService		vendedorService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Vendedor			carles;


	@BeforeEach
	void setup() {

		this.carles = new Vendedor();
		this.carles.setId(VendedorControllerTests.TEST_VENDEDOR_ID);
		this.carles.setNombre("REY");
		this.carles.setApellidos("MISTERIO");
		this.carles.setDireccionTienda("Sevilla");
		this.carles.setNombreTienda("41012");
		this.carles.setDni("32097886Y");
		this.carles.setEmail("antmorgon4@alum.us.es");
		this.carles.setFechaNacimiento(LocalDate.of(1997, 05, 16));
		this.carles.setTelefono("601326967");
		this.carles.setVacaciones(true);
		this.carles.setValoracion(500.0);
		this.carles.setVotos(100);
		BDDMockito.given(this.vendedorService.findVendedorByIdNormal(VendedorControllerTests.TEST_VENDEDOR_ID)).willReturn(this.carles);

	}

	@WithMockUser(value = "spring")
	@Test

	void testGetFormularioCreacion() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vendedores/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vendedor"))
			.andExpect(MockMvcResultMatchers.view().name("vendedores/nuevoVendedor"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testPostFormularioCreacion() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/vendedores/new").param("nombre", "Paco").param("apellidos", "Pruebas").with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombreTienda", "Jerez").param("vacaciones", "true").param("dni", "32097886Y")
				.param("direccionTienda", "carles stt").param("email", "pacopruebas@hotmail.es").param("fechaNacimiento", "1990-01-01").param("Valoracion", "500.0").param("telefono", "956085319").param("votos", "100"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vendedores/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Paco").param("apellidos", "Pruebas").param("dni", "32097886Y").param("email", "pacopruebas@hotmail.es")
			.param("fechaNacimiento", "1990-01-01").param("telefono", "")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("vendedor", "telefono")).andExpect(MockMvcResultMatchers.view().name("vendedores/nuevoVendedor"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vendedores/{vendedorId}/edit", VendedorControllerTests.TEST_VENDEDOR_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vendedor"))
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("apellidos", Matchers.is("MISTERIO")))).andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("nombre", Matchers.is("REY"))))
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("email", Matchers.is("antmorgon4@alum.us.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("telefono", Matchers.is("601326967"))))
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("nombreTienda", Matchers.is("41012")))).andExpect(MockMvcResultMatchers.view().name("vendedores/editVendedor"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vendedores/{vendedorId}/edit", VendedorControllerTests.TEST_VENDEDOR_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Joe").param("apellidos", "Bloggs")
			.param("direcionTienda", "123 Caramel Street").param("nombreTienda", "London").param("telefono", "01616291589")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vendedores/{vendedorId}/edit", VendedorControllerTests.TEST_VENDEDOR_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Joe").param("apellidos", "Bloggs")
			.param("direccionTienda", "London").param("email", "London")).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vendedores/{vendedorId}", VendedorControllerTests.TEST_VENDEDOR_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("nombre", Matchers.is("REY")))).andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("apellidos", Matchers.is("MISTERIO"))))
			.andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("telefono", Matchers.is("601326967")))).andExpect(MockMvcResultMatchers.model().attribute("vendedor", Matchers.hasProperty("dni", Matchers.is("32097886Y"))))
			.andExpect(MockMvcResultMatchers.view().name("vendedores/vendedorDetails"));
	}

}
