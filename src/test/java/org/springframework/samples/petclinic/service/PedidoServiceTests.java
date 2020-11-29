
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTests {

	@Autowired
	private PedidoService pedidoService;


	@Test
	public void testCountWithInitialData() {

		int count = this.pedidoService.conteoPedido();
		Assertions.assertEquals(count, 1);

	}

}
