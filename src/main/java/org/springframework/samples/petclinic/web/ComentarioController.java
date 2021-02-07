package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	@Autowired
	private final VideojuegoService videojuegoService;
	
	@Autowired 
	private final MerchandasingService merchandasingService;
	
	@Autowired
	private final UserService userService;
	
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P = "comentarios/createOrUpdateComentarioForm";
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V = "comentarios/createOrUpdateComentarioFormVideojuego";
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_M = "comentarios/createOrUpdateComentarioFormMerchandasing";
	
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
	public ComentarioController(ComentarioService comentarioService, ClienteService clienteService, PeliculaService peliculaService, VideojuegoService videojuegoService, MerchandasingService merchandasingService, UserService userService) {
		this.comentarioService = comentarioService;
		this.clienteService = clienteService;
		this.peliculaService = peliculaService;
		this.videojuegoService = videojuegoService;
		this.merchandasingService = merchandasingService;
		this.userService = userService;
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
	
	@InitBinder("videojuego")
	public void initVideojuegoFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("merchandasing")
	public void initMerchandasingFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/pelicula/{peliculaId}/new")
	public String initCreationFormComentarioPelicula(@PathVariable("peliculaId") int peliculaId, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		User usuario = this.userService.findUser(userDetails.getUsername()).get();
		
		
		Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());
		Comentario comentario = new Comentario(cliente);
		Pelicula pelicula = peliculaService.findPeliculaById(peliculaId);
		
		pelicula.addComment(comentario);
		cliente.addComment(comentario);
		
		model.put("comentario", comentario);
	 	    
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
	}
	
	@GetMapping(value = "/videojuego/{videojuegoId}/new")
	public String initCreationFormComentarioVideojuego(@PathVariable("videojuegoId") int videojuegoId, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		User usuario = this.userService.findUser(userDetails.getUsername()).get();
		
		
		Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());
		Comentario comentario = new Comentario(cliente);
		Videojuego videojuego = videojuegoService.findVideojuegoById(videojuegoId);
		
		videojuego.addComment(comentario);
		cliente.addComment(comentario);
		
		model.put("comentario", comentario);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V;
	}
	
	@GetMapping(value = "merchandasing/{merchandasingId}/new")
	public String initCreationFormComentarioMerchandasing(@PathVariable("merchandasingId") int merchandasingId, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		User usuario = this.userService.findUser(userDetails.getUsername()).get();
		
		
		Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());
		Comentario comentario = new Comentario(cliente);
		Merchandasing merchandasing = this.merchandasingService.findMerchandasingById(merchandasingId);
		
		merchandasing.addComment(comentario);
		cliente.addComment(comentario);
		
		model.put("comentario", comentario);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_M;
	}
	
	
	@PostMapping(value = "/pelicula/{peliculaId}/new")
	public String processCreationFormComentarioPelicula(@Valid Comentario comentario, BindingResult result, ModelMap model) {	
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
		}
		else {
			
						comentario.setCliente(clienteService.findClienteById(comentario.getCliente().getId()));
						comentario.setPelicula(peliculaService.findPeliculaById(comentario.getPelicula().getId()));
						
						Cliente cliente = comentario.getCliente();
						Collection<Comentario> comentariosCliente = cliente.getComentarios();
						comentariosCliente.add(comentario);
						cliente.setComentarios(comentariosCliente);
						
						Pelicula pelicula = comentario.getPelicula();
						Collection<Comentario> comentariosPelicula = pelicula.getComentarios();
						comentariosPelicula.add(comentario);
						pelicula.setComentarios(comentariosPelicula);
						
                    	
                    	this.comentarioService.saveComment(comentario);
                    	this.clienteService.saveCliente(cliente);
                    	this.peliculaService.savePelicula(pelicula);
                    	Iterable<Comentario> comentarios = comentarioService.findAll();
                    	model.addAttribute("comentarios", comentarios);
                    	model.addAttribute("message", "Comentario creado con exito");
                    	
                    
                    return "redirect:/" ;
		}
	}
	
	@PostMapping(value = "/videojuego/{videojuegoId}/new")
	public String processCreationFormComentarioVideojuego(@Valid Comentario comentario, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V;
		} else {
			
				comentario.setCliente(clienteService.findClienteById(comentario.getCliente().getId()));
				comentario.setVideojuego(videojuegoService.findVideojuegoById(comentario.getVideojuego().getId()));
				
				Cliente cliente = comentario.getCliente();
				Collection<Comentario> comentariosCliente = cliente.getComentarios();
				comentariosCliente.add(comentario);
				cliente.setComentarios(comentariosCliente);
				
				Videojuego videojuego = comentario.getVideojuego();
				Collection<Comentario> comentariosVideojuego = videojuego.getComentarios();
				comentariosVideojuego.add(comentario);
				videojuego.setComentarios(comentariosVideojuego);
				
				this.comentarioService.saveComment(comentario);
				this.clienteService.saveCliente(cliente);
				this.videojuegoService.saveVideojuego(videojuego);
				Iterable<Comentario> comentarios = comentarioService.findAll();
            	model.addAttribute("comentarios", comentarios);
            	model.addAttribute("message", "Comentario creado con exito");
            	
			return "redirect:/comentarios/" + cliente.getId();
		}
	}
	
	@PostMapping(value = "/merchandasing/{merchandasingId}/new")
	public String processCreationFormComentarioMerchandasing(@Valid Comentario comentario , BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_M;
		} else {
			comentario.setCliente(clienteService.findClienteById(comentario.getCliente().getId()));
			comentario.setMerchandasing(merchandasingService.findMerchandasingById(comentario.getMerchandasing().getId()));
			
			Cliente cliente = comentario.getCliente();
			Collection<Comentario> comentariosCliente = cliente.getComentarios();
			comentariosCliente.add(comentario);
			cliente.setComentarios(comentariosCliente);
			
			Merchandasing merchandasing = comentario.getMerchandasing();
			Collection<Comentario> comentariosMerchandasing = merchandasing.getComentarios();
			comentariosMerchandasing.add(comentario);
			merchandasing.setComentarios(comentariosMerchandasing);
			
			this.comentarioService.saveComment(comentario);
			this.clienteService.saveCliente(cliente);
			this.merchandasingService.saveMerchandasing(merchandasing);
			Iterable<Comentario> comentarios = comentarioService.findAll();
			model.addAttribute("comentarios", comentarios);
			model.addAttribute("message", "Comentario creado con exito");
			
			return "redirect:/comentarios/" + cliente.getId();
		}
	}
	
	@GetMapping(value = "/comentario/{comentarioId}/edit")
	public String initUpdateFormComentario(@PathVariable("comentarioId") int comentarioId, ModelMap model) {
		String res = "";
		Comentario comentario = comentarioService.findCommentById(comentarioId);
		model.put("comentario", comentario);
		if(comentario.getPelicula()!=null) {
			res = VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
		} else if(comentario.getVideojuego()!=null) {
			res = VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V;
		} else {
			res = VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_M;
		}
		return res;
	}
	
	@PostMapping(value = "/comentario/{comentarioId}/edit")
	public String processUpdateFormComentario(@Valid Comentario comentario, BindingResult result, Cliente cliente,@PathVariable("comentarioId") int comentarioId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("comentario", comentario);
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
		}
		else {
                        Comentario comentarioToUpdate=this.comentarioService.findCommentById(comentarioId);
                        BeanUtils.copyProperties(comentario, comentarioToUpdate, "id","cliente","pelicula", "videojuego", "merchandasing");                                                                                                     
                        this.comentarioService.saveComment(comentarioToUpdate);
                    
                        return "redirect:/comentarios/" + cliente.getId();
		}
	}
	
	@GetMapping("comentario/{comentarioId}/delete")
	public String deleteComentario(@PathVariable("comentarioId") int comentarioId, ModelMap model) {
		Comentario comentario = comentarioService.findCommentById(comentarioId);
		String view = "comentarios/comentariosList";
		if(comentario!=null) {
			comentarioService.deleteComment(comentario);
		} else {
			model.addAttribute("message", "ERROR!");
		}
		return view;
	}
	
	
	
	
	
	}