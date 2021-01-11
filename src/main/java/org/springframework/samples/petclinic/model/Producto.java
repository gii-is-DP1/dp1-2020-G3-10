package org.springframework.samples.petclinic.model;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Producto extends BaseEntity {
	

	private String nombre;
	
	private Double precio;
	
	/*
	@OneToMany
	private Collection<@Valid Oferta>	ofertas;
	
	@OneToMany(mappedBy = "producto")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente	cliente;
	*/

}
