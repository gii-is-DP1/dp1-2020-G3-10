package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.PlataformaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plataformas")
public class PlataformaController {

	private final PlataformaService plataformaService;
	private final ClienteService clienteService;


	public PlataformaController(PlataformaService plataformaService, AuthoritiesService authoritiesService, ClienteService clienteService) {
		this.plataformaService = plataformaService;
		this.clienteService = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//MostrarPlataformas
	@GetMapping()
	public String showPlataformas(ModelMap mp) {
		String vista = "plataformas/listadoPlataformas";
		Iterable<Plataforma> plataformas = plataformaService.findAllPlataforma();
		mp.addAttribute("plataformas",plataformas);
		return vista;
	}
	
	//Crear Plataformas
	//GET
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap mp) {
		String view = "plataformas/editPlataforma";
		mp.addAttribute("plataforma", new Plataforma());
		return view;
	}

	//POST
	@PostMapping(value = "/save")
	public String processCreationForm(@Valid Plataforma plataforma, BindingResult result,ModelMap mp) {
		String view = "/plataformas/listadoPlataformas" ;
		if (result.hasErrors()) {
			mp.addAttribute("plataforma",plataforma);
			return "/plataformas/editPlataforma";
		} else {
			this.plataformaService.savePlataforma(plataforma);
			Iterable<Plataforma>plataformas = plataformaService.findAllPlataforma();
			mp.addAttribute("plataformas",plataformas);
			mp.addAttribute("message", "Plataforma successfully saved!!");
			return view;
		}
	}

	//EliminarPlataforma
	@GetMapping(value = "/delete/{plataformaId}")
	public String deletePlataforma(@PathVariable("plataformaId") int plataformaId, ModelMap modelMap) {

		String view = "plataformas/listadoPlataformas";
		
		Optional<Plataforma> plataforma = plataformaService.findPlataformaById(plataformaId);

		if (plataforma.isPresent()) {
			plataformaService.delete(plataformaId);
			Iterable<Plataforma>plataformas = plataformaService.findAllPlataforma();
			modelMap.addAttribute("plataformas",plataformas);
			modelMap.addAttribute("message", "Plataforma successfully deleted!!");
		} else {
			modelMap.addAttribute("message", "Plataforma no encontrada");
		}
		return view;
	}
	
	/* Redirigimos a una vista en la que haya un formulario con todas las plataformas a modo seleccionar puntos
	y quiero que si el cliente ya tiene dicha plataforma ese punto venga marcado */

	//Vamos a la vista que lista las plataformas indicando que cliente ha mandado la petición
	@GetMapping("/{clienteId}/updatePlataformas")
	public String iniUpdatePlataforma(@PathVariable("clienteId") int clienteId,ModelMap mp) {
		
		String view = "/plataformas/updatePlataformas";
		mp.addAttribute("plataformasCliente",this.clienteService.findClienteById(clienteId).getPlataformas());
		mp.addAttribute("plataformas", this.plataformaService.findAllPlataforma());
		return view;
	}
	
	//Procesamos el formulario que me devuelve las plataformas escogidas por el cliente
	@PostMapping("/plataformas/save")
	public String processUpdatePlataforma(@Valid Set<Plataforma> plataformas, @Valid int clienteId, BindingResult result, ModelMap modelMap ) {
		if (result.hasErrors()) {
			modelMap.addAttribute("plataformas",plataformas);
			return "plataforma/updatePlataformas";
		}
		else {
			Cliente cliente = this.clienteService.findClienteById(clienteId);
			cliente.setPlataformas(plataformas);
			this.clienteService.saveCliente(cliente);
			return "redirect:/cliente/{clienteId}";
		}
	}
	
	

}
