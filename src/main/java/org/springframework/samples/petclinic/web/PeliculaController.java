package org.springframework.samples.petclinic.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pelicula;

import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

@Controller
@RequestMapping(path = "/peliculas")
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
	
	@GetMapping(path = {"/mostrar", "/"})
	public String showPeliculasList(Map<String, Object> model) {
		
		List<Pelicula> peliculas = this.peliculaService.findPeliculas();
		model.put("peliculas", peliculas);
		return "/peliculas/PeliculasList";
	}
	
	@GetMapping(value = "/peliculas/{peliculaId}/edit")
	public String initUpdateForm(@PathVariable("peliculaId") int peliculaId, ModelMap model){
		Pelicula p= this.peliculaService.findPeliculaById(peliculaId);
		model.put("pelicula", p);
		return "/peliculas/createOrUpdatePeliculaForm";
	}
	
	@PostMapping(value ="/peliculas/{peliculaId}/edit")
	public String processUpdatePeliculaForm(@Valid Pelicula p, BindingResult result,
			@PathVariable("peliculaId") int peliculaId) {
		if(result.hasErrors()) {
			return "/peliculas/createOrUpdatePeliculaForm";
			
		}else {
			Pelicula pParaActualizar = this.peliculaService.findPeliculaById(peliculaId);
			try {
				this.peliculaService.savePelicula(pParaActualizar);
			}catch (Exception e) {
				return "/peliculas/createOrUpdatePeliculaForm";
			}
			
			return "redirect:/peliculas/{peliculaId}";
		}
	}

}
