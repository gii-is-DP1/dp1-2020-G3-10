
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
		Assertions.assertTrue(count==3);

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

		Assert.assertTrue(pedidoCarrito.getPeliculas().contains(peliculaRepository.findById(3).get()));
	}

	@Test
	void creaPedidoCarritoPeliculaSuccess() {

		pedidoService.añadirProductoCarrito(6, "marta", "PELICULA");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getPeliculas().contains(peliculaRepository.findById(6).get()));

	}
	
	@Test
	void creaPedidoCarritoVideojuegoSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "VIDEOJUEGO");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getVideojuegos().contains(videojuegoRepository.findVideojuegoById(1).get()));

	}
	
	@Test
	void creaPedidoCarritoMerchandasingSuccess() {
		
		int productoId = 1;

		pedidoService.añadirProductoCarrito(productoId, "marta", "MERCHANDASING");
		
		Pedido pedidoCarritoNuevo = pedidoService.findProductosCarritoByClienteId("marta").get();

		Assert.assertTrue(pedidoCarritoNuevo.getMerchandasings().contains(merchandasingRepository.findById(productoId).get()));

	}
	
	@Test
	void carritoContienePeliculaSuccess() {
		
		pedidoService.añadirProductoCarrito(6, "marta", "PELICULA");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(6, "marta", "PELICULA");

		Assert.assertTrue(contieneProducto);

	}
	
	@Test
	void carritoContieneVideojuegoSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "VIDEOJUEGO");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(1, "marta", "VIDEOJUEGO");

		Assert.assertTrue(contieneProducto);

	}
	
	@Test
	void carritoContieneMerchandasingSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "MERCHANDASING");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(1, "marta", "MERCHANDASING");

		Assert.assertTrue(contieneProducto);

	}
	
	@Test
	void carritoContieneProductoConTipoIncorrecto() {
		
		pedidoService.añadirProductoCarrito(1, "marta", "MERCHANDASING");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pedidoService.carritoContieneProducto(1, "marta", "MERCHANestaMalEscrito");
		});
	
	}
	
	@Test
	void carritoEliminaProductoPeliculaSuccess() {
		
		pedidoService.añadirProductoCarrito(6, "marta", "PELICULA");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(6, "marta", "PELICULA");

		Assert.assertTrue(contieneProducto);
		
		Pedido pedidoCarrito = pedidoService.findProductosCarritoByClienteId("marta").get();
		
		pedidoService.eliminaProductoCarrito(pedidoCarrito.getId(), 6, "PELICULA");
		
		Boolean contieneProductoAfter = pedidoService.carritoContieneProducto(6, "marta", "PELICULA");

		Assert.assertTrue(!contieneProductoAfter);

	}
	
	@Test
	void carritoEliminaProductoVideojuegoSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "VIDEOJUEGO");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(1, "marta", "VIDEOJUEGO");

		Assert.assertTrue(contieneProducto);
		
		Pedido pedidoCarrito = pedidoService.findProductosCarritoByClienteId("marta").get();
		
		pedidoService.eliminaProductoCarrito(pedidoCarrito.getId(), 1, "VIDEOJUEGO");
		
		Boolean contieneProductoAfter = pedidoService.carritoContieneProducto(1, "marta", "VIDEOJUEGO");

		Assert.assertTrue(!contieneProductoAfter);

	}
	
	@Test
	void carritoEliminaProductoMerchandasingSuccess() {

		pedidoService.añadirProductoCarrito(1, "marta", "MERCHANDASING");
		
		Boolean contieneProducto = pedidoService.carritoContieneProducto(1, "marta", "MERCHANDASING");

		Assert.assertTrue(contieneProducto);
		
		Pedido pedidoCarrito = pedidoService.findProductosCarritoByClienteId("marta").get();
		
		pedidoService.eliminaProductoCarrito(pedidoCarrito.getId(), 1, "MERCHANDASING");
		
		Boolean contieneProductoAfter = pedidoService.carritoContieneProducto(1, "marta", "MERCHANDASING");

		Assert.assertTrue(!contieneProductoAfter);

	}
	
	@Test
	void completaPedidoSuccess() {

		pedidoService.añadirProductoCarrito(2, "marta", "MERCHANDASING");
		
		Optional<Pedido> pedidoCarritoOptional = pedidoService.findProductosCarritoByClienteId("marta");
		
		Assert.assertTrue(pedidoCarritoOptional.isPresent());
		
		pedidoService.completaPedido(pedidoCarritoOptional.get());
		
		Optional<Pedido> pedidoCarritoOptionalAfter = pedidoService.findProductosCarritoByClienteId("marta");

		Assert.assertTrue(!pedidoCarritoOptionalAfter.isPresent());

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
	
	@Test
	void listaIdPeliculasCompradasSuccess() {

		pedidoService.añadirProductoCarrito(8, "marta", "PELICULA");
		
		Pedido carrito = pedidoService.findProductosCarritoByClienteId("marta").get();
		
		pedidoService.completaPedido(carrito);
		
		List<Integer> idPeliculasCompradas = pedidoService.listaIdPeliculasCompradas();
		
		Assert.assertTrue(idPeliculasCompradas.contains(8));
	}
	
	@Test
	void listaIdVideojuegosCompradosSuccess() {

		pedidoService.añadirProductoCarrito(5, "marta", "VIDEOJUEGO");
		
		Pedido carrito = pedidoService.findProductosCarritoByClienteId("marta").get();
		
		pedidoService.completaPedido(carrito);
		
		List<Integer> idVideojuegosComprados = pedidoService.listaIdVideojuegosComprados();
		
		Assert.assertTrue(idVideojuegosComprados.contains(5));

	}
	
	@Test
	void deletePedidoSuccess() {

		Optional<Pedido> optionalPedido = pedidoRepository.findById(100);
		
		Assert.assertTrue(optionalPedido.isPresent());
		
		pedidoService.deletePedidoById(100);
		
		Optional<Pedido> optionalPedidoAfter = pedidoRepository.findById(100);

		Assert.assertTrue(!optionalPedidoAfter.isPresent());

	}
	
	@Test
	void deletePedidoNoPresenteSuccess() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pedidoService.deletePedidoById(10000);
		  });

	}
	
	@Test
	void cancelaPedidoSuccess() {
		
		Integer  clienteId = 1;

		Optional<Pedido> optionalPedido = pedidoRepository.findById(200);
		
		Assert.assertTrue(optionalPedido.isPresent());
		
		Cliente cliente = clienteRepository.findById(clienteId).get();
		
		Double carteraCliente = cliente.getCartera();
		
		Double valorPedido = optionalPedido.get().getPrecioTotal();
		
		pedidoService.cancelaPedidoById(200);
		
		Optional<Pedido> optionalPedidoAfter = pedidoRepository.findById(200);

		Assert.assertTrue(!optionalPedidoAfter.isPresent());
		
		Cliente clienteActualizado = clienteRepository.findById(clienteId).get();
		
		Double carteraClienteActualizada = clienteActualizado.getCartera();
		
		Assert.assertTrue(carteraClienteActualizada == carteraCliente + valorPedido);

	}
	
	@Test
	void findPedidosClienteSuccess() {
		
		pedidoService.findPedidosCliente("marta");

	}
	
	@Test
	void findAllPedidosSuccess() {
		
		pedidoService.findAll();

	}
	
	@Test
	void cancelaPedidoNoPresenteSuccess() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pedidoService.cancelaPedidoById(10000);
		  });

	}
	
	@Test
	void cancelaPedidoYaEnviado() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pedidoService.cancelaPedidoById(300);
		  });

	}

}
