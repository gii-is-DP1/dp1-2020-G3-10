package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ReproductorService;
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
@RequestMapping("/reproductores")
public class ReproductorController {

	private final ReproductorService ReproductorService;
	private final ClienteService clienteService;


	public ReproductorController(ReproductorService ReproductorService, AuthoritiesService authoritiesService, ClienteService clienteService) {
		this.ReproductorService = ReproductorService;
		this.clienteService = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//MostrarReproductors
	@GetMapping()
	public String showReproductors(ModelMap mp) {
		String vista = "reproductores/listadoReproductores";
		Iterable<Reproductor> Reproductors = ReproductorService.findAllReproductor();
		mp.addAttribute("Reproductores",Reproductors);
		return vista;
	}
	
	//Crear Reproductors
	//GET
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap mp) {
		String view = "reproductores/editReproductor";
		mp.addAttribute("reproductor", new Reproductor());
		return view;
	}

	//POST
	@PostMapping(value = "/save")
	public String processCreationForm(@Valid Reproductor Reproductor, BindingResult result,ModelMap mp) {
		String view = "reproductores/listadoReproductores" ;
		if (result.hasErrors()) {
			mp.addAttribute("Reproductor",Reproductor);
			mp.addAttribute("message","La Reproductor no se ha podido crear");
			return "reproductores/editReproductor";
		} else {
			this.ReproductorService.saveReproductor(Reproductor);
			Iterable<Reproductor>Reproductors = ReproductorService.findAllReproductor();
			mp.addAttribute("reproductores",Reproductors);
			mp.addAttribute("message", "Reproductor successfully saved!!");
			return view;
		}
	}

	//EliminarReproductor
	@GetMapping(value = "/delete/{ReproductorId}")
	public String deleteReproductor(@PathVariable("ReproductorId") int ReproductorId, ModelMap modelMap) {

		String view = "reproductores/listadoReproductores";
		
		Optional<Reproductor> Reproductor = ReproductorService.findReproductorById(ReproductorId);

		if (Reproductor.isPresent()) {
			ReproductorService.delete(ReproductorId);
			Iterable<Reproductor>Reproductors = ReproductorService.findAllReproductor();
			modelMap.addAttribute("reproductores",Reproductors);
			modelMap.addAttribute("message", "Reproductor successfully deleted!!");
		} else {
			modelMap.addAttribute("message", "Reproductor no encontrada");
		}
		return view;
	}
	
//	/* Redirigimos a una vista en la que haya un formulario con todas las Reproductors a modo seleccionar puntos
//	y quiero que si el cliente ya tiene dicha Reproductor ese punto venga marcado */
//
//	//Vamos a la vista que lista las Reproductors indicando que cliente ha mandado la petición
//	@GetMapping("/{clienteId}/updateReproductors")
//	public String iniUpdateReproductor(@PathVariable("clienteId") int clienteId,ModelMap mp) {
//		
//		String view = "/Reproductors/updateReproductors";
//		mp.addAttribute("ReproductorsCliente",this.clienteService.findClienteById(clienteId).getReproductors());
//		mp.addAttribute("Reproductors", this.ReproductorService.findAllReproductor());
//		return view;
//	}
//	
//	//Procesamos el formulario que me devuelve las Reproductors escogidas por el cliente
//	@PostMapping("/Reproductors/save")
//	public String processUpdateReproductor(@Valid Set<Reproductor> Reproductors, @Valid int clienteId, BindingResult result, ModelMap modelMap ) {
//		if (result.hasErrors()) {
//			modelMap.addAttribute("Reproductors",Reproductors);
//			return "Reproductor/updateReproductors";
//		}
//		else {
//			Cliente cliente = this.clienteService.findClienteById(clienteId);
//			cliente.setReproductors(Reproductors);
//			this.clienteService.saveCliente(cliente);
//			return "redirect:/cliente/{clienteId}";
//		}
//	}
//	
//	

}
