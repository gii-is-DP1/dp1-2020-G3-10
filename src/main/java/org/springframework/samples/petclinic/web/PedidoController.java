
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private VideojuegoService videojuegoService;
	@Autowired
	private MerchandasingService merchandasingService;
	@Autowired
	private PeliculaService peliculaService;
	
	private List<Producto> carrito = new ArrayList<>();


	@GetMapping
	public String listadoPedidos(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";

		Iterable<Pedido> pedidos = this.pedidoService.findAll();

		modelMap.addAttribute("pedidos", pedidos);

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearPedido(final ModelMap modelMap) {

		String view = "pedidos/editPedido";

		modelMap.addAttribute("pedido", new Pedido());

		return view;

	}

	@PostMapping(path = "/save")
	public String salvarPedido(@Valid final Pedido pedido, final BindingResult result, final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("pedido", pedido);
			return "pedidos/editPedido";
		} else {
			this.pedidoService.save(pedido);
			modelMap.addAttribute("message", "Pedido guardo correctamente");
			vista = this.listadoPedidos(modelMap);
		}

		return vista;

	}
	@GetMapping(path = "/delete/{pedidoId}")
	public String borrarPedido(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "pedidos/listadoPedidos";

		Optional<Pedido> pedido = this.pedidoService.findPedidoById(pedidoId);

		if (pedido.isPresent()) {

			this.pedidoService.delete(pedido.get());
			modelMap.addAttribute("message", "Se ha borrado su pedido");
			vista = this.listadoPedidos(modelMap);
		} else {

			modelMap.addAttribute("message", "No se ha encontrado su pedido");
			vista = this.listadoPedidos(modelMap);

		}

		return vista;
	}
	
	@GetMapping(path = "/addCarrito/{productoId}/{tipo}")
	public String añadirACarrito(@PathVariable("productoId") final int productoId, @PathVariable("tipo") final String tipo, final ModelMap modelMap) {

		String vista = "/";
		
		Producto producto = null ;
		
		switch(tipo) {
			case "PELICULA":
				producto = peliculaService.findPeliculaById(productoId);
			case "VIDEOJUEGO":
				producto = videojuegoService.findVideojuegoById(productoId);
			default:
				producto = merchandasingService.findMerchandasingById(productoId);
		}
		
		//carrito.add(producto);
		
		//System.out.println("CARRITO PRUEBA: "+carrito);
		System.out.println("TIPO PRODUCTO: "+ tipo + "IDENTIFICACION: "+productoId);
		
		//nuevoProducto.getClass().getSimpleName()
		//Optional<Pedido> pedido = this.pedidoService.findPedidoById(pedidoId);

		return vista;
	}
	
	@GetMapping(path = "/carrito")
	public String listCarrito(final ModelMap modelMap) {

		String vista = "/";
		
		//System.out.println("CARRITO PRUEBA: "+carrito);
		System.out.println("LLEGA AL CARRITO");
		
		return vista;
		
	}

}
