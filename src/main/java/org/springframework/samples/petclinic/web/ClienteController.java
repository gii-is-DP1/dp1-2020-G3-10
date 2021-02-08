
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteController{

	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "clientes/createClienteForm";
	
	private final ClienteService clienteService;
	
	private ReproductorService reproductorService;
	
	private UserService userService;

	private AuthoritiesService authoritiesService;
	
	private ComentarioService		comentarioService;
	
	private PedidoService			pedidoService;

	
	@Autowired
	public ClienteController(final ClienteService clienteService, final UserService userService, final AuthoritiesService authoritiesService, final ReproductorService reproductorService, final PedidoService pedidoService, final ComentarioService comentarioService) {
		this.clienteService = clienteService;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		this.reproductorService = reproductorService;
		this.pedidoService = pedidoService;
		this.comentarioService = comentarioService;
	}
	

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//Formulario Creación
	@GetMapping(value = "/clientes/new")
	public String getFormularioCreacion(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/clientes/new")
	public String postFormularioCreacion(@Valid Cliente cliente, BindingResult result, ModelMap mp) {	
		
		if (result.hasErrors()) {
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido actualizar correctamente " + result);
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.clienteService.saveCliente(cliente);
			this.userService.saveUser(cliente.getUser());
			this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "Cliente creado satisfactoriamente");
			return "redirect:/clientes/" + cliente.getId();
		}
	}
	
	//Formulario Editar Cliente 
	@GetMapping(value = "/clientes/{clienteId}/edit")
	public String initEditForm(@PathVariable("clienteId") final int clienteId,ModelMap mp) {		
		Cliente cliente = clienteService.findClienteById(clienteId);
		mp.addAttribute("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/clientes/{clienteId}/edit")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result,
			@PathVariable("clienteId") int clienteId, ModelMap mp) {
		
		if (result.hasErrors()) {
			
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido actualizar correctamente");
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			
			Cliente clienteAntiguo = this.clienteService.findClienteById(clienteId);
			
			//Reasigno el ID al cliente modificado y lo actualizo
			cliente.setId(clienteId);
			Set<Authorities> authorities = clienteAntiguo.getUser().getAuthorities();
			
			for(Authorities a : authorities) {
				
				a.setUser(cliente.getUser());
				
			}
			cliente.getUser().setAuthorities(authorities);
			cliente.setReproductores(clienteAntiguo.getReproductores());
			cliente.setComentarios(clienteAntiguo.getComentarios());
			this.clienteService.saveCliente(cliente);
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente se ha actualizado satisfactoriamente");
			
			return "redirect:/clientes/miPerfil";
		}
	}

	//Eliminar Cliente
	@GetMapping("/clientes/{clienteId}/delete")
	public String eliminarCliente(@PathVariable("clienteId") final int clienteId, final ModelMap mp) {

		String view = "/welcome";

		Cliente cliente = this.clienteService.findClienteById(clienteId);

		if (cliente.getPedidos() != null && !this.carritoCancelado(cliente.getPedidos())) {

			view = "/clientes/clienteDetails";
			mp.addAttribute("message", "Un cliente no se puede eliminar mientras tenga pedidos");
			mp.addAttribute("cliente", cliente);
			return view;
		}

		if (!cliente.getPedidos().isEmpty()) {

			//Como se quedan los carritos cancelados debo eliminar dichos pedidos antes de eliminar el usuario
			cliente.getPedidos().stream().forEach(p -> this.pedidoService.cancelaPedidoById(p.getId()));

		}
		
		if(!cliente.getComentarios().isEmpty()) {
			
			//Si el cliente tiene comentarios posteados pasamos a eliminarlos
			
			cliente.getComentarios().stream().forEach(c -> this.comentarioService.deleteComentario(c.getId()));
			
//			List<Comentario> comentarios = this.comentarioService.findByClientId(cliente.getId());
//			
//			for(Comentario c : comentarios) {
//				
//				this.comentarioService.deleteComment(c);
//				
//			}
			
		}

		this.clienteService.deleteCliente(cliente);
		this.userService.deleteUser(cliente.getUser());
		SecurityContextHolder.clearContext();
		mp.addAttribute("message", "Cliente eliminado con éxito");
		return view;
	}
	
	/*
	 * Si un carrito se cancela, este se muestra como un pedido en estado carrito y total 0.0 pero no se elimina de la lista de pedidos
	 * por lo que hay que apañar un método que nos diga si realmente es un carrito cancelado o no
	 */
	private Boolean carritoCancelado(final Collection<Pedido> pedidos) {

		boolean result = true;
		Double total = 0.0;

		List<Pedido> listaPedidos = (List<Pedido>) pedidos;

		for (Pedido p : listaPedidos) {

			total += p.getPrecioTotal();
		}

		if (total > 0.0) {

			result = false;
		}

		return result;

	}
		
	
	//Mostrar detalles de cliente
	@GetMapping("/clientes/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}
	
	//Redirigir a detalles de cliente sin conocer su id
	@GetMapping("/clientes/miPerfil")
	public String showCliente(ModelMap mp) {
		String vista;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User currUser = userService.findUser(auth.getName()).get(); 

		Cliente cliente = this.clienteService.findClienteByUserName(currUser.getUsername());
		 
		if(cliente == null) {
			 
			 SecurityContextHolder.clearContext();
			 vista = "redirect:/login";
			 mp.addAttribute("message", "El usuario debe volver a loggear para actualizar los cambios");
			 
		 }else {
			 
			mp.addAttribute("cliente",cliente);
			vista = "redirect:/clientes/" + cliente.getId();
			 
		 }
		 
		return vista;
	}
		
	//Redirige al cliente a la página de sus reproductores
	@GetMapping("clientes/{clienteId}/reproductores")
	public ModelAndView showReproductoresCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("/reproductores/listadoReproductores");
		
		Cliente cliente = this.clienteService.findClienteById(clienteId);
		Collection<Reproductor> reproductores = cliente.getReproductores();
		
		mav.addObject("cliente",cliente);
		mav.addObject("reproductores",reproductores);

		return mav;
	}
	
	//Redirige al cliente a la página donde añadirá reproductores
	@GetMapping("clientes/{clienteId}/addReproductores")
		public ModelAndView ClienteAnyadeReproductores(@PathVariable("clienteId") int clienteId) {
			ModelAndView mav = new ModelAndView("/reproductores/listadoReproductores");
			Cliente cliente = this.clienteService.findClienteById(clienteId);
			Collection<Reproductor> reproductoresCliente = cliente.getReproductores();
			Collection<Reproductor> reproductores = (Collection<Reproductor>) reproductorService.findAllReproductor();

			for(Reproductor r : reproductoresCliente) {
				
				if(reproductores.contains(r)) {
					
					reproductores.remove(r);
					
				}
				
			}
			
			mav.addObject("cliente",cliente);
			mav.addObject("reproductores",reproductores);
			mav.addObject("MostrarBoton", true);
			
			return mav;
		}
	
	
	
}
