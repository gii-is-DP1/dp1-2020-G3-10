package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
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



	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoMerchandasing tipo;
	
	@NotBlank
	@Column(name = "fabricante")
	private String fabricante;
	
	@OneToMany(mappedBy = "merchandasing")
	private Collection<@Valid Comentario>	comentarios;
	
	public void addComment(Comentario comentario) {
		getComentarios().add(comentario);
	}
	
	
}
