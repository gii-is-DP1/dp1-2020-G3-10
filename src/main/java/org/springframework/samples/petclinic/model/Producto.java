package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Producto extends BaseEntity {
	

	private String nombre;
	
	private String descripcion;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaSalida;
	
	private Double precio;
	
	private String imagen;
	
	
	
	
	
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
