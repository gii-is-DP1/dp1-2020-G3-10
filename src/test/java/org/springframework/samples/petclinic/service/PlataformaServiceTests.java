/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PlataformaServiceTests {        

	@Autowired
	protected PlataformaService platS;

	@Test
	@Transactional
	public void shouldInsertPlataforma() {

		List<Plataforma> plataformas = (List<Plataforma>) this.platS.findAllPlataforma();

		Integer nplataformas = plataformas.size();

		Plataforma p = new Plataforma();
		p.setNombre("PS6");
		p.setDescripcion("Aún más nueva, aún más cara, aún peor que un PC");

		this.platS.savePlataforma(p);
		assertThat(p.getId().longValue()).isNotEqualTo(0);

		plataformas = (List<Plataforma>) this.platS.findAllPlataforma();
		assertThat(plataformas.size()).isEqualTo(nplataformas + 1);

	}
	
	@Test
	@Transactional
	public void shouldDeletePlataforma() {
		
		List<Plataforma> plataformas = (List<Plataforma>) this.platS.findAllPlataforma();

		Integer nplataformas = plataformas.size();
		
		this.platS.delete(plataformas.get(0).getId());
		
		plataformas = (List<Plataforma>) this.platS.findAllPlataforma();
			
		assertThat(plataformas.size()).isEqualTo(nplataformas - 1);
		
	}
}
