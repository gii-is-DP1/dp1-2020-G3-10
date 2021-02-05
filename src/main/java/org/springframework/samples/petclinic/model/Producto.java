package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Producto extends BaseEntity {
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String descripcion;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaSalida;
	
	private Double precio;
	
	@NotNull
	private String imagen;
	
	

}
