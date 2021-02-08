package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.TipoMerchandasing;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MerchandasingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MerchandasingControllerTests {

	@Autowired
	private MerchandasingController merchandasingController;

	@MockBean
	private MerchandasingService merchandasingService;

	@MockBean
	private VendedorService vendedorService;
	
	@MockBean
	private PedidoService pedidoService;

	
	@Autowired
	private MockMvc mockMvc;

	private static final int TEST_MERCHANDASING_ID = 1;

	private static final int TEST_MERCHANDASING_ERROR_ID = 30;

	@BeforeEach
	void setup() {
		List<Merchandasing> merchandasinges = new ArrayList<Merchandasing>();
		Merchandasing vegeta = new Merchandasing();
		vegeta.setNombre("Figura de Vegeta");
		vegeta.setId(300);
		vegeta.setFabricante("Bandai");
		vegeta.setTipo(TipoMerchandasing.FIGURA);
		vegeta.setPrecio(33.33);
		vegeta.setDescripcion("Descripción de Vegeta para un test");
		vegeta.setImagen("https://www.toysrus.es/medias/?context=bWFzdGVyfHByb2R1Y3RfaW1hZ2VzfDIw"
				+ "MDM0fGltYWdlL2pwZWd8aGY4L2gwZi85MDc4MjY0NzI1NTM0fGQzYTAzOTM1NmU0NGU2ZjJiYmM4OWU1"
				+ "NjNjMDc1MzI4MjBkODRmMWFmZmY3NjMyNjFlOGQ0NWQwNTc2ZDllOGQ");
		merchandasinges.add(vegeta);

		// SHOW
		Merchandasing merchandasing = new Merchandasing();
		merchandasing.setId(301);
		merchandasing.setNombre("Figura de Vegeta 2");
		merchandasing.setFabricante("Bandai");
		merchandasing.setDescripcion("Descripción de Vegeta para un test show");
		merchandasing.setImagen("https://www.toysrus.es/medias/?context=bWFzdGVyfHByb2R1Y3RfaW1hZ2VzfDIw"
				+ "MDM0fGltYWdlL2pwZWd8aGY4L2gwZi85MDc4MjY0NzI1NTM0fGQzYTAzOTM1NmU0NGU2ZjJiYmM4OWU1"
				+ "NjNjMDc1MzI4MjBkODRmMWFmZmY3NjMyNjFlOGQ0NWQwNTc2ZDllOGQ");
		merchandasing.setTipo(TipoMerchandasing.FIGURA);
		merchandasing.setPrecio(33.33);
		merchandasinges.add(merchandasing);

		// LIST ERROR
		List<Merchandasing> merchandasingesError = new ArrayList<Merchandasing>();

		// SHOW ERROR
		Merchandasing merchandasingError = new Merchandasing();
		merchandasingError.setId(30);
		merchandasingError.setNombre("Pájaro Loco");
		merchandasingError.setFabricante("");
		merchandasingError.setDescripcion("Descripción de Vegeta para un test show");
		merchandasingError.setImagen("https://www.toysrus.es/medias/?context=bWFzdGVyfHByb2R1Y3RfaW1hZ2VzfDIw"
				+ "MDM0fGltYWdlL2pwZWd8aGY4L2gwZi85MDc4MjY0NzI1NTM0fGQzYTAzOTM1NmU0NGU2ZjJiYmM4OWU1"
				+ "NjNjMDc1MzI4MjBkODRmMWFmZmY3NjMyNjFlOGQ0NWQwNTc2ZDllOGQ");
		merchandasingError.setTipo(TipoMerchandasing.FIGURA);
		merchandasingError.setPrecio(11.11);

		merchandasingesError.add(merchandasingError);

		BDDMockito.given(this.merchandasingService.findMerchandasings()).willReturn(merchandasinges);
		BDDMockito
				.given(this.merchandasingService
						.findMerchandasingById(MerchandasingControllerTests.TEST_MERCHANDASING_ID))
				.willReturn(merchandasing);
		BDDMockito
				.given(this.merchandasingService
						.findMerchandasingById(MerchandasingControllerTests.TEST_MERCHANDASING_ERROR_ID))
				.willReturn(merchandasingError);

	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessBorrarMerchandasing() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/vendedor/merchandasings/{merchandasingId}/delete",
						MerchandasingControllerTests.TEST_MERCHANDASING_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vendedor/merchandasings/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/merchandasings/createOrUpdateMerchandasingForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/vendedor/merchandasings/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("tipo", "FIGURA")
						.param("fabricante", "Toei")
						.param("nombre", "Krilin")
						.param("descripcion", "Figura de Krilín para test de creación correcta")
						.param("imagen", "https://lafrikileria.com/34614-large_default/figura-montable-dragon-ball-krilin-bandai-figure-rise.jpg")
						.param("precio", "33.33"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
				

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors1() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/vendedor/merchandasings/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("tipo", "Cause 1")
						.param("fabricante", "")
						.param("descripcion", "Figura de Krilín para test de creación correcta")
						.param("imagen", "https://lafrikileria.com/34614-large_default/figura-montable-dragon-ball-krilin-bandai-figure-rise.jpg")
						.param("nombre", "Krilin")
						.param("precio", "33.33"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("merchandasing"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "tipo"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "fabricante"))
				.andExpect(MockMvcResultMatchers.view().name("/merchandasings/createOrUpdateMerchandasingForm"));

	}



	// UPDATE TESTS

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/vendedor/merchandasings/{merchandasingId}/edit",
						MerchandasingControllerTests.TEST_MERCHANDASING_ID))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/vendedor/merchandasings/{merchandasingId}/edit",
								MerchandasingControllerTests.TEST_MERCHANDASING_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("tipo", "FIGURA")
						.param("fabricante", "fabricante 1"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("merchandasings/createOrUpdateMerchandasingForm"));
	}


}
