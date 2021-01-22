
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String añadirACarrito(@PathVariable("productoId") final int productoId,
			@PathVariable("tipo") final String tipo, ModelMap modelMap) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		// userDetail.getAuthorities() == "Cliente"

		String usuario = userDetail.getUsername();
		if (auth.getPrincipal() == "anonymousUser") {
			modelMap.addAttribute("mensaje", "¡Debes estar registrado para añadir al carrito!");
		} else {

			modelMap.addAttribute("mensaje", "Pedido Creado");
			if (!pedidoService.carritoContieneProducto(productoId, usuario, tipo)) {
				modelMap.addAttribute("mensaje", "¡Producto añadido!");
				pedidoService.añadirProductoCarrito(productoId, usuario, tipo);

			} else {
				modelMap.addAttribute("mensaje", "¡Ya añadiste este producto!");
			}
		}	

		return "redirect:/pedidos/mostrarCarrito";
	}

	@GetMapping(path = "/eliminaProductoCarrito/{productoId}/{tipo}")
	public String eliminaProductoCarrito(@PathVariable("productoId") final int productoId,
			@PathVariable("tipo") final String tipo, final ModelMap modelMap) {
		try {
			System.out.println("LOG: PRODUCTO ID: " + productoId + " ********************************* Tipo: " + tipo);
			pedidoService.eliminaProductoCarrito(productoId, tipo);
			modelMap.addAttribute("mensaje", "El producto se ha eliminado del carrito.");

		} catch (Exception e) {

			System.out.println(e);
			modelMap.addAttribute("mensaje", "El producto no se ha podido eliminar.");
		}
		return listCarrito(modelMap);
	}

	@GetMapping(path = "/mostrarCarrito")
	public String listCarrito(ModelMap modelMap) {

		String vista = "/pedidos/carrito";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String username = userDetail.getUsername();

		Optional<Pedido> optionalPedido = pedidoService.findProductosCarritoByClienteId(username);
		
		if (optionalPedido.isPresent()) {
			Pedido pedido = optionalPedido.get();
		
			modelMap.addAttribute("pedidoId", pedido.getId());
			modelMap.addAttribute("precioTotal", pedido.getPrecioTotal());

			modelMap.addAttribute("peliculas", pedido.getPeliculas());
			modelMap.addAttribute("videojuegos", pedido.getVideojuegos());
			modelMap.addAttribute("merchandasing", pedido.getMerchandasings());

			modelMap.addAttribute("peliculasNoVacio", !(pedido.getPeliculas().isEmpty()));
			modelMap.addAttribute("videojuegosNoVacio", !(pedido.getVideojuegos().isEmpty()));
			modelMap.addAttribute("merchandasingsNoVacio", !(pedido.getMerchandasings().isEmpty()));
			modelMap.addAttribute("carritoVacio", pedido.getPeliculas().isEmpty() && pedido.getVideojuegos().isEmpty()
					&& pedido.getMerchandasings().isEmpty());
		} else {
			modelMap.addAttribute("carritoVacio", true);
		}

		return vista;
	}
	
	@GetMapping(path = "/{pedidoId}/pagar")
    public String finalizarCarrito(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

        Pedido pedido = pedidoService.findPedidoById(pedidoId);
        Cliente cliente = pedido.getCliente();
        modelMap.addAttribute("pedido", pedido);
        modelMap.addAttribute("cliente", cliente);
        return "/pedidos/finalizarCarrito";

    }
	
	@PostMapping(value = "/{pedidoId}/pagar")
    public String processFinalizarCarritoForm(Cliente cliente, Pedido pedido, BindingResult result,
            @PathVariable("pedidoId") int pedidoId, ModelMap mp) {
        Pedido pedidoAntiguo = pedidoService.findPedidoById(pedido.getId());
        if (result.hasErrors()) {
            mp.addAttribute("cliente", cliente);
            mp.addAttribute("message", "¡Los datos introducidos no son correctos!");
            return "/pedidos/finalizarCarrito";
        } else {

            pedidoAntiguo.setFecha(LocalDate.now());
            pedidoAntiguo.setDireccionEnvio(pedido.getDireccionEnvio());
            try{
                pedidoService.completaPedido(pedidoAntiguo);
            }catch(Exception e) {
                mp.addAttribute("message", e.getMessage());
                return "/pedidos/finalizarCarrito";
            }

        }

        return "/pedidos/pedidoCompletado";
    }
	
	

}
