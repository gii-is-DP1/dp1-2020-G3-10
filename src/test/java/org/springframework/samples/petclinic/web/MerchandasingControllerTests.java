package org.springframework.samples.petclinic.web;

import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.TipoMerchandasing;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = MerchandasingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MerchandasingControllerTests {

	@Autowired
	private MerchandasingController merchandasingController;

	@MockBean
	private MerchandasingService merchandasingService;

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
		merchandasinges.add(vegeta);

		// SHOW
		Merchandasing merchandasing = new Merchandasing();
		merchandasing.setId(301);
		merchandasing.setNombre("Figura de Vegeta 2");
		merchandasing.setFabricante("Bandai");
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
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("/merchandasings/MerchandasingsList"));
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
						.param("precio", "33.33"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
				//.andExpect(MockMvcResultMatchers.view().name("redirect:/merchandasings/" + ));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors1() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/vendedor/merchandasings/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("tipo", "Cause 1")
						.param("fabricante", "")
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
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("merchandasings/createOrUpdateMerchandasingForm"));
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
				.andExpect(MockMvcResultMatchers.view().name("welcome"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors1() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/vendedor/merchandasings/{merchandasingId}/edit",
								MerchandasingControllerTests.TEST_MERCHANDASING_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("tipo", "FIGURA"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("merchandasing"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "fabricante"))
				//.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "tipo"))
				.andExpect(MockMvcResultMatchers.view().name("merchandasings/createOrUpdateMerchandasingForm"));

	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateFormHasErrors2() throws Exception {
//		this.mockMvc
//				.perform(MockMvcRequestBuilders
//						.post("/vendedor/merchandasings/{merchandasingId}/edit",
//								MerchandasingControllerTests.TEST_MERCHANDASING_ID)
//						.with(SecurityMockMvcRequestPostProcessors.csrf())
//						.param("tipo","FIGURA")
//						.param("fabricante",""))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("merchandasing"))
//				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "fabricante"))
//				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("merchandasing", "tipo"))
//				.andExpect(MockMvcResultMatchers.view().name("merchandasings/createOrUpdateMerchandasingForm"));
//
//	}

}
