package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Marta Díaz
 */
@Slf4j
@Controller
public class VideojuegoController {

	@Autowired
	private final VideojuegoService videojuegoService;
	
	@Autowired
	private final PedidoService pedidoService;
	
	@Autowired
	private final VendedorService vendedorService;
	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final ClienteService clienteService;
	
	@Autowired
	private final ComentarioService comentarioService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public VideojuegoController(VideojuegoService videojuegoService, PedidoService pedidoService, VendedorService vendedorService, UserService userService, ClienteService clienteService, ComentarioService comentarioService) {
		this.videojuegoService = videojuegoService;
		this.pedidoService = pedidoService;
		this.vendedorService = vendedorService;
		this.userService = userService;
		this.clienteService = clienteService;
		this.comentarioService = comentarioService;
		
	}

	@GetMapping(value = "/videojuegos")
	public String showVideojuegosList(Map<String, Object> model) {
		List<Videojuego> videojuegos = this.videojuegoService.findVideojuegos();
		List<Integer> idProhibidos = this.pedidoService.listaIdVideojuegosComprados();
		List<Videojuego> videojuegosPermitidos= new ArrayList<Videojuego>();
		for(Videojuego v: videojuegos) {
			if(!idProhibidos.contains(v.getId())) {
				videojuegosPermitidos.add(v);
				
			}
		}
		model.put("videojuegos", videojuegosPermitidos);
		return "/videojuegos/videojuegosList";
	}

	@GetMapping(value = "/videojuegos/{videojuegoId}")
	public String showVideojuego(@PathVariable("videojuegoId") int videojuegoId, Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal() == "anonymousUser") {
			Videojuego videojuego = this.videojuegoService.findVideojuegoById(videojuegoId);
			List<Comentario> comentarios = comentarioService.findComentariosByVideojuegoId(videojuego.getId());
			model.put("comentarios", comentarios);
			model.put("videojuego", videojuego);
		} else {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			User usuario = this.userService.findUser(userDetails.getUsername()).get();
			Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());
			
			Videojuego videojuego = this.videojuegoService.findVideojuegoById(videojuegoId);
			List<Comentario> comentarios = comentarioService.findComentariosByVideojuegoId(videojuego.getId());
			model.put("videojuego", videojuego);
			model.put("cliente", cliente);
			model.put("comentarios", comentarios);
			
		}
		return "/videojuegos/videojuegoDetails";
	}

	@GetMapping(value = "/videojuegos/new")
	public String createVideojuego(final ModelMap modelmap) {
		String view = "/videojuegos/formCreateVideojuegos";
		modelmap.addAttribute("plataformas", this.videojuegoService.getPlataformas());
		modelmap.addAttribute("videojuego", new Videojuego());
		return view;
	}

	@PostMapping(value = "/videojuegos/new")
	public String saveVideojuego(@Valid Videojuego vid, BindingResult result, ModelMap model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		
		if (result.hasErrors()) {
			model.addAttribute("plataformas", this.videojuegoService.getPlataformas());
			return "videojuegos/formCreateVideojuegos";
		} else {
			this.videojuegoService.saveVideojuego(vid);
			if (vendedor.getVideojuegos() == null) {
				Collection<Videojuego> videojuegos = new ArrayList<Videojuego>();
				videojuegos.add(vid);
				vendedor.getVideojuegos().addAll(videojuegos);
			} else {
				vendedor.getVideojuegos().add(vid);
			}
			log.info("¡Se ha creado el videojuego correctamente!");
			this.vendedorService.save(vendedor);
			return showProductos(model);
		}
	}

	@GetMapping(value = "/videojuegos/edit/{videojuegoId}")
	public String initUpdateForm(@PathVariable("videojuegoId") int videojuegoId, ModelMap model) {
		Videojuego videojuegoEditar = this.videojuegoService.findVideojuegoById(videojuegoId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Videojuego> videojuegos = vendedor.getVideojuegos();
		List<Integer> idVideojuegosPedidos= pedidoService.listaIdVideojuegosComprados();
		
		if(videojuegoEditar == null) {
			model.addAttribute("message", "El videojuego que se quiere editar no existe.");
			return showProductos(model);
		}else if(!videojuegos.contains(videojuegoEditar)) {
			model.addAttribute("message", "No tiene permisos para editar el videojuego.");
			return showProductos(model);
		}else if(idVideojuegosPedidos.contains(videojuegoId)) {
			model.addAttribute("message", "El videojuego no puede editarse porque esta en un pedido.");
			return showProductos(model);
		}else {
			model.addAttribute("plataformas", this.videojuegoService.getPlataformas());
			model.put("videojuego", videojuegoEditar);
			return "/videojuegos/formCreateVideojuegos";
			
		}
		
	}

	@PostMapping(value = "/videojuegos/edit/{videojuegoId}")
	public String processUpdatePeliculaForm(@Valid final Videojuego v, BindingResult result,
			@PathVariable("videojuegoId") int videojuegoId, ModelMap model) {
		String view = "/videojuegos/formCreateVideojuegos";
		if (result.hasErrors()) {
			model.addAttribute("plataformas", this.videojuegoService.getPlataformas());
			model.addAttribute("message", "¡No se pudo actualizar el videojuego!");
			return view;
		} else {
			v.setId(videojuegoId);
			this.videojuegoService.saveVideojuego(v);
			log.info("¡Se ha editado el videojuego correctamente!");
			view = "redirect:/videojuegos/" + v.getId();
			return view;
		}
	}
	
	@GetMapping(value = "/videojuegos/delete/{videojuegoId}")
	public String deleteVideojuego(@PathVariable("videojuegoId") int videojuegoId, final ModelMap modelMap) {
		
		Videojuego videojuegoBorrar = this.videojuegoService.findVideojuegoById(videojuegoId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Videojuego> videojuegos = vendedor.getVideojuegos();
		List<Integer> idVideojuegosPedidos = pedidoService.listaIdVideojuegosComprados();
		List<Comentario> comentarios = comentarioService.findComentariosByVideojuegoId(videojuegoId); 
		
		if (videojuegoBorrar == null) {
			modelMap.addAttribute("message", "El videojuego que se quiere borrar no existe.");
		}else if(!videojuegos.contains(videojuegoBorrar)) {
			modelMap.addAttribute("message", "No tiene permisos para eliminar el videojuego.");
		}else if(idVideojuegosPedidos.contains(videojuegoId)) {	
			modelMap.addAttribute("message", "El videojuego no puede borrarse porque esta en un pedido.");
		}else {
			if(!comentarios.isEmpty()) { 
				for(Comentario c: comentarios) { 
					comentarioService.deleteComment(c); 
				} 
			} 
			videojuegos.remove(videojuegoBorrar);
			this.vendedorService.save(vendedor);
			this.videojuegoService.delete(videojuegoBorrar);
			log.info("¡Se ha borrado el videojuego correctamente!");
			modelMap.addAttribute("message", "¡Producto eliminado!");
		}
		return showProductos(modelMap);
	}
	
	public String showProductos(ModelMap modelMap) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String usuario = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(usuario);
		
		
		if(this.vendedorService.obtenerPeliculas(vendedor.getId())!=null) {
			List<Pelicula> peliculas = new ArrayList<>();
			peliculas.addAll(this.vendedorService.obtenerPeliculas(vendedor.getId()));
			modelMap.addAttribute("peliculas", peliculas);
		}
		if(this.vendedorService.obtenerVideojuegos(vendedor.getId())!=null) {
			List<Videojuego> videojuegos = new ArrayList<>();
			videojuegos.addAll(this.vendedorService.obtenerVideojuegos(vendedor.getId()));
			modelMap.addAttribute("videojuegos", videojuegos);
		}
		if(this.vendedorService.obtenerMerchandasings(vendedor.getId())!=null) {
			List<Merchandasing> merch = new ArrayList<>();
			merch.addAll(this.vendedorService.obtenerMerchandasings(vendedor.getId()));
			modelMap.addAttribute("merch", merch);
		}
		
		return "/productos/productosVendedor";
	}
	
}
