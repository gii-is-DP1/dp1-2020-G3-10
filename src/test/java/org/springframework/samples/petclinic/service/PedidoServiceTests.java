
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTests {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PeliculaService peliculaService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PeliculaRepository peliculaRepository;
	
	final int pedidoId1 = 1; 
	final int pedidoId2 = 2; 
	final int pedidoId3 = 3; 
	final int pedidoId4 = 4; 
	final int pedidoId5 = 5; 

	
	public Pedido getDummyPedidoCarrito() {
		List<Pelicula> productos = new ArrayList<>();
		productos.add(peliculaService.findPeliculaById(1));
		productos.add(peliculaService.findPeliculaById(2));
		
		Pedido pedido = new Pedido();
		pedido.setCliente(null);
		pedido.setDireccionEnvio("Direccion Prueba");
		pedido.setPrecioTotal(0.0);
		pedido.setPeliculas(productos);
		pedido.setFecha(LocalDate.of(2020, 12, 01));
		pedido.setEstado(EstadoPedido.CARRITO);
				
		return pedido;
	}
	
	public Pedido getDummyPedido1() {
		List<Pelicula> productos = new ArrayList<>();
		productos.add(peliculaService.findPeliculaById(1));
		productos.add(peliculaService.findPeliculaById(2));
		
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
		Assertions.assertThat(count).isEqualTo(0);

	}
	
	/*
	
	@Test
	void savePedidoSuccess() {
		
		Pedido pedido = getDummyPedido1();
		
		pedidoService.save(pedido);
		
		Assert.assertTrue(
				pedido.getDireccionEnvio() == pedidoService.findPedidoById(1).getDireccionEnvio() &&
				pedido.getPrecioTotal() == pedidoService.findPedidoById(1).getPrecioTotal() &&
				pedido.getCliente() == pedidoService.findPedidoById(1).getCliente()
		);
	}
	
	@Test
	void findPedidoSuccess() {
		
		Pedido pedido = getDummyPedido1();
		
		pedidoService.save(pedido);
		
		Assert.assertTrue(
				pedido.getDireccionEnvio() == pedidoService.findPedidoById(1).getDireccionEnvio() &&
				pedido.getPrecioTotal() == pedidoService.findPedidoById(1).getPrecioTotal() &&
				pedido.getCliente() == pedidoService.findPedidoById(1).getCliente()
		);
	}
	
	*/
	
	@Test
	void findPedidoNoExiste() {
	
	}
	
	@Test
	void pruebaCarrito() {
		
		///// CREAR CLIENTE
		
		User user = userService.findUser("marta").get();
		System.out.println("PRUEBA USERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: "+user);
		Cliente cliente = new Cliente();
		cliente.setUser(user);
		cliente.setApellidos("prueba");
		cliente.setPedidos(null);
		cliente.setCartera(10.0);
		cliente.setCiudad("Sevilla");
		cliente.setCodigoPostal("10400");
		cliente.setDireccion("prueba");
		cliente.setTarjetaCredito("100000000");
		cliente.setFechaNacimiento(LocalDate.of(2020, 5, 12));
		cliente.setDni("12345678X");
		cliente.setNombre("nombre prueba");
		cliente.setTelefono("123456789");
		cliente.setEmail("email@email.es");
	
		clienteRepository.save(cliente);
		
		Cliente cliente2 = clienteRepository.findById(1);
		
		System.out.println("PRUEBA Cliente 2222222222222222222222222222222222222222: "+cliente2.getApellidos());
		
		Cliente cliente1 = clienteRepository.findByUsername("marta");
		
		System.out.println("PRUEBA: "+cliente1.getApellidos());
		
		
		///// CREAR PEDIDO CON PELICULAS
		
		Pedido p = this.getDummyPedidoCarrito();
		p.setCliente(cliente);
		Collection<Pedido> nuevos = new ArrayList<>();
		nuevos.add(p);
		cliente.setPedidos(nuevos);
		pedidoRepository.save(p);
		clienteRepository.save(cliente);
		Pedido pedido = pedidoRepository.findById(1).get();
		System.out.println("PEDIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: " + pedido.getPeliculas());
		
		Pelicula pelicula = peliculaService.findPeliculaById(3);
		System.out.println("PELICULAAAAAAAAAAAAAAAAAAAAAAAAAA: " + pelicula.getNombre());
		
		
		///// PROBAR CARRITO
		
		pedidoService.añadirProductoCarrito(3, "marta", "PELICULA");
		Pedido cambiado =  pedidoRepository.findById(1).get();
		System.out.println("PEDIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOO CAMBIADOOOOOOOOOOOOOOOOOOOOOOOOOO: " + cambiado.getPeliculas());
	}
	
	@Test
	void actualizaCarritoSuccess() {
		
		///// CREAR CLIENTE
		
		User user = userService.findUser("marta").get();
		Cliente cliente = new Cliente();
		cliente.setUser(user);
		cliente.setApellidos("prueba");
		cliente.setCartera(10.0);
		cliente.setCiudad("Sevilla");
		cliente.setCodigoPostal("10400");
		cliente.setDireccion("prueba");
		cliente.setTarjetaCredito("100000000");
		cliente.setFechaNacimiento(LocalDate.of(2020, 5, 12));
		cliente.setDni("12345678X");
		cliente.setNombre("nombre prueba");
		cliente.setTelefono("123456789");
		cliente.setEmail("email@email.es");
		
		/// CREAR PEDIDO
		
		Pedido pedido = this.getDummyPedidoCarrito();
		pedido.setCliente(cliente);
		
		Collection<Pedido> nuevosPedidos = new ArrayList<>();
		nuevosPedidos.add(pedido);
		
		/// GUARDAR PEDIDO Y CLIENTE
		
		cliente.setPedidos(nuevosPedidos);
		pedidoRepository.save(pedido);
		clienteRepository.save(cliente);
		
		///// PROBAR CARRITO
		
		pedidoService.añadirProductoCarrito(3, "marta", "PELICULA");
		
		Pedido cambiado =  pedidoRepository.findById(1).get();
		
		Assert.assertTrue(cambiado.getPeliculas().contains(peliculaRepository.findById(3)));
	}
	
	@Test
	void creaPedidoCarritoSuccess() {
		
	///// CREAR CLIENTE
		
			User user = userService.findUser("marta").get();
			Cliente cliente = new Cliente();
			cliente.setUser(user);
			cliente.setApellidos("prueba");
			cliente.setCartera(10.0);
			cliente.setCiudad("Sevilla");
			cliente.setCodigoPostal("10400");
			cliente.setDireccion("prueba");
			cliente.setTarjetaCredito("100000000");
			cliente.setFechaNacimiento(LocalDate.of(2020, 5, 12));
			cliente.setDni("12345678X");
			cliente.setNombre("nombre prueba");
			cliente.setTelefono("123456789");
			cliente.setEmail("email@email.es");
			
			/// CREAR PEDIDO
			
			Pedido pedido = this.getDummyPedidoCarrito();
			pedido.setEstado(EstadoPedido.ENTREGADO);
			pedido.setCliente(cliente);
			
			Collection<Pedido> nuevosPedidos = new ArrayList<>();
			nuevosPedidos.add(pedido);
			
			/// GUARDAR PEDIDO Y CLIENTE
			
			cliente.setPedidos(nuevosPedidos);
			pedidoRepository.save(pedido);
			clienteRepository.save(cliente);
			
			///// PROBAR CARRITO
			
			pedidoService.añadirProductoCarrito(3, "marta", "PELICULA");
			
			Pedido cambiado =  pedidoRepository.findById(2).get();
			
			Assert.assertTrue(cambiado.getPeliculas().contains(peliculaRepository.findById(3)));
			
	}
	
	@Test
	void creaPedidoCarritoSuccessPrueba() {
		
	///// CREAR CLIENTE
		
			User user = userService.findUser("admin1").get();
			Cliente cliente = new Cliente();
			cliente.setUser(user);
			cliente.setApellidos("prueba");
			cliente.setCartera(10.0);
			cliente.setCiudad("Sevilla");
			cliente.setCodigoPostal("10400");
			cliente.setDireccion("prueba");
			cliente.setTarjetaCredito("100000000");
			cliente.setFechaNacimiento(LocalDate.of(2020, 5, 12));
			cliente.setDni("12345678X");
			cliente.setNombre("nombre prueba");
			cliente.setTelefono("123456789");
			cliente.setEmail("email@email.es");
			
			/// CREAR PEDIDO
			/*
			Pedido pedido = this.getDummyPedido1();
			pedido.setEstado(EstadoPedido.ENTREGADO);
			pedido.setCliente(cliente);
			
			Collection<Pedido> nuevosPedidos = new ArrayList<>();
			nuevosPedidos.add(pedido);
			*/
			/// GUARDAR PEDIDO Y CLIENTE
			
			//cliente.setPedidos(nuevosPedidos);
			//pedidoRepository.save(pedido);
			clienteRepository.save(cliente);
			
			///// PROBAR CARRITO
			
			pedidoService.añadirProductoCarrito(3, "admin1", "PELICULA");
			
			Pedido cambiado =  pedidoRepository.findById(1).get();
			
			System.out.println("PEDIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOO CAMBIADOOOOOOOOOOOOOOOOOOOOOOOOOO: " + cambiado.getPeliculas());
			
			//Assert.assertTrue(cambiado.getPeliculas().contains(peliculaRepository.findById(3)));
	}
	
	@Test
	void queryFindCarritoTest() {
		
	///// CREAR CLIENTE
		
			Cliente cliente = clienteRepository.findById(1);
			
			/// CREAR PEDIDO
			
			Pedido pedido1 = this.getDummyPedidoCarrito();
			pedido1.setEstado(EstadoPedido.CARRITO);
			pedido1 = this.getDummyPedidoCarrito();
			pedido1.setCliente(cliente);		
			cliente.getPedidos().add(pedido1);
			pedidoRepository.save(pedido1);
			
			/// GUARDAR PEDIDO Y CLIENTE
			
			clienteRepository.save(cliente);
			
			Pedido pedidoquery = pedidoRepository.findProductosCarrito(cliente.getId()).get();
			System.out.println("PEDIDO REPOSITORY ####################################################: " + pedidoquery.getPeliculas() + "----------------" +pedidoquery.getEstado());
			//Assert.assertTrue(cambiado.getPeliculas().contains(peliculaRepository.findById(3)));
	}
	


}
