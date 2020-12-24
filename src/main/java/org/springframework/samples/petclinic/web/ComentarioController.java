package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
	
	@Autowired
	private final ComentarioService comentarioService;

	@Autowired
	private final ClienteService clienteService;
	
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM = "comentarios/createOrUpdateComentarioForm";
	
	@GetMapping()
	public String listComments(ModelMap modelMap) {
		String vista = "comentarios/comentariosList";
		Iterable<Comentario> comentarios = comentarioService.findAll();
		modelMap.addAttribute("comentario", comentarios);
		return vista;
	}
	
	@Autowired
	public ComentarioController(ComentarioService comentarioService, ClienteService clienteService) {
		this.comentarioService = comentarioService;
		this.clienteService = clienteService;
	}
	
	@InitBinder("cliente")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	/*
	@GetMapping(value = "/comentarios/new")
	public String initCreationForm(Cliente cliente, ModelMap model) {
		Comentario comentario = new Comentario();
		cliente.addComment(comentario);
		model.put("comentario", comentario);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/comentarios/new")
	public String processCreationForm(Cliente cliente, @Valid Comentario comentario, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("comentario", comentario);
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    	cliente.addComment(comentario);;
                    	this.comentarioService.saveComment(comentario);
                    
                    return "redirect:/comentarios/{comentarioId}";
		}
	}
	
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
