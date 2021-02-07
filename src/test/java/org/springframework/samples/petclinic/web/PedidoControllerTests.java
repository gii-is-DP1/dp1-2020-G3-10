package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Formato;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = PedidoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PedidoControllerTests {
	
	private static final String TEST_USER_ID_USERNAME_1 = "usuario1";
	private static final String TEST_USER_ID_USERNAME_2 = "usuarioSinCarrito";
	
	private static final int TEST_AUTHORITIES_ID_1 = 1;
	private static final int TEST_AUTHORITIES_ID_2 = 2;
	
	private static final int TEST_CLIENTE_ID_1 = 1;
	private static final int TEST_CLIENTE_ID_2 = 2;

	private static final int TEST_PELICULA_ID_1 = 1;
	private static final int TEST_PELICULA_ID_2 = 1;
	private static final int TEST_PELICULA_ID_3 = 1;
	
	private static final int TEST_PEDIDO_ID_1 = 1;
	private static final int TEST_PEDIDO_ID_2 = 2;
	private static final int TEST_PEDIDO_ID_3 = 3;
	private static final int TEST_PEDIDO_ID_4 = 4;
	private static final int TEST_PEDIDO_ID_5 = 5;

	@Autowired
	private PedidoController pedidoController;

	@MockBean
	private VendedorService vendedorService;
	@MockBean
	private ProductoService productoService;
	@MockBean
	private PedidoService pedidoService;
	@MockBean
	private VideojuegoService videojuegoService;
	@MockBean
	private MerchandasingService merchandasingService;
	@MockBean
	private PeliculaService peliculaService;
	@MockBean
	private UserService userService;


	@Autowired
	private MockMvc mockMvc;
	
	private User user1;
	private User user2;
	
	private Authorities authorities1;
	private Authorities authorities2;
	
	private Cliente cliente1;
	private Cliente cliente2;
	
	private Pedido pedido1;
	private Pedido pedido2;
	private Pedido pedido3;
	private Pedido pedido4;
	private Pedido pedido5;

	private Pelicula pelicula;
	
	private Videojuego videojuego;
	
	private Merchandasing merchandasing;

	@BeforeEach
	void setup() {
		
		user1 = new User();
		user1.setUsername(TEST_USER_ID_USERNAME_1);
		user1.setPassword("usuario1");
		user1.setEnabled(true);
		
		authorities1 = new Authorities();
		authorities1.setId(TEST_AUTHORITIES_ID_1);
		authorities1.setUser(user1);
		authorities1.setAuthority("cliente");
		
		cliente1 = new Cliente();
		cliente1.setId(TEST_CLIENTE_ID_1);
		cliente1.setCartera(1000.0);
		cliente1.setUser(user1);
		cliente1.setDni("12345612Y");
		cliente1.setCodigoPostal("41980");
		cliente1.setComentarios(null);
		cliente1.setApellidos("Apellido1");
		cliente1.setNombre("NombrePrueba");
		cliente1.setCiudad("Sevilla");
		cliente1.setFechaNacimiento(LocalDate.of(1998, 5, 20));
		cliente1.setEmail("email@email.es");
		cliente1.setDireccion("Direccion 1");
		cliente1.setTelefono("123456789");
		cliente1.setReproductores(null);
		cliente1.setTarjetaCredito("1212 2323 3434 5566");
		
		
		
		user2 = new User();
		user2.setUsername(TEST_USER_ID_USERNAME_2);
		user2.setPassword("usuario1");
		user2.setEnabled(true);
		
		authorities2 = new Authorities();
		authorities2.setId(TEST_AUTHORITIES_ID_2);
		authorities2.setUser(user2);
		authorities2.setAuthority("cliente");
		
		cliente1 = new Cliente();
		cliente1.setId(TEST_CLIENTE_ID_1);
		cliente1.setCartera(1000.0);
		cliente1.setUser(user2);
		cliente1.setDni("12345612Y");
		cliente1.setCodigoPostal("41980");
		cliente1.setComentarios(null);
		cliente1.setApellidos("Apellido1");
		cliente1.setNombre("NombrePrueba");
		cliente1.setCiudad("Sevilla");
		cliente1.setFechaNacimiento(LocalDate.of(1998, 5, 20));
		cliente1.setEmail("email@email.es");
		cliente1.setDireccion("Direccion 1");
		cliente1.setTelefono("123456789");
		cliente1.setReproductores(null);
		cliente1.setTarjetaCredito("1212 2323 3434 5566");
		
		

		
		pelicula = new Pelicula();
		pelicula.setId(TEST_PELICULA_ID_1);
		pelicula.setNombre("pelicula1");
		pelicula.setPrecio(12.50);
		pelicula.setAgno(2023);
		pelicula.setDirector("director");
		pelicula.setDuracion(2.5);
		pelicula.setEdicion(2);
		pelicula.setFormato(Formato.DVD);
		pelicula.setImagen("https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp");
		pelicula.setDescripcion("Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.");
		pelicula.setFechaSalida(LocalDate.now());
		
		
		
		
		pedido1 = new Pedido();
		pedido1.setId(TEST_PEDIDO_ID_1);
		pedido1.setDireccionEnvio("Direccion Prueba Numero 1");
		pedido1.setEstado(EstadoPedido.PENDIENTE);
		pedido1.setPrecioTotal(10.0);
		pedido1.setFecha(LocalDate.of(2020, 12, 15));
		pedido1.setCliente(cliente1);
		pedido1.setMerchandasings(null);
		pedido1.setPeliculas(null);
		pedido1.setVideojuegos(null);
		
		pedido2 = new Pedido();
		pedido2.setId(TEST_PEDIDO_ID_2);
		pedido2.setDireccionEnvio("Direccion Prueba Numero 2");
		pedido2.setEstado(EstadoPedido.PENDIENTE);
		pedido2.setPrecioTotal(150.0);
		pedido2.setFecha(LocalDate.of(2019, 12, 15));
		pedido2.setCliente(cliente1);
		pedido2.setMerchandasings(null);
		pedido2.setPeliculas(null);
		pedido2.setVideojuegos(null);
		
		pedido3 = new Pedido();
		pedido3.setId(TEST_PEDIDO_ID_3);
		pedido3.setDireccionEnvio("Direccion Prueba Numero 3");
		pedido3.setEstado(EstadoPedido.PENDIENTE);
		pedido3.setPrecioTotal(150.0);
		pedido3.setFecha(LocalDate.of(2019, 12, 15));
		pedido3.setCliente(cliente1);
		pedido3.setMerchandasings(null);
		pedido3.setPeliculas(null);
		pedido3.setVideojuegos(null);
		
		pedido4 = new Pedido();
		pedido4.setId(TEST_PEDIDO_ID_4);
		pedido4.setDireccionEnvio("Direccion Prueba Numero 4");
		pedido4.setEstado(EstadoPedido.ENVIADO);
		pedido4.setPrecioTotal(150.0);
		pedido4.setFecha(LocalDate.of(2019, 12, 15));
		pedido4.setCliente(cliente1);
		pedido4.setMerchandasings(null);
		pedido4.setPeliculas(null);
		pedido4.setVideojuegos(null);
		
		pedido5 = new Pedido();
		pedido5.setId(TEST_PEDIDO_ID_5);
		pedido5.setDireccionEnvio("Direccion Prueba Numero 5");
		pedido5.setEstado(EstadoPedido.CARRITO);
		pedido5.setPrecioTotal(150.0);
		pedido5.setFecha(LocalDate.of(2019, 12, 15));
		pedido5.setCliente(cliente1);
		pedido5.setMerchandasings(null);
		pedido5.setPeliculas(null);
		pedido5.setVideojuegos(null);
		
		List<Pedido> listaPedidosCliente = new ArrayList<>();
		listaPedidosCliente.add(pedido1);
		listaPedidosCliente.add(pedido2);
		given(this.pedidoService.findPedidosCliente(TEST_USER_ID_USERNAME_1)).willReturn(listaPedidosCliente);

	}

	@WithMockUser(value = "usuario1")
	@Test
	void testListPedidosClienteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void mostrarCarritoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void añadirACarritoExistenteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));

	}
	
	@WithMockUser(value = "usuarioSinCarrito")
	@Test
	void añadirACarritoNuevoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void eliminaProductoCarritoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));
	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void borrarPedidoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/delete/{pedidoId}",TEST_PEDIDO_ID_1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void cancelarPedidoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cancelarPedido/{pedidoId}",TEST_PEDIDO_ID_2))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("redirect:/pedidos/cliente"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void cancelarPedidoEnviado() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cancelarPedido/{pedidoId}",TEST_PEDIDO_ID_4))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError())
					.andExpect(MockMvcResultMatchers.view().name("redirect:/pedidos/cliente"));

	}
	
	

}
