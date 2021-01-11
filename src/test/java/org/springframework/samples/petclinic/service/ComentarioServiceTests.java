package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ComentarioServiceTests {

	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PeliculaService peliculaService;
	
	@Test
	void deberiaCrearComentario(){
		
		Collection<Comentario> comentariosIni = (Collection<Comentario>)comentarioService.findAll();
		int numeroIni = comentariosIni.size();
		
		Comentario comentario = new Comentario();

		Cliente cliente = this.clienteService.findClienteById(3);
		Pelicula pelicula = this.peliculaService.findPeliculaById(1);
		
		comentario.setCliente(cliente);
		comentario.setPelicula(pelicula);
		comentario.setTitulo("Probando titulo");
		comentario.setTexto("Probando Texto");
		
		comentarioService.saveComment(comentario);
		
		
		Collection<Comentario> comentariosAfter = (Collection<Comentario>)comentarioService.findAll();
		int numeroAfter = comentariosAfter.size();		
		
		assertThat(numeroAfter).isEqualTo(numeroIni + 1);

	}
	
}
