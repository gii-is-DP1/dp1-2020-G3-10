package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.Optional;

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
	private static final int TEST_PEDIDO_ID_6 = 6;
	private static final int TEST_PEDIDO_ID_7 = 7;

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
	private Pedido pedido6;
	private Pedido pedido7;

	private Pelicula pelicula;
	private Pelicula pelicula2;
	
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
		user2.setPassword("usuarioSinCarrito");
		user2.setEnabled(true);
		
		authorities2 = new Authorities();
		authorities2.setId(TEST_AUTHORITIES_ID_2);
		authorities2.setUser(user2);
		authorities2.setAuthority("cliente");
		
		cliente2 = new Cliente();
		cliente2.setId(TEST_CLIENTE_ID_1);
		cliente2.setCartera(1000.0);
		cliente2.setUser(user2);
		cliente2.setDni("12345632Y");
		cliente2.setCodigoPostal("41180");
		cliente2.setComentarios(null);
		cliente2.setApellidos("ApellidoCliente2");
		cliente2.setNombre("NombrePrueba1");
		cliente2.setCiudad("Sevilla");
		cliente2.setFechaNacimiento(LocalDate.of(1998, 5, 20));
		cliente2.setEmail("email@email.es");
		cliente2.setDireccion("Direccion Prueba 2");
		cliente2.setTelefono("123456789");
		cliente2.setReproductores(null);
		cliente2.setTarjetaCredito("1212232334345566");
		
		

		
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
		
		pelicula2 = new Pelicula();
		pelicula2.setId(TEST_PELICULA_ID_2);
		pelicula2.setNombre("pelicula2");
		pelicula2.setPrecio(12.50);
		pelicula2.setAgno(2023);
		pelicula2.setDirector("director2");
		pelicula2.setDuracion(2.6);
		pelicula2.setEdicion(2);
		pelicula2.setFormato(Formato.DVD);
		pelicula2.setImagen("https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp");
		pelicula2.setDescripcion("DescPrueba");
		pelicula2.setFechaSalida(LocalDate.now());
		
		
		
		
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
		
		
		
		pedido6 = new Pedido();
		pedido6.setId(TEST_PEDIDO_ID_6);
		pedido6.setDireccionEnvio("Direccion Prueba Numero 6");
		pedido6.setEstado(EstadoPedido.PENDIENTE);
		pedido6.setPrecioTotal(150.0);
		pedido6.setFecha(LocalDate.of(2019, 12, 15));
		pedido6.setCliente(cliente2);
		pedido6.setMerchandasings(null);
		pedido6.setPeliculas(null);
		pedido6.setVideojuegos(null);
		
		pedido7 = new Pedido();
		pedido7.setId(TEST_PEDIDO_ID_7);
		pedido7.setDireccionEnvio("Direccion Prueba Numero 7");
		pedido7.setEstado(EstadoPedido.ENTREGADO);
		pedido7.setPrecioTotal(150.0);
		pedido7.setFecha(LocalDate.of(2019, 12, 15));
		pedido7.setCliente(cliente2);
		pedido7.setMerchandasings(null);
		pedido7.setPeliculas(null);
		pedido7.setVideojuegos(null);
		
		
		List<Pedido> listaPedidosCliente = new ArrayList<>();
		listaPedidosCliente.add(pedido1);
		listaPedidosCliente.add(pedido2);
		given(this.pedidoService.findPedidosCliente(TEST_USER_ID_USERNAME_1)).willReturn(listaPedidosCliente);
		
		given(pedidoService.carritoContieneProducto(TEST_PELICULA_ID_1, "usuario1", "PELICULA")).willReturn(true);
		given(pedidoService.carritoContieneProducto(TEST_PELICULA_ID_2, "usuario1", "PELICULA")).willReturn(false);
		
		

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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/mostrarCarrito"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/pedidos/mostrarCarrito"));

	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void añadirACarritoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/addCarrito/{productoId}/{tipo}",TEST_PELICULA_ID_1,"PELICULA"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/pedidos/mostrarCarrito"));
	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void eliminaProductoCarritoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/eliminaProductoCarrito/{pedidoId}/{productoId}/{tipo}",TEST_PEDIDO_ID_1,TEST_PELICULA_ID_1,"PELICULA"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("pedidos/listadoPedidos"));
	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void finalizarCarritoFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pagar"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/pedidos/pedidoCompletado"));
	}
	
	@WithMockUser(value = "usuario1")
	@Test
	void finalizarCarritoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/{pedidoId}/pagar",TEST_PEDIDO_ID_2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/pedidos/finalizarCarrito"));
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
