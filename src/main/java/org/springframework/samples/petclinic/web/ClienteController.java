
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
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
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteController {

	private static final String		VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM	= "clientes/createClienteForm";

	private final ClienteService	clienteService;

	private ReproductorService		reproductorService;

	private UserService				userService;

	private AuthoritiesService		authoritiesService;

	private PedidoService			pedidoService;


	@Autowired
	public ClienteController(final ClienteService clienteService, final UserService userService, final AuthoritiesService authoritiesService, final ReproductorService reproductorService, final PedidoService pedidoService) {
		this.clienteService = clienteService;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		this.reproductorService = reproductorService;
		this.pedidoService = pedidoService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//Formulario Creación
	@GetMapping(value = "/clientes/new")
	public String getFormularioCreacion(final ModelMap modelmap) {
		modelmap.put("cliente", new Cliente());
		return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clientes/new")
	public String postFormularioCreacion(@Valid final Cliente cliente, final BindingResult result, final ModelMap mp) {

		if (result.hasErrors()) {
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido crear correctamente ");
			return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		} else {
			//creating owner, user and authorities
			this.clienteService.saveCliente(cliente);
			this.userService.saveUser(cliente.getUser());
			this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "Cliente creado satisfactoriamente");
			return "redirect:/login";
		}
	}

	//Formulario Editar Cliente
	@GetMapping(value = "/clientes/{clienteId}/edit")
	public String initEditForm(@PathVariable("clienteId") final int clienteId, final ModelMap mp) {
		Cliente cliente = this.clienteService.findClienteById(clienteId);
		mp.addAttribute("cliente", cliente);
		return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clientes/{clienteId}/edit")
	public String processUpdateClienteForm(@Valid final Cliente cliente, final BindingResult result, @PathVariable("clienteId") final int clienteId, final ModelMap mp, @RequestParam(value = "version", required = false) final Integer version) {

		if (result.hasErrors()) {

			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido actualizar correctamente");
			return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		} else {

			Cliente clienteAntiguo = this.clienteService.findClienteById(clienteId);

			//Reasigno el ID al cliente modificado y lo actualizo
			cliente.setId(clienteId);

			Integer version2 = cliente.getVersion();

			if (version2 != version) {
				mp.addAttribute("message", "Se esta produciendo un cambio actualmente, pruebe de nuevo");
				return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
			}

			if (version2 == null) {
				version2 = 0;
			}

			cliente.setVersion(version2 + 1);

			Set<Authorities> authorities = clienteAntiguo.getUser().getAuthorities();

			for (Authorities a : authorities) {

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
	public ModelAndView showCliente(@PathVariable("clienteId") final int clienteId) {
		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}

	//Redirigir a detalles de cliente sin conocer su id
	@GetMapping("/clientes/miPerfil")
	public String showCliente(final ModelMap mp) {
		String vista;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User currUser = this.userService.findUser(auth.getName()).get();

		Cliente cliente = this.clienteService.findClienteByUserName(currUser.getUsername());

		if (cliente == null) {

			SecurityContextHolder.clearContext();
			vista = "/login";
			mp.addAttribute("message", "El usuario debe volver a loggear para actualizar los cambios");

		} else {

			mp.addAttribute("cliente", cliente);
			vista = "redirect:/clientes/" + cliente.getId();

		}

		return vista;
	}

	//Redirige al cliente a la página de sus reproductores
	@GetMapping("clientes/{clienteId}/reproductores")
	public ModelAndView showReproductoresCliente(@PathVariable("clienteId") final int clienteId) {
		ModelAndView mav = new ModelAndView("/reproductores/listadoReproductores");

		Cliente cliente = this.clienteService.findClienteById(clienteId);
		Collection<Reproductor> reproductores = cliente.getReproductores();

		mav.addObject("cliente", cliente);
		mav.addObject("reproductores", reproductores);

		return mav;
	}

	//Redirige al cliente a la página donde añadirá reproductores
	@GetMapping("clientes/{clienteId}/addReproductores")
	public ModelAndView ClienteAnyadeReproductores(@PathVariable("clienteId") final int clienteId) {
		ModelAndView mav = new ModelAndView("/reproductores/listadoReproductores");
		Cliente cliente = this.clienteService.findClienteById(clienteId);
		Collection<Reproductor> reproductoresCliente = cliente.getReproductores();
		Collection<Reproductor> reproductores = this.reproductorService.findAllReproductor();

		for (Reproductor r : reproductoresCliente) {

			if (reproductores.contains(r)) {

				reproductores.remove(r);

			}

		}

		mav.addObject("cliente", cliente);
		mav.addObject("reproductores", reproductores);
		mav.addObject("MostrarBoton", true);

		return mav;
	}

}
