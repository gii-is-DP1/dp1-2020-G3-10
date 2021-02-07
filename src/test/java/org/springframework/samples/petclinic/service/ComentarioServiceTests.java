package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ComentarioServiceTests {
	
	@Autowired
	protected ComentarioService comentarioService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected PeliculaService peliculaService;
	
	@Test
	void shouldFindComentariosByClientIdSuccess() {
		List<Comentario> comentariosCliente =  this.comentarioService.findByClientId(1);
		List<Comentario> comentarios = this.comentarioService.findAll();
		List<Comentario> aux = new ArrayList<>();
		for(Comentario c: comentarios) {
			if(c.getCliente().getId()==1) {
				aux.add(c);
			}
		}
		assertThat(comentariosCliente.size()).isEqualTo(aux.size());
	}
	
	@Test
	void shouldFindComentariosByClientIdNoSuccess() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			comentarioService.findByClientId(200);
		});
	}
	
	@Test
	void shouldFindComentarioByIdSuccess() {
		Comentario comentario = this.comentarioService.findCommentById(1);
		List<Comentario> comentarios = this.comentarioService.findAll();
		assertThat(comentario).isEqualTo(comentarios.get(0));
	}
	
	@Test
	void shouldFindComentarioByIdNoSuccess() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			comentarioService.findCommentById(1000);
		});
	}
	
	@Test
	@Transactional
	public void shouldInsertComentario() {
		Comentario comentario = new Comentario();
		Cliente cliente = this.clienteService.findClienteById(1);
		Pelicula pelicula = this.peliculaService.findPeliculaById(1);
		comentario.setCliente(cliente);
		comentario.setPelicula(pelicula);
		comentario.setTexto("Texto de comentario");
		comentario.setTitulo("Titulo comentario");
		
		pelicula.addComment(comentario);
		cliente.addComment(comentario);
		
		this.comentarioService.saveComment(comentario);
		List<Comentario> comentarios = this.comentarioService.findAll();
		assertThat(comentarios.get(comentarios.size()-1)).isEqualTo(comentario);
		
	}
	
	@Test
	@Transactional
	public void shouldEditComentario() {
		Comentario comentario = comentarioService.findCommentById(1);
		String textoAnt = comentario.getTexto();
		String textoNu = comentario.getTexto() + "2";
		
		comentario.setTexto(textoNu);
		assertThat(comentario.getTexto()==textoNu);
		
	}
	
	
	@Test
	void deleteComentarioSuccess() {
		
		List<Comentario> comentarios = this.comentarioService.findAll();
		Comentario c = this.comentarioService.findCommentById(1);
		
		Assertions.assertTrue(comentarios.contains(c));
		comentarios.remove(c);
		Assertions.assertTrue(!comentarios.contains(c));
		this.comentarioService.deleteComment(c);
		List<Comentario> comentarios2 = this.comentarioService.findAll();
		Assertions.assertTrue(!comentarios2.contains(c));
	}
}
