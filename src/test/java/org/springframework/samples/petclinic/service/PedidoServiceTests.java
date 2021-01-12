
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
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
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PedidoServiceTests {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PeliculaService peliculaService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClienteRepository clienteRepository;
	
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
		pedido.setEstado(EstadoPedido.CARRITO);
				
		return pedido;
	}


	@Test
	public void testCountWithInitialData() {

		int count = this.pedidoService.conteoPedido();
		Assertions.assertThat(count).isEqualTo(0);

	}
	
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
		
		Pedido p = this.getDummyPedido1();
		p.setCliente(cliente);
		pedidoRepository.save(p);
		Pedido pedido = pedidoRepository.findById(1).get();
		System.out.println("PEDIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: " + pedido.getPeliculas());
		
		Pelicula pelicula = peliculaService.findPeliculaById(3);
		System.out.println("PELICULAAAAAAAAAAAAAAAAAAAAAAAAAA: " + pelicula.getNombre());
		
		
		///// PROBAR CARRITO
		
		pedidoService.añadirProductoCarrito(3, "marta", "PELICULA");
		Pedido cambiado =  pedidoRepository.findById(1).get();
		System.out.println("PEDIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOO CAMBIADOOOOOOOOOOOOOOOOOOOOOOOOOO: " + cambiado.getPeliculas());
	}


}
