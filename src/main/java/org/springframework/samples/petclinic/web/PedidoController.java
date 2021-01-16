
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	@Autowired
	private UserService userService;

	/*
	 * @GetMapping public boolean esMiPedido(@AuthenticationPrincipal User user,
	 * Pedido pedido) { return pedido.getCliente().getUser() == user; }
	 */
	@GetMapping
	public String listadoPedidos(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";

		Iterable<Pedido> pedidos = this.pedidoService.findAll();

		modelMap.addAttribute("pedidos", pedidos);

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearPedido(final ModelMap modelMap) {

		String vista = "pedidos/editPedido";

		Pedido pedido = new Pedido();
		pedido.setEstado(EstadoPedido.CARRITO);

		modelMap.addAttribute("pedido", new Pedido());

		return vista;

	}

	@PostMapping(path = "/save")
	public String salvarPedido(@Valid final Pedido pedido, final BindingResult result, final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("pedido", pedido);
			return "pedidos/editPedido";
		} else {
			this.pedidoService.save(pedido);
			// modelMap.addAttribute("message", "Pedido guardado"); do correctamente");
			vista = this.listadoPedidos(modelMap);
		}

		return vista;
	}

	@GetMapping(path = "/delete/{pedidoId}")
	public String borrarPedido(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "pedidos/listadoPedidos";

		try {
			pedidoService.delete(pedidoId);
			modelMap.addAttribute("message", "El pedido se ha borrado satisfactoriamente.");
		} catch (Exception e) {
			modelMap.addAttribute("message", "El pedido no ha podido borrarse");
		}

		return vista;
	}

	@GetMapping(path = "/addCarrito/{productoId}/{tipo}")
	public String añadirACarrito(@PathVariable("productoId") final int productoId, @PathVariable("tipo") final String tipo, final ModelMap modelMap) {

		String vista = "pedidos/carrito";
	
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    
	    //userDetail.getAuthorities() == "Cliente"
	    String usuario = userDetail.getUsername();
	    if(auth.getPrincipal() == "anonymousUser") {
	            modelMap.addAttribute("mensaje", "¡Debes estar registrado para añadir al carrito!");
	    }else {
	    	
	    	modelMap.addAttribute("mensaje", "Pedido Creado");
	    	pedidoService.añadirProductoCarrito(productoId, usuario, tipo);
	    }
		return vista;
	}

	@GetMapping(path = "/carrito")
	public String listCarrito(final ModelMap modelMap) {

		String vista = "/pedidos/carrito";

		System.out.println("CARRITO PRUEBA: ");

		return vista;

	}

}
