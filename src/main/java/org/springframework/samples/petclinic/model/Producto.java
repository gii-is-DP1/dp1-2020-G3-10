package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Producto extends BaseEntity {

	@NotEmpty(message = "El nombre no puede estar vacio.")
	private String nombre;

	@NotEmpty(message = "La descripción no puede estar vacia.")
	private String descripcion;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaSalida;
	
	
	private Double precio;

	@NotEmpty(message = "La imagen es obligatoria.")
	@URL(message = "Debe proporcionar una URL válida.")
	private String imagen;

}
