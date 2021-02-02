
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.repository.MerchandasingRepository;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.repository.PeliculaRepository;
import org.springframework.samples.petclinic.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTests {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PeliculaService peliculaService;
	@Autowired
	private VideojuegoService videojuegoService;
	@Autowired
	private MerchandasingService merchandasingService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PeliculaRepository peliculaRepository;
	@Autowired
	private VideojuegoRepository videojuegoRepository;
	@Autowired
	private MerchandasingRepository merchandasingRepository;

	public Pedido getDummyPedidoCarrito() {
		List<Pelicula> productos = new ArrayList<>();
		productos.add(peliculaService.findPeliculaById(1));

		Pedido pedido = new Pedido();
		pedido.setCliente(null);
		pedido.setDireccionEnvio("Direccion Prueba Numero 1");
		pedido.setPrecioTotal(10.0);
		pedido.setPeliculas(productos);
		pedido.setFecha(LocalDate.of(2019, 12, 01));
		pedido.setEstado(EstadoPedido.CARRITO);

		return pedido;
	}
	
	public Pedido getDummyPedidoCarrito2() {
		List<Pelicula> peliculas = new ArrayList<>();
		List<Videojuego> videojuegos = new ArrayList<>();
		peliculas.add(peliculaService.findPeliculaById(3));
		videojuegos.add(videojuegoService.findVideojuegoById(1));

		Pedido pedido = new Pedido();
		pedido.setCliente(null);
		pedido.setDireccionEnvio("Direccion Prueba Numero 2");
		pedido.setPrecioTotal(0.0);
		pedido.setPeliculas(peliculas);
		pedido.setVideojuegos(videojuegos);
		pedido.setFecha(LocalDate.of(2020, 12, 01));
		pedido.setEstado(EstadoPedido.CARRITO);

		return pedido;
	}

	public Pedido getDummyPedidoPendiente() {
		List<Pelicula> productos = new ArrayList<>();
		productos.add(peliculaService.findPeliculaById(4));
		productos.add(peliculaService.findPeliculaById(5));

		Pedido pedido = new Pedido();
		pedido.setCliente(null);
		pedido.setDireccionEnvio("Direccion Prueba");
		pedido.setPrecioTotal(0.0);
		pedido.setPeliculas(productos);
		pedido.setFecha(LocalDate.of(2020, 12, 01));
		pedido.setEstado(EstadoPedido.PENDIENTE);

		return pedido;
	}

	@Test
	public void testCountWithInitialData() {

		int count = this.pedidoService.conteoPedido();
		Assertions.assertTrue(count==1);

	}

	@Test
	void findPedidoSuccess() {
		
		Pedido pedidoObtenido = pedidoService.findPedidoById(100);

		Assert.assertTrue(pedidoObtenido.getId() == 100);
	}

	@Test
	void findPedidoNoExiste() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			 pedidoService.findPedidoById(5000);
		});
	
	}

	@Test
	void actualizaCarritoSuccess() {
		
		Cliente clienteMarta = clienteRepository.findByUsername("marta");

		Pedido pedido = this.getDummyPedidoCarrito();
		pedido.setCliente(clienteMarta);

		Collection<Pedido> nuevosPedidos = new ArrayList<>();
		nuevosPedidos.add(pedido);

		clienteMarta.setPedidos(nuevosPedidos);
		pedidoRepository.save(pedido);
		clienteRepository.save(clienteMarta);

		pedidoService.añadirProductoCarrito(3, "marta", "PELICULA");

		Pedido pedidoCarrito = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarrito.getPeliculas().contains(peliculaRepository.findById(3)));
	}

	@Test
	void creaPedidoCarritoPeliculaSuccess() {

		pedidoService.añadirProductoCarrito(6, "marta", "PELICULA");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getPeliculas().contains(peliculaRepository.findById(6)));

	}
	
	@Test
	void creaPedidoCarritoVideojuegoSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "VIDEOJUEGO");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getVideojuegos().contains(videojuegoRepository.findVideojuegoById(1)));

	}
	
	@Test
	void creaPedidoCarritoMerchandasingSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "MERCHANDASING");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getMerchandasings().contains(merchandasingRepository.findById(1)));

	}

	@Test
	void queryFindCarritoTest() {

		Cliente cliente = clienteRepository.findById(1);

		Pedido pedido1 = this.getDummyPedidoCarrito();
		pedido1.setEstado(EstadoPedido.CARRITO);
		pedido1 = this.getDummyPedidoCarrito();
		pedido1.setCliente(cliente);
		cliente.getPedidos().add(pedido1);
		pedidoRepository.save(pedido1);

		clienteRepository.save(cliente);

		Pedido pedidoquery = pedidoRepository.findProductosCarrito(cliente.getId()).get();

		Assert.assertTrue( pedidoquery.getPeliculas() == pedido1.getPeliculas() );
	}

}
