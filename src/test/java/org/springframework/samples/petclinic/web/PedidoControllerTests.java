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
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
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


@WebMvcTest(controllers = PeliculaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PedidoControllerTests {
	
	private static final String TEST_USER_ID_USERNAME = "usuario1";
	
	private static final int TEST_AUTHORITIES_ID_1 = 1;
	
	private static final int TEST_CLIENTE_ID_1 = 1;

	private static final int TEST_PELICULA_ID_1 = 1;
	private static final int TEST_PELICULA_ID_2 = 1;
	private static final int TEST_PELICULA_ID_3 = 1;
	
	private static final int TEST_PEDIDO_ID_1 = 1;
	private static final int TEST_PEDIDO_ID_2 = 2;
	private static final int TEST_PEDIDO_ID_3 = 3;
	private static final int TEST_PEDIDO_ID_4 = 4;

	@Autowired
	private PeliculaController peliculaController;

	@MockBean
	private PeliculaService peliculaService;

	@MockBean
	private ProductoService productoService;
	
	@MockBean
	private PedidoService pedidoService;

	@Autowired
	private MockMvc mockMvc;
	
	private User user1;
	
	private Authorities authorities;
	
	private Cliente cliente1;
	
	private Pedido pedido1;
	private Pedido pedido2;
	private Pedido pedido3;

	private Pelicula pelicula;
	
	private Videojuego videojuego;
	
	private Merchandasing merchandasing;

	@BeforeEach
	void setup() {
		
		user1 = new User();
		user1.setUsername(TEST_USER_ID_USERNAME);
		user1.setPassword("usuario1");
		user1.setEnabled(true);
		
		authorities = new Authorities();
		authorities.setId(TEST_AUTHORITIES_ID_1);
		authorities.setUser(user1);
		authorities.setAuthority("cliente");
		
		cliente1 = new Cliente();
		cliente1.setId(TEST_CLIENTE_ID_1);
		cliente1.setCartera(1000.0);

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
		given(this.peliculaService.findPeliculaById(TEST_PELICULA_ID_1)).willReturn(pelicula);
		
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
		pedido2.setEstado(EstadoPedido.ENTREGADO);
		pedido2.setPrecioTotal(150.0);
		pedido2.setFecha(LocalDate.of(2019, 12, 15));
		pedido2.setCliente(cliente1);
		pedido2.setMerchandasings(null);
		pedido2.setPeliculas(null);
		pedido2.setVideojuegos(null);
		
		List<Pedido> listaPedidosCliente = new ArrayList<>();
		listaPedidosCliente.add(pedido1);
		listaPedidosCliente.add(pedido2);
		given(this.pedidoService.findPedidosCliente(TEST_USER_ID_USERNAME)).willReturn(listaPedidosCliente);

	}

	// /pedidos/cliente
	@WithMockUser(value = "spring")
	@Test
	void testListPedidosCliente() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/cliente"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/pedidos/listadoPedidos"));

	}
	
	

}
