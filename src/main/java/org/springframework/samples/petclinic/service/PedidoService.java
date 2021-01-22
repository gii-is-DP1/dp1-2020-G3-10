
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	@Transactional
	public boolean carritoContieneProducto(int productoId, String usuario, String tipo) {
		Boolean result = false;
		Cliente cliente = clienteRepository.findByUsername(usuario);
		Collection<Pedido> pedidosCliente = cliente.getPedidos();

		if (pedidosCliente != null) {
			for (Pedido p : pedidosCliente) {
				if (p.getEstado() == EstadoPedido.CARRITO) {
					switch (tipo) {
					case "PELICULA":
						Collection<Pelicula> peliculas = p.getPeliculas();
						for (Pelicula pel : peliculas) {
							if (pel.getId() == productoId) {
								result = true; //¿?
							}
						}
						break;
					case "VIDEOJUEGO":
						Collection<Videojuego> videojuegos = p.getVideojuegos();
						for (Videojuego vid : videojuegos) {
							if (vid.getId() == productoId) {
								result = true;
							}
						}
						break;
					case "MERCHANDASING":
						Collection<Merchandasing> merchandasings = p.getMerchandasings();
						for (Merchandasing merch : merchandasings) {
							if (merch.getId() == productoId) {
								result = true;
							}
						}
						break;
					default:
						throw new IllegalArgumentException("El tipo no es correcto");
					}

				}
			}
		}
		return result;
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
	
	@Transactional(readOnly = true)
	public Optional<Pedido> findProductosCarritoByClienteId(final String usuario) {
		Cliente cliente = clienteRepository.findByUsername(usuario);
		return this.pedidoRepository.findProductosCarrito(cliente.getId());
		
		
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

		Pedido pedido = new Pedido();
		Cliente cliente = clienteRepository.findByUsername(usuario);

		Collection<Pedido> pedidosCliente = cliente.getPedidos();

		if (pedidosCliente != null) {
			for (Pedido p : pedidosCliente) {
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

		if (pedido.getEstado() == null || pedidosCliente == null) {
			pedido = new Pedido();
			pedido.setCliente(cliente);
			pedido.setEstado(EstadoPedido.CARRITO);
			pedido.setPrecioTotal(0.0);
			switch (tipo) {
			case "PELICULA":
				Pelicula pelicula = peliculaService.findPeliculaById(productoId);
				Collection<Pelicula> peliculas = new ArrayList<Pelicula>();
				peliculas.add(pelicula);
				pedido.setPeliculas(peliculas);
				pedido.setPrecioTotal(pedido.getPrecioTotal() + pelicula.getPrecio());
				break;
			case "VIDEOJUEGO":
				Videojuego videojuego = videojuegoService.findVideojuegoById(productoId);
				Collection<Videojuego> videojuegos = new ArrayList<Videojuego>();
				videojuegos.add(videojuego);
				pedido.setVideojuegos(videojuegos);
				pedido.setPrecioTotal(pedido.getPrecioTotal() + videojuego.getPrecio());
				break;
			case "MERCHANDASING":
				Merchandasing merchandasing = merchandasingService.findMerchandasingById(productoId);
				Collection<Merchandasing> merchandasings = new ArrayList<Merchandasing>();
				merchandasings.add(merchandasing);
				pedido.setMerchandasings(merchandasings);
				pedido.setPrecioTotal(pedido.getPrecioTotal() + merchandasing.getPrecio());
				break;
			default:
				throw new IllegalArgumentException("El tipo no es correcto");
			}
		}

		pedidoRepository.save(pedido);
		Collection<Pedido> pedidosNuevos = new ArrayList<>();
		Collection<Pedido> actualPedidos = cliente.getPedidos();
		if (actualPedidos != null) {
			pedidosNuevos.addAll(actualPedidos);
		}
		System.out.println(actualPedidos);
		pedidosNuevos.add(pedido);
		cliente.setPedidos(pedidosNuevos);
		clienteRepository.save(cliente);
	}
	
	@Transactional
	public void eliminaProductoCarrito(int productoId, String tipo) {

		Pedido pedido = pedidoRepository.findById(productoId).get();
		
		System.out.println("LOG ******************************************** "+pedido.toString());
		
		switch (tipo) {
			case "PELICULA":
				Pelicula pelicula = peliculaService.findPeliculaById(productoId);
				pedido.getPeliculas().remove(pelicula);
				pedido.setPrecioTotal(pedido.getPrecioTotal() - pelicula.getPrecio());
				System.out.println("LOG: Pelicula eliminada de carrito.");
				break;
			case "VIDEOJUEGO":
				Videojuego videojuego = videojuegoService.findVideojuegoById(productoId);
				pedido.getVideojuegos().remove(videojuego);
				pedido.setPrecioTotal(pedido.getPrecioTotal() - videojuego.getPrecio());
				System.out.println("LOG: Videojuego eliminado de carrito.");
				break;
			case "MERCHANDASING":
				Merchandasing merchandasing = merchandasingService.findMerchandasingById(productoId);
				pedido.getMerchandasings().remove(merchandasing);
				pedido.setPrecioTotal(pedido.getPrecioTotal() - merchandasing.getPrecio());
				System.out.println("LOG: Merchandasings eliminada de carrito.");
				break;
			default:
				throw new IllegalArgumentException("El tipo no es correcto");
		}
		
		pedidoRepository.save(pedido);
	}
	

	@Transactional
	public void completaPedido(final Pedido pedido) {
		if (pedido.getCliente() == null && pedido.getDireccionEnvio() == null && pedido.getEstado() == null && pedido.getFecha() == null && pedido.getPrecioTotal() == null ) {
			throw new IllegalArgumentException("No puede haber campos nulos.");
		} else if (pedido.getEstado() != EstadoPedido.CARRITO) {
			throw new IllegalArgumentException("El pedido no es un carrito.");
		} else if (pedido.getCliente().getCartera() < pedido.getPrecioTotal()) {
			throw new IllegalArgumentException("El cliente no dispone de suficiente dinero en la cartera.");
		} else {
			pedido.setEstado(EstadoPedido.PENDIENTE);
			this.pedidoRepository.save(pedido);
			Cliente cliente = pedido.getCliente();
			cliente.setCartera(cliente.getCartera() - pedido.getPrecioTotal());
			this.clienteRepository.save(cliente);
		}
	}
	
	
	
	
	
	
}
