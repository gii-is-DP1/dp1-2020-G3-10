package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineaPedido extends BaseEntity {
	
	
	private Integer cantidad;

	public LineaPedido() {
		super();
	}
	
	
	private Integer idCliente;
	
	
	private Pelicula pelicula;
	

	private Videojuego videojuego;
	
	
	private Merchandasing merchandising;

	public LineaPedido(Integer cantidad, Integer idCliente, Pelicula pelicula) {
		super();
		this.cantidad = cantidad;
		this.idCliente = idCliente;
		this.pelicula = pelicula;
	}

	public LineaPedido(Integer cantidad, Integer idCliente, Videojuego videojuego) {
		super();
		this.cantidad = cantidad;
		this.idCliente = idCliente;
		this.videojuego = videojuego;
	}

	public LineaPedido(Integer cantidad, Integer idCliente, Merchandasing merchandising) {
		super();
		this.cantidad = cantidad;
		this.idCliente = idCliente;
		this.merchandising = merchandising;
	}
	
	
	

	
	
	

}
