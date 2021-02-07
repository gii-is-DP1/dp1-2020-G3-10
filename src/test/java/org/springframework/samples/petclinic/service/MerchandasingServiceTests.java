package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

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

	
	@Test
	@Transactional
	void saveMerchandasingTest() {
		Merchandasing merchandasing = new Merchandasing();
		merchandasing.setId(4);
		merchandasing.setFabricante("BANDAI");
		merchandasing.setNombre("Figura de Goku");
		merchandasing.setPrecio(33.33);
		merchandasing.setDescripcion("Esta es la descripción del producto de prueba 1, un goku super saiyan 4 del todo a cien");
		merchandasing.setTipo(TipoMerchandasing.FIGURA);
		merchandasing.setImagen("https://www.lamansiondelterror.es/4276-large_default/super-saiyan-4-son-goku-figura-15-cm-dragon-ball-super-son-goku-fes.jpg");
		this.merchandasingService.saveMerchandasing(merchandasing);
		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		Merchandasing merchan = merchandasings.get(2);
		Assertions.assertThat(merchandasings.size() == 3);
		Assertions.assertThat(!merchandasings.isEmpty());
		Assertions.assertThat(merchan.getId().equals(3));
		Assertions.assertThat(merchan.getNombre().equals("Figura de Goku"));
		Assertions.assertThat(merchan.getPrecio().equals(33.33));
		Assertions.assertThat(merchan.getDescripcion().equals("Esta es la descripción del producto de prueba 1, un goku super saiyan 4 del todo a cien"));
		Assertions.assertThat(merchan.getTipo().equals(TipoMerchandasing.FIGURA));
		Assertions.assertThat(merchan.getImagen().equals("https://www.lamansiondelterror.es/4276-large_default/super-saiyan-4-son-goku-figura-15-cm-dragon-ball-super-son-goku-fes.jpg"));
		Assertions.assertThat(merchan.getFabricante().equals("BANDAI"));

	}

	@Test
	@Transactional
	void deleteMerchandasingTest() {
		this.merchandasingService.deleteMerchandasing(4);
		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		Assertions.assertThat(!merchandasings.isEmpty() && merchandasings.size() == 1);
	}

	@Test
	@Transactional
	void findMerchandasingByIdTest() {
		Merchandasing merchandasing = this.merchandasingService.findMerchandasingById(2);
		Assertions.assertThat(merchandasing.getNombre().equals("Goku SSJ4") && merchandasing.getPrecio().equals(33.33)
				&& merchandasing.getFabricante().equals("Bandai") && merchandasing.getId().equals(2)
				&& merchandasing.getDescripcion().equals("Un Goku plasticoso y chinoso más, tal cual ;)")
				&& merchandasing.getImagen().equals("https://www.comicalia.com/39974-thickbox_default/super-saiyan-4-son-goku-figura-18-cm-dragon-ball-gt-full-scratch.jpg")
				&& merchandasing.getTipo().equals(TipoMerchandasing.FIGURA));

	}

}
