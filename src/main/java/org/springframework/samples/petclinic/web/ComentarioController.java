package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentarios/{clienteId}")
public class ComentarioController {
	
	@Autowired
	private final ComentarioService comentarioService;

	@Autowired
	private final ClienteService clienteService;
	
	@Autowired
	private final PeliculaService peliculaService;
	
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM = "comentarios/createOrUpdateComentarioForm";
	
	@GetMapping()
	public String listComments(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
		String vista = "comentarios/comentariosList";
		List<Comentario> comentarios = comentarioService.findByClientId(clienteId);
		modelMap.addAttribute("comentarios", comentarios);
		if(comentarios.isEmpty()) {
			System.out.println("No hay nada");
		} else {
			System.out.println(comentarios.size());
			System.out.println(comentarios.get(0).getCliente().getNombre());
		}
		return vista;
	}
	
	@Autowired
	public ComentarioController(ComentarioService comentarioService, ClienteService clienteService, PeliculaService peliculaService) {
		this.comentarioService = comentarioService;
		this.clienteService = clienteService;
		this.peliculaService = peliculaService;
	}
	
	@ModelAttribute("cliente")
	public Cliente findCliente(@PathVariable("clienteId") int clienteId) {
		return this.clienteService.findClienteById(clienteId);
	}
	
	@InitBinder("cliente")
	public void initClienteFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("pelicula")
	public void initPeliculaFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping(value = "/pelicula/{peliculaId}/new")
	public String initCreationForm(@PathVariable("peliculaId") int peliculaId, Cliente cliente, ModelMap model) {
		Comentario comentario = new Comentario();
		Pelicula pelicula = peliculaService.findPeliculaById(peliculaId);
		comentario.setPelicula(pelicula);
		comentario.setCliente(cliente);
		pelicula.addComment(comentario);
		cliente.addComment(comentario);
		System.out.println(pelicula.getNombre());
		System.out.println(cliente.getNombre());
		model.put("comentario", comentario);
	   // model.put("cliente", cliente);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
	}
	
	
	@PostMapping(value = "/pelicula/{peliculaId}/new")
	public String processCreationForm(Pelicula pelicula, Cliente cliente, @Valid Comentario comentario, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
		}
		else {
						comentario.setCliente(cliente);
						this.comentarioService.saveComment(comentario);
                    	cliente.addComment(comentario);
                    	pelicula.addComment(comentario);
                    	Iterable<Comentario> comentarios = comentarioService.findAll();
                    	model.addAttribute("comentarios", comentarios);
                    	model.addAttribute("message", "Comentario creado con exito");
                    
                    return "redirect:/comentarios/" + cliente.getId();
		}
	}
	/*
	@GetMapping(value = "/comentarios/{comentarioId}/edit")
	public String initUpdateForm(@PathVariable("comentarioId") int comentarioId, ModelMap model) {
		Comentario comentario = this.comentarioService.findCommentById(comentarioId);
		model.put("comentario", comentario);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/comentarios/{comentarioId}/edit")
	public String processUpdateForm(@Valid Comentario comentario, BindingResult result, Cliente cliente,@PathVariable("comentarioId") int comentarioId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("comentario", comentario);
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
		}
		else {
			Comentario comentarioToUpdate = this.comentarioService.findCommentById(comentarioId);
			BeanUtils.copyProperties(comentario, comentarioToUpdate, "id","cliente","vendedor");                                                                                              
            this.comentarioService.saveComment(comentarioToUpdate);                    
                    
			return "redirect:/comentarios/{comentarioId}";
		}
	}
	
	
	
	*/
	
	
	
	
	
	
	
	}
