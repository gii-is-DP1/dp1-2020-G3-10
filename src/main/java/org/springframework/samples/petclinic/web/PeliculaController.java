package org.springframework.samples.petclinic.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pelicula;

import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

@Controller
public class PeliculaController {
	
	@Autowired
	private final PeliculaService peliculaService;
	
	@Autowired
	private final ProductoService productoService;
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	

	
	@Autowired
	public PeliculaController(final PeliculaService peliculaService,final ProductoService productoService) {
		this.peliculaService = peliculaService;
		this.productoService = productoService;
		
	}
	
	//Muestra todas las peliculas
	@GetMapping(value = "/peliculas")
	public String showPeliculasList(Map<String, Object> model) {
		List<Pelicula> peliculas = this.peliculaService.findPeliculas();
		model.put("peliculas", peliculas);
		return "/peliculas/PeliculasList";
		
	}
	
	
	@GetMapping(value = "/peliculas/edit/{peliculaId}")
	public String initUpdateForm(@PathVariable("peliculaId") int peliculaId, ModelMap model){
		Pelicula p= this.peliculaService.findPeliculaById(peliculaId);
		model.put("pelicula", p);
		String view= "/peliculas/formCreatePeliculas";
		return view;
	}
	
	@PostMapping(value ="/peliculas/edit/{peliculaId}")
	public String processUpdatePeliculaForm(@Valid final Pelicula p, BindingResult result,
			@PathVariable("peliculaId") int peliculaId) {
		
		String view = "/peliculas/formCreatePeliculas";
		if(result.hasErrors()) {
			return view;
		}else {
			p.setId(peliculaId);
			this.peliculaService.savePelicula(p);
			view ="redirect:/peliculas/" + p.getId() ;
			return view;
		}
		
	}
	
	//Muestra en la vista peliculaDetails los detalles de una pelicula pasando su id
	@GetMapping("/peliculas/{peliculaId}")
	public String showPelicula(@PathVariable("peliculaId") int peliculaId, Map<String, Object> model) {
		Pelicula pelicula = this.peliculaService.findPeliculaById(peliculaId);
		model.put("pelicula", pelicula);
		return "/peliculas/peliculaDetails";
	}

	//Muestra la vista para crear una pelicula
	@GetMapping(value= "/peliculas/new")
	public String createPelicula(final ModelMap modelmap) {
		String view = "/peliculas/formCreatePeliculas";
		modelmap.addAttribute("pelicula", new Pelicula());
		return view;
	}
	
	//Se traen los datos de la pelicula nueva del form y se procede al post 
	@PostMapping(value= "/peliculas/new")
	public String savePelicula(@Valid Pelicula pel, BindingResult result) {
		if(result.hasErrors()) {
			return "/peliculas/formCreatePeliculas";
		}else {
			this.peliculaService.savePelicula(pel);
			return "redirect:/peliculas/" + pel.getId();
		}
	}
	
	//borra una determinada pelicula
	@GetMapping("/peliculas/delete/{peliculaId}")
	public String deletePelicula(@PathVariable("peliculaId") int peliculaId, final ModelMap modelMap) {
		Pelicula peliculaBorrar = this.peliculaService.findPeliculaById(peliculaId);
		String view = "peliculas/PeliculasList";
		if (peliculaBorrar != null) {
			this.peliculaService.delete(peliculaBorrar);
			view = this.showPeliculasList(modelMap);
		}else {
			modelMap.addAttribute("message", "ERROR!");
		}
		
		return view;
		
	}
	
}
