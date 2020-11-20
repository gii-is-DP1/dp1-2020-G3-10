package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntity {

	//Propiedades
	@Column(name = "dni")
	@NotEmpty
	private String	dni;

	@Column(name = "email")
	@NotEmpty
	private String	email;
	
	@Column(name = "nombre")
	@NotEmpty
	private String	nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String	apellidos;
	
	@Column(name = "direccion")
	@NotEmpty
	private String	direccion;

	@Column(name = "tarjeta_credito")
	@NotEmpty
	private String	tarjeta_credito;
	
	@Column(name = "cartera")
	@NotEmpty
	private Double cartera;

	@Column(name = "admin")
	@NotEmpty	
	private Boolean admin;
	
	/* DESCOMENTAR CUANDO ESTÉ CLARAS LAS RELACIONES y REPASAR LAS MISMAS
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	private List<Producto> deseados;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor vendedor;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<Pedido> pedidos;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mensaje_id")
	private List<Mensaje> bandeja;
	*/
	
	//Getters && Setters

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTarjeta_credito() {
		return tarjeta_credito;
	}

	public void setTarjeta_credito(String tarjeta_credito) {
		this.tarjeta_credito = tarjeta_credito;
	}

	public Double getCartera() {
		return cartera;
	}

	public void setCartera(Double cartera) {
		this.cartera = cartera;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
}
