
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.EstadoPedido;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.PedidoRepository;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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


	public boolean esMiPedido(Pedido pedido) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String username = userDetail.getUsername();
		
		return pedido.getCliente().getUser().getUsername() ==  username; 
	}
	
	/// REVISAR SI SE USA O NO
	@GetMapping
	public String listadoPedidos(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";

		Iterable<Pedido> pedidos = this.pedidoService.findAll();

		modelMap.addAttribute("pedidos", pedidos);

		return vista;
	}
	
	@GetMapping(path = "/cliente")
	public String listadoPedidosCliente(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String username = userDetail.getUsername();

		Collection<Pedido> pedidos = pedidoService.findPedidosCliente(username);

		modelMap.addAttribute("pedidos", pedidos);
		log.info("Obteniendo pedidos de cliente");

		return vista;
	}
	
	@GetMapping(path = "/detalles/{pedidoId}")
	public String mostrarDetallesPedido(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "pedidos/pedidoDetails";

		try {
			Pedido pedido = pedidoService.findPedidoById(pedidoId);
			modelMap.addAttribute("pedido", pedido);
			log.info("Obtenido detalles del pedido");
		} catch (Exception e) {
			modelMap.addAttribute("message", "Error al mostrar los detalles del pedido.");
			log.warn("Error obtenido al intentar mostrar los detalles del pedido");
			vista = "reedirect:/";
		}

		return vista;
	}
	
	@GetMapping(path = "/vendedor")
	public String listadoPedidosVendedor(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String username = userDetail.getUsername();

		Collection<Pedido> pedidos = pedidoService.findPedidosVendedor(username);

		modelMap.addAttribute("pedidos", pedidos);
		log.info("Obteniendo pedidos del vendedor");

		return vista;
	}
	
	@GetMapping(path = "/enviado/{pedidoId}")
	public String marcarPedidoEnviado(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "redirect:/pedidos/detalles/" + String.valueOf(pedidoId);

		try {
			Pedido pedido = pedidoService.findPedidoById(pedidoId);
			pedidoService.pedidoEnviado(pedido);
			log.info("Marcando el pedido {pedidoId} como enviado");
		} catch (Exception e) {
			modelMap.addAttribute("message", "Error al marcar como enviado.");
			log.warn("Error al marcar el pedido como enviado");
		}

		return vista;
	}
	
	@GetMapping(path = "/entregado/{pedidoId}")
	public String marcarPedidoEntregado(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "redirect:/pedidos/detalles/" + String.valueOf(pedidoId);

		try {
			Pedido pedido = pedidoService.findPedidoById(pedidoId);
			pedidoService.pedidoEntregado(pedido);
			log.info("Marcado pedido como entregado");
		} catch (Exception e) {
			modelMap.addAttribute("message", "Error al marcar como entregado.");
			log.warn("Error al marcar pedido como entregado");
		}

		return vista;
	}

	@GetMapping(path = "/delete/{pedidoId}")
	public String borrarPedido(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "pedidos/listadoPedidos";

		try {
			pedidoService.deletePedidoById(pedidoId);
			modelMap.addAttribute("message", "El pedido se ha borrado satisfactoriamente.");
			log.info("Pedido borrado");
		} catch (Exception e) {
			modelMap.addAttribute("message", "El pedido no ha podido borrarse");
			log.info("Fallo al borrar pedido");
		}

		return vista;
	}

	@GetMapping(path = "/addCarrito/{productoId}/{tipo}")
	public String añadirACarrito(@PathVariable("productoId") final int productoId,
			@PathVariable("tipo") final String tipo, ModelMap modelMap) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		String usuario = userDetail.getUsername();
		if (auth.getPrincipal() == "anonymousUser") {
			modelMap.addAttribute("mensaje", "¡Debes estar registrado para añadir al carrito!");
			log.info("Debe estar registrado para anadir al carrito");
		} else {

			modelMap.addAttribute("mensaje", "Pedido Creado");
			if (!pedidoService.carritoContieneProducto(productoId, usuario, tipo)) {
				modelMap.addAttribute("mensaje", "¡Producto añadido!");
				pedidoService.añadirProductoCarrito(productoId, usuario, tipo);
				log.info("Producto añadido en carrito");
			} else {
				modelMap.addAttribute("mensaje", "¡Ya añadiste este producto!");
			}
		}	

		return "redirect:/pedidos/mostrarCarrito";
	}

	@GetMapping(path = "/eliminaProductoCarrito/{pedidoId}/{productoId}/{tipo}")
	public String eliminaProductoCarrito(@PathVariable("pedidoId") final int pedidoId, @PathVariable("productoId") final int productoId,
			@PathVariable("tipo") final String tipo, final ModelMap modelMap) {
		try {
			pedidoService.eliminaProductoCarrito(pedidoId,productoId, tipo);
			modelMap.addAttribute("mensaje", "El producto se ha eliminado del carrito.");
			log.info("Producto eliminado del carrito");
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
			modelMap.addAttribute("precioTotal",String.format("%.2f", pedido.getPrecioTotal()));

			modelMap.addAttribute("peliculas", pedido.getPeliculas());
			modelMap.addAttribute("videojuegos", pedido.getVideojuegos());
			modelMap.addAttribute("merchandasings", pedido.getMerchandasings());

			modelMap.addAttribute("peliculasNoVacio", !(pedido.getPeliculas().isEmpty()));
			modelMap.addAttribute("videojuegosNoVacio", !(pedido.getVideojuegos().isEmpty()));
			modelMap.addAttribute("merchandasingsNoVacio", !(pedido.getMerchandasings().isEmpty()));
			modelMap.addAttribute("carritoVacio", pedido.getPeliculas().isEmpty() && pedido.getVideojuegos().isEmpty()
					&& pedido.getMerchandasings().isEmpty());
			log.info("Mostrando carrito");
		} else {
			modelMap.addAttribute("carritoVacio", true);
		}
		return vista;
	}
	
	@GetMapping(path = "/{pedidoId}/pagar")
    public String finalizarCarrito(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		try {
	        Pedido pedido = pedidoService.findPedidoById(pedidoId);
	        Cliente cliente = pedido.getCliente();
	        pedido.setDireccionEnvio(cliente.getDireccion()+" ("+cliente.getCiudad()+")");
	        modelMap.addAttribute("pedido", pedido);
	        modelMap.addAttribute("cliente", cliente);
		}catch(Exception e){
			modelMap.addAttribute("menssage", e.getMessage());
			return "redirect:/exception";
		}
        return "/pedidos/finalizarCarrito";

    }
	
	@PostMapping(value = "/pagar")
    public String processFinalizarCarritoForm(Pedido pedido, BindingResult result, ModelMap mp) {
        Pedido pedidoAntiguo = pedidoService.findPedidoById(pedido.getId());
        if (result.hasErrors()) {
            mp.addAttribute("cliente", pedido.getCliente());
            mp.addAttribute("message", "¡Los datos introducidos no son correctos!");
            return "/pedidos/finalizarCarrito";
        } else {

            pedidoAntiguo.setFecha(LocalDate.now());
            pedidoAntiguo.setDireccionEnvio(pedido.getDireccionEnvio());
            try{
            	mp.addAttribute("pedido", pedidoAntiguo);
                pedidoService.completaPedido(pedidoAntiguo);
                log.info("Pedido completado con exito");
            }catch(Exception e) {
                mp.addAttribute("message", e.getMessage());
                log.info("Fallo al intentar completar carrito");
                return "/pedidos/finalizarCarrito";
            }

        }

        return "/pedidos/pedidoCompletado";
    }
	
	@GetMapping(value = "/cancelarPedido/{pedidoId}")
    public String cancelarPedido(@PathVariable("pedidoId") int pedidoId, final ModelMap modelMap) {
		
		String vista = "redirect:/pedidos/cliente";

        try{
            
             pedidoService.cancelaPedidoById(pedidoId);
             log.info("Pedido cancelado exitosamente");
                
        }catch(Exception e) {
        	
             modelMap.addAttribute("message", e.getMessage());
             log.info("Fallo al intentar cancelar pedido");
             vista = "redirect:/exception";
             
        }

        return vista;
    }
	
	

}
