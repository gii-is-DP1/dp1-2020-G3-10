
package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Set;

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

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "cliente/createOrUpdateClienteForm";

	@Autowired
	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService, UserService userService, AuthoritiesService authoritiesService, PlataformaService plataformaService) {
		this.clienteService = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/cliente/new")
	public String initCreationForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		cliente.setCartera(0.0);
		cliente.setAdmin(false);
		model.put("cliente", cliente);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/cliente/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.clienteService.saveCliente(cliente);
			
			return "redirect:/cliente/" + cliente.getId();
		}
	}

	@GetMapping("/cliente/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("cliente/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}
	

}
