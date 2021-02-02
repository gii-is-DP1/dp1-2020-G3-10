package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.PedidoService;
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

/**
 * @author Marta Díaz
 */

@Controller
public class VideojuegoController {

	@Autowired
	private final VideojuegoService videojuegoService;
	
	@Autowired
	private final PedidoService pedidoService;
	
	@Autowired
	private final VendedorService vendedorService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public VideojuegoController(VideojuegoService videojuegoService, PedidoService pedidoService, VendedorService vendedorService) {
		this.videojuegoService = videojuegoService;
		this.pedidoService = pedidoService;
		this.vendedorService = vendedorService;
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
		Videojuego v = this.videojuegoService.findVideojuegoById(videojuegoId);
		model.put("videojuego", v);
		return "/videojuegos/videojuegoDetails";
	}

	@GetMapping(value = "/videojuegos/new")
	public String createVideojuego(final ModelMap modelmap) {
		String view = "/videojuegos/formCreateVideojuegos";
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
			this.vendedorService.save(vendedor);
			return showProductos(model);
		}
	}

	@GetMapping(value = "/videojuegos/delete/{videojuegoId}")
	public String deleteVideojuego(@PathVariable("videojuegoId") int videojuegoId, final ModelMap modelmap) {
		Videojuego videojuegoBorrar = this.videojuegoService.findVideojuegoById(videojuegoId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Videojuego> videojuegos = vendedor.getVideojuegos();

		
		if ((videojuegoBorrar != null) && (videojuegos.contains(videojuegoBorrar))) {
			videojuegos.remove(videojuegoBorrar);
			this.vendedorService.save(vendedor);
			modelmap.addAttribute("message", "¡Producto eliminado!");
			this.videojuegoService.delete(videojuegoBorrar);
			return showProductos(modelmap);
		} else {
			modelmap.addAttribute("message", "ERROR!");
			return "/videojuegos/videojuegosList";
		}
		
	}

	@GetMapping(value = "/videojuegos/edit/{videojuegoId}")
	public String initUpdateForm(@PathVariable("videojuegoId") int videojuegoId, ModelMap model) {
		Videojuego v = this.videojuegoService.findVideojuegoById(videojuegoId);
		model.put("videojuego", v);
		String view = "/videojuegos/formCreateVideojuegos";
		return view;
	}

	@PostMapping(value = "/videojuegos/edit/{videojuegoId}")
	public String processUpdatePeliculaForm(@Valid final Videojuego v, BindingResult result,
			@PathVariable("videojuegoId") int videojuegoId) {
		String view = "/videojuegos/formCreateVideojuegos";
		if (result.hasErrors()) {
			return view;
		} else {
			v.setId(videojuegoId);
			this.videojuegoService.saveVideojuego(v);
			view = "redirect:/videojuegos/" + v.getId();
			return view;
		}
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
