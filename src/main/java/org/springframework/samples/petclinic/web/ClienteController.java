
package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.ehcache.shadow.org.terracotta.offheapstore.util.FindbugsSuppressWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
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

	
	@Autowired
	public ClienteController(ClienteService clienteService,  UserService userService, AuthoritiesService authoritiesService, ReproductorService reproductorService ) {
		this.clienteService = clienteService;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		this.reproductorService = reproductorService;
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
	public String postFormularioCreacion(@Valid Cliente cliente, @Valid User user, BindingResult result, ModelMap mp) {	
				
		System.out.println("================================================================>" + cliente);
		System.out.println("================================================================>" + user);
		
		
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
			System.out.println("===========================El cliente actualizado es:"+ this.clienteService.findClienteById(clienteId)+"==========================");
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente se ha actualizado satisfactoriamente");
			
//			if(clienteAntiguo.getUser() != cliente.getUser()) {
//				
//				return "redirect:/clientes/miPerfil";
//				
//			}
			
			
			return "redirect:/clientes/miPerfil";//"{clienteId}";
		}
	}

	//Eliminar Cliente
	@PostMapping("/clientes/{clienteId}/delete")
	public String eliminarCliente(@PathVariable("clienteId") int clienteId, ModelMap mp) {

		this.clienteService.deleteClienteById(clienteId);
		return "redirect:/cliente/{clienteId}"; //TODO donde redirigir? 
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
