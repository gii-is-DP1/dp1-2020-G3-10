
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.repository.MerchandasingRepository;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.repository.PeliculaRepository;
import org.springframework.samples.petclinic.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PeliculaService peliculaService;
	@Autowired
	private VideojuegoService videojuegoService;
	@Autowired
	private MerchandasingService merchandasingService;
	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public int conteoPedido() {
		return (int) this.pedidoRepository.count();

	}

	@Transactional
	public Iterable<Pedido> findAll() {
		return this.pedidoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Pedido findPedidoById(final int id) {

		Optional<Pedido> pedido = this.pedidoRepository.findById(id);

		if (!pedido.isPresent()) {
			throw new IllegalArgumentException("El pedido no existe");
		} else {
			return pedido.get();
		}
	}

	@Transactional
	public void delete(final int id) {
		
		Optional<Pedido> pedido = this.pedidoRepository.findById(id);

		if (!pedido.isPresent()) {
			throw new IllegalArgumentException("El pedido que quiere borrar no existe");
		} else {
			this.pedidoRepository.deleteById(id);
		}

	}
	
	@Transactional
	public void añadirProductoCarrito(int productoId, String usuario, String tipo) {
		
		Pedido pedido = null;
		Cliente cliente = clienteRepository.findByUsername(usuario);
		
		Collection<Pedido> pedidos = cliente.getPedidos();
		
		System.out.println("PRUEBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+pedidos==null);
		
		if(pedidos != null) {
			for (Pedido p : pedidos) {
				if (p.getEstado() == EstadoPedido.CARRITO) {

					switch (tipo) {
					case "PELICULA":
						Collection<Pelicula> peliculas = p.getPeliculas();
						Pelicula pelicula = peliculaService.findPeliculaById(productoId);
						peliculas.add(pelicula);
						p.setPrecioTotal(p.getPrecioTotal() + pelicula.getPrecio());
						p.setPeliculas(peliculas);
						break;
					case "VIDEOJUEGO":
						Collection<Videojuego> videojuegos = p.getVideojuegos();
						Videojuego videojuego = videojuegoService.findVideojuegoById(productoId);
						videojuegos.add(videojuego);
						p.setPrecioTotal(p.getPrecioTotal() + videojuego.getPrecio());
						p.setVideojuegos(videojuegos);
						break;
					case "MERCHANDASING":
						Collection<Merchandasing> merchandasings = p.getMerchandasings();
						Merchandasing merchandasing = merchandasingService.findMerchandasingById(productoId);
						merchandasings.add(merchandasing);
						p.setMerchandasings(merchandasings);
						p.setPrecioTotal(p.getPrecioTotal() + merchandasing.getPrecio());
						break;
					default:
						throw new IllegalArgumentException("El tipo no es correcto");
					}
					pedido = p;
				}
			}
		}
		
		if(pedido == null) {
			pedido = new Pedido();
			pedido.setCliente(cliente);
			pedido.setEstado(EstadoPedido.CARRITO);
			switch(tipo) {
				case "PELICULA":
					Pelicula pelicula = peliculaService.findPeliculaById(productoId);
					pedido.getPeliculas().add(pelicula);
					pedido.setPrecioTotal(pedido.getPrecioTotal() + pelicula.getPrecio());
					break;
				case "VIDEOJUEGO":
					Videojuego videojuego = videojuegoService.findVideojuegoById(productoId);
					pedido.getVideojuegos().add(videojuego);
					pedido.setPrecioTotal(pedido.getPrecioTotal() + videojuego.getPrecio());
					break;
				case "MERCHANDASING":
					Merchandasing merchandasing = merchandasingService.findMerchandasingById(productoId);
					pedido.getMerchandasings().add(merchandasing);
					pedido.setPrecioTotal(pedido.getPrecioTotal() + merchandasing.getPrecio());
					break;
				default:
					throw new IllegalArgumentException("El tipo no es correcto");
			}
		}
		
		pedidoRepository.save(pedido);
		Collection<Pedido> actualPedidos = cliente.getPedidos();
		actualPedidos.add(pedido);
		cliente.setPedidos(actualPedidos);
		clienteRepository.save(cliente);
	}
	


	@Transactional
	public void save(final Pedido pedido) {
		//Cliente cliente = new Cliente();
		//List<Pedido> pedidos = (List<Pedido>) cliente.getPedidos();
		//pedidos.get(0).getEstado() == EstadoPedido.CARRITO
		if(pedido.getPeliculas()==null) {
			throw new IllegalArgumentException("No puede hacerse un pedido sin productos");
		}else {
			this.pedidoRepository.save(pedido);
		}
	}
}
