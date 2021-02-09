package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Reproductor;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ReproductorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reproductores")
public class ReproductorController {

	private final ReproductorService ReproductorService;
	private final ClienteService clienteService;
	private ClienteController clienteController;

	public ReproductorController(ReproductorService ReproductorService, AuthoritiesService authoritiesService, ClienteService clienteService, ClienteController clienteController) {
		this.ReproductorService = ReproductorService;
		this.clienteService = clienteService;
		this.clienteController = clienteController;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//MostrarReproductors
	@GetMapping()
	public String showReproductores(ModelMap mp) {
		String vista = "reproductores/listadoReproductores";
		Iterable<Reproductor> Reproductors = ReproductorService.findAllReproductor();
		mp.addAttribute("reproductores",Reproductors);
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
			mp.addAttribute("message","El Reproductor no se ha podido crear");
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

	//Cliente Añade Reproductor
	
	@GetMapping(value = "/add/{ReproductorId}")
	public ModelAndView addClienteReproductor(@PathVariable("ReproductorId") int reproductorId) {
		
		//Obtengo el cliente loggeado que quiere añadir el reproductor
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Cliente cliente= this.clienteService.findClienteByUserName(username);
		
		//Creo la llamada al controlador de cliente (suena feo pero es la única forma limpia de hacerlo que he encontrado)
		ModelAndView mav =  this.clienteController.showReproductoresCliente(cliente.getId());

		//Obtengo el reproductor deseado y la lista de reproductores que ya tiene mi cliente
		Optional<Reproductor> reproductor = ReproductorService.findReproductorById(reproductorId);
		Collection<Reproductor> reproductoresCliente = cliente.getReproductores();
		
		if (reproductor.isPresent() && !reproductoresCliente.contains(reproductor.get())) {

		//Actualizo los reproductores del cliente y actualizo el propio cliente
		reproductoresCliente.add(reproductor.get());
		cliente.setReproductores(reproductoresCliente);
		this.clienteService.saveCliente(cliente);
		
		//Actualizo los clientes que disponen de dicho reproductor
		Collection<Cliente> clientesReproductor = reproductor.get().getClientes();
		clientesReproductor.add(cliente);
		reproductor.get().setClientes(clientesReproductor);
		ReproductorService.saveReproductor(reproductor.get());
		
		mav.addObject("message", "Reproductor añadido con éxito!!");
			
		} else {

			mav.addObject("message","Error: El reproductor no se ha podido añadir!!");
		
		}
		
		return mav;
	}
	
	//Cliente Elimina Reproductor
	
		@GetMapping(value = "/remove/{ReproductorId}")
		public ModelAndView removeClienteReproductor(@PathVariable("ReproductorId") int reproductorId) {
			
			//Obtengo el cliente loggeado que quiere eliminar el reproductor
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = ((UserDetails)principal).getUsername();
			Cliente cliente= this.clienteService.findClienteByUserName(username);
			
			//Creo la llamada al controlador de cliente (suena feo pero es la única forma limpia de hacerlo que he encontrado)
			ModelAndView mav =  this.clienteController.showReproductoresCliente(cliente.getId());

			//Obtengo el reproductor deseado y la lista de reproductores que ya tiene mi cliente
			Optional<Reproductor> reproductor = ReproductorService.findReproductorById(reproductorId);
			Collection<Reproductor> reproductoresCliente = cliente.getReproductores();
			
			if (reproductor.isPresent() && reproductoresCliente.contains(reproductor.get())) {

				//Actualizo los reproductores del cliente y actualizo el propio cliente
				reproductoresCliente.remove(reproductor.get());
				cliente.setReproductores(reproductoresCliente);
				this.clienteService.saveCliente(cliente);
				
				//Actualizo los clientes que disponen de dicho reproductor
				Collection<Cliente> clientesReproductor = reproductor.get().getClientes();
				clientesReproductor.remove(cliente);
				reproductor.get().setClientes(clientesReproductor);
				ReproductorService.saveReproductor(reproductor.get());
				
				mav.addObject("message", "Reproductor eliminado con éxito!!");
					
				} else {

				mav.addObject("message","Error: El reproductor no se ha podido eliminar!!");
			
			}
			
			return mav;
		}
}
