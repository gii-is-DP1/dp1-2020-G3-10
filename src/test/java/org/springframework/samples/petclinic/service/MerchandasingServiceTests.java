package org.springframework.samples.petclinic.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.TipoMerchandasing;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DirtiesContext
public class MerchandasingServiceTests {

	@Autowired
	private MerchandasingService merchandasingService;

	@Test
	void findMerchandasingsTest() {
		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		Assertions.assertThat(!merchandasings.isEmpty() && merchandasings.size() == 2
				&& merchandasings.get(0).getFabricante().equals("Fabricante 1")
				&& merchandasings.get(1).getTipo().equals(TipoMerchandasing.FIGURA));

	}

	// id = 31
	@Test
	void saveMerchandasingTest() {
		Merchandasing merchandasing = new Merchandasing();
		merchandasing.setId(4);
		merchandasing.setFabricante("BANDAI");
		merchandasing.setNombre("Figura de Goku");
		merchandasing.setPrecio(33.33);
		merchandasing.setTipo(TipoMerchandasing.FIGURA);
		this.merchandasingService.saveMerchandasing(merchandasing);
		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		Merchandasing merchan = merchandasings.get(2);
		Assertions.assertThat(merchandasings.size() == 3);
		Assertions.assertThat(!merchandasings.isEmpty());
		Assertions.assertThat(merchan.getId().equals(3));
		Assertions.assertThat(merchan.getNombre().equals("Figura de Goku"));
		Assertions.assertThat(merchan.getPrecio().equals(33.33));
		Assertions.assertThat(merchan.getTipo().equals(TipoMerchandasing.FIGURA));
		Assertions.assertThat(merchan.getFabricante().equals("BANDAI"));

	}

	@Test
	void deleteMerchandasingTest() {
		this.merchandasingService.deleteMerchandasing(2);
		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		Assertions.assertThat(!merchandasings.isEmpty() && merchandasings.size() == 1);
	}

	@Test
	void findMerchandasingByIdTest() {
		Merchandasing merchandasing = this.merchandasingService.findMerchandasingById(2);
		Assertions.assertThat(merchandasing.getNombre().equals("Goku SSJ4") && merchandasing.getPrecio().equals(33.33)
				&& merchandasing.getFabricante().equals("Bandai") && merchandasing.getId().equals(2)
				&& merchandasing.getTipo().equals(TipoMerchandasing.FIGURA));

	}

}
