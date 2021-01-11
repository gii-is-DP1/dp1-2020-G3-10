
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Marta Díaz
 */
@Entity
@Getter
@Setter
@Table(name = "peliculas")
public class Pelicula extends Producto {

	@Override
	public String toString() {
		return "Pelicula [director=" + director + ", agno=" + agno + ", duracion=" + duracion 
				+ ", formato=" + formato + ", edicion=" + edicion + ", imagen=" + getImagen() + ", comentarios="
				+ comentarios + "]";
	}

	@Column(name = "director")
	@NotEmpty
	private String	director;

	@Column(name = "agno")
	private Integer	agno;
	
	@Column(name = "duracion")
	private Double	duracion;

	
	@Column(name = "formato")
	@Enumerated(EnumType.STRING)
	private Formato formato;

	@Column(name = "edicion")
	private Integer edicion;

	
	@OneToMany(mappedBy = "pelicula")
	private Collection<@Valid Comentario>	comentarios;
	
	public void addComment(Comentario comentario) {
		getComentarios().add(comentario);
	}
	
    /*
	 
	@OneToMany (mappedBy = "pelicula")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor vendedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	@Valid
	private Pedido pedido;
	
	*/
	
}
