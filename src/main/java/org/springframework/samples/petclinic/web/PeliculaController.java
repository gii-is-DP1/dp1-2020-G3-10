package org.springframework.samples.petclinic.web;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pelicula;

import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
		return "/peliculas/PeliculasList";
	}
	

}
