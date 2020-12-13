
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.PlataformaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteController{

	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "cliente/createOrUpdateClienteForm";
	
	@Autowired
	private final ClienteService clienteService;
	
	private final PlataformaService plataformaService;
	
	@Autowired
	public ClienteController(ClienteService clienteService, UserService userService, AuthoritiesService authoritiesService, PlataformaService plataformaService) {
		this.clienteService = clienteService;
		this.plataformaService = new PlataformaService();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/cliente/new")
	public String initCreationForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/cliente/save")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result, ModelMap mp) {	
				
		if (result.hasErrors()) {
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido actualizar correctamente " + result);
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.clienteService.saveCliente(cliente);
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "Cliente creado satisfactoriamente");
			return "/cliente/" + cliente.getId();
		}
	}
	
	//Formulario Editar Cliente 
	@GetMapping(value = "/cliente/{clienteId}/edit")
	public String initEditForm(@PathVariable("clienteId") final int clienteId,ModelMap mp) {		
		Cliente cliente = clienteService.findClienteById(clienteId);
		mp.addAttribute("cliente", cliente);
		
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/cliente/{clienteId}/edit")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result,
			@PathVariable("clienteId") int clienteId, ModelMap mp) {
		
		if (result.hasErrors()) {
			
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente no se ha podido actualizar correctamente");
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			
			Cliente c = clienteService.findClienteById(clienteId);
			cliente.setId(c.getId());
			cliente.setUser(c.getUser());
			cliente.setVendedor(c.getVendedor());
			this.clienteService.saveCliente(cliente);
			mp.addAttribute("cliente", cliente);
			mp.addAttribute("message", "El cliente se ha actualizado satisfactoriamente");
			return "redirect:/cliente/{clienteId}";
		}
	}


	//Mostrar detalles de cliente
	@GetMapping("/cliente/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("cliente/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}
	
	//Operaciones entre cliente y plataforma
	
	//Método Get que nos redirigirá a la vista updatePlataformasCliente donde el cliente podrá seleccionar las plataformas que posee
		@GetMapping("cliente/{clienteId}/updatePlataformas")
		public String iniUpdateClientePlataformas(@PathVariable("clienteId") int clienteId,ModelMap mp) {

			System.out.println("=====================================" + clienteId + "===================================");
			
			String view = "/cliente/updatePlataformasCliente";
			
			Cliente cliente = clienteService.findClienteById(clienteId);
			
			Collection<Plataforma> plataformasCliente = null;
			try {
				
				plataformasCliente = cliente.getPlataformas();
				
			} catch (Exception e) {
				
				plataformasCliente = new ArrayList<Plataforma>();
				
			}
			
			mp.addAttribute("plataformasCliente",plataformasCliente);
			mp.addAttribute("plataformas", this.plataformaService.findAllPlataforma());
			return view;
		}
		
		//Método POST que recibirá las plataformas seleccionadas por el cliente y actualizará su listado de plataformas
		@PostMapping("cliente/{clienteId}/updatePlataformas")
		public String processUpdateClientePlataformasForm(@PathVariable("clienteId") int clienteId, @Valid Collection<Plataforma> plataformasCliente ,BindingResult result,ModelMap mp) {
			
			if (result.hasErrors()) {
				mp.addAttribute("plataformas",plataformasCliente);
				mp.addAttribute("message", "Ha habido un error al actualizar las plataformas");
				return "cliente/{clienteId}/updatePlataformas";
			}
			else {
				Cliente cliente = clienteService.findClienteById(clienteId);
				cliente.setPlataformas(plataformasCliente);
				this.clienteService.saveCliente(cliente);
				return "redirect:/cliente/{clienteId}";
			}
		}
	
}
