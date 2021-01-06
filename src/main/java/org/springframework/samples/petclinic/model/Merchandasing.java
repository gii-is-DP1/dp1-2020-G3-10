package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="merchandasings")
@Getter 
@Setter
public class Merchandasing extends Producto {


	
	@Column(name = "tipo")
	private TipoMerchandasing tipo;
	
	@NotBlank
	@Column(name = "fabricante")
	private String fabricante;
	
}
