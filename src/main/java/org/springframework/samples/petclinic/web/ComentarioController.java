package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P = "comentarios/createOrUpdateComentarioForm";
	
	private static final String VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V = "comentarios/createOrUpdateComentarioForm";
	
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
	public ComentarioController(ComentarioService comentarioService, ClienteService clienteService, PeliculaService peliculaService, VideojuegoService videojuegoService) {
		this.comentarioService = comentarioService;
		this.clienteService = clienteService;
		this.peliculaService = peliculaService;
		this.videojuegoService = videojuegoService;
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
	
	@InitBinder
	public void initMerchandasingFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	//Inicio el formulario de cliente crea comentario sobre una pelicula
	@GetMapping(value = "/pelicula/{peliculaId}/new")
	public String initCreationFormPelicula(@PathVariable("peliculaId") int peliculaId, Cliente cliente, ModelMap model) {
		
		Comentario comentario = new Comentario();
		Pelicula pelicula = peliculaService.findPeliculaById(peliculaId);
		comentario.setPelicula(pelicula);
		comentario.setCliente(cliente);
		
		System.out.println(cliente);
		System.out.println(pelicula);
		
		comentario.setTitulo("xxxx");
		comentario.setTexto("xxxxx");
		comentarioService.saveComment(comentario);

		pelicula.addComment(comentario);
		peliculaService.savePelicula(pelicula);
		cliente.addComment(comentario);
		clienteService.saveCliente(cliente);
		
		System.out.println(pelicula.getNombre());
		System.out.println(cliente.getNombre());

		model.put("comentario", comentario);
		
	    model.put("cliente", cliente);

		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
	}
	
	//Procesa el comentario creado por un cliente sobre una película
	@PostMapping(value = "/pelicula/{peliculaId}/new")
	public String processCreationForm(@PathVariable("peliculaId") int peliculaId, @Valid Comentario comentario, BindingResult result, ModelMap model) {	
		
		System.out.println("===================================== 0 ===================================================");
		
		//Obtengo el cliente loggeado que quiere añadir el comentario
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ((UserDetails)principal).getUsername();
//		Cliente cliente= this.clienteService.findClienteByUserName(username);
		
		Cliente cliente = this.clienteService.findClienteById(3);
		
		Pelicula pelicula = this.peliculaService.findPeliculaById(peliculaId);
		
		System.out.println(cliente);
		System.out.println(pelicula);
		System.out.println(comentario);
		
		System.out.println("===================================== 1 ===================================================");

		if (result.hasErrors()) {

			System.out.println(result.getAllErrors());
		
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			System.out.println("===================================== ERROR ===================================================");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_P;
			

		}
		else {
			//Se crea el comentario
			comentario.setCliente(cliente);
			comentario.setPelicula(pelicula);
			this.comentarioService.saveComment(comentario);
			
			System.out.println("===================================== Creo comentario ===================================================");

			
			//Se vincula el comentario al cliente que lo escribió
            cliente.addComment(comentario);
            clienteService.saveCliente(cliente);
            
    		System.out.println("===================================== Vinculo Cliente ===================================================");

            //Se vincula la pelicula con el comentario
            pelicula.addComment(comentario);
            peliculaService.savePelicula(pelicula);
            
    		System.out.println("===================================== Vinculo Pelicula ===================================================");

            //Saco todos los comentarios del cliente -- Saco todos los comentarios de la pelicula y redirijo a la pagina de la pelicula para comprobar que se vean los comentarios
            Iterable<Comentario> comentarios = comentarioService.findAll();
            model.addAttribute("comentarios", comentarios);
            model.addAttribute("message", "Comentario creado con exito");
         
    		System.out.println("===================================== PIJERIO ===================================================");

            return "redirect:/comentarios/" + cliente.getId();
		}
	}
	
	@GetMapping(value = "/videojuego/{videojuegoId}/new")
	public String initCreationFormVideojuego(@PathVariable("videojuegoId") int videojuegoId, Cliente cliente, ModelMap model) {
		Comentario comentario = new Comentario();
		Videojuego videojuego = videojuegoService.findVideojuegoById(videojuegoId);
		comentario.setVideojuego(videojuego);
		comentario.setCliente(cliente);
		videojuego.addComment(comentario);
		cliente.addComment(comentario);
		model.put("comentario", comentario);
		model.put("cliente", cliente);
		return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V;
	}
	
	
	
	
	@PostMapping(value = "/videojuego/{videojuegoId}/new")
	public String processCreationFormVideojuego(Videojuego videojuego, Cliente cliente, @Valid Comentario comentario, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("comentario", comentario);
			model.addAttribute("message", "El comentario no se ha podido crear");
			return VIEWS_COMENTARIOS_CREATE_OR_UPDATE_FORM_V;
		} else {
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
