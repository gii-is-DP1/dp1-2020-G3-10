package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

/**
 * @author Marta Díaz
 */
@Slf4j
@Controller
public class PeliculaController {

	@Autowired
	private final PeliculaService peliculaService;

	@Autowired
	private final ProductoService productoService;

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
	public PeliculaController(final PeliculaService peliculaService, final ProductoService productoService,
			final PedidoService pedidoService, final VendedorService vendedorService, final UserService userService,
			final ClienteService clienteService, final ComentarioService comentarioService) {
		this.peliculaService = peliculaService;
		this.productoService = productoService;
		this.pedidoService = pedidoService;
		this.vendedorService = vendedorService;
		this.userService = userService;
		this.clienteService = clienteService;
		this.comentarioService = comentarioService;

	}

	@GetMapping(value = "/peliculas")
	public String showPeliculasList(Map<String, Object> model) {
		List<Pelicula> peliculas = this.peliculaService.findPeliculas();
		List<Integer> idProhibidos = this.pedidoService.listaIdPeliculasCompradas();
		List<Pelicula> peliculasPermitidas = new ArrayList<>();
		for (Pelicula pel : peliculas) {
			if (!idProhibidos.contains(pel.getId())) {
				peliculasPermitidas.add(pel);
			}
		}
		model.put("peliculas", peliculasPermitidas);
		return "/peliculas/PeliculasList";
	}

	@GetMapping("/peliculas/{peliculaId}")
	public String showPelicula(@PathVariable("peliculaId") int peliculaId, Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() == "anonymousUser") {
			Pelicula pelicula = this.peliculaService.findPeliculaById(peliculaId);
			List<Comentario> comentarios = comentarioService.findComentariosByPeliculaId(pelicula.getId());
			model.put("comentarios", comentarios);
			model.put("pelicula", pelicula);
		} else {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			User usuario = this.userService.findUser(userDetails.getUsername()).get();
			Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());

			Pelicula pelicula = this.peliculaService.findPeliculaById(peliculaId);
			List<Comentario> comentarios = comentarioService.findComentariosByPeliculaId(pelicula.getId());
			model.put("pelicula", pelicula);
			model.put("cliente", cliente);
			model.put("comentarios", comentarios);

		}
		return "/peliculas/peliculaDetails";
	}

	@GetMapping(value = "/peliculas/new")
	public String createPelicula(final ModelMap modelmap) {
		String view = "/peliculas/formCreatePeliculas";
		modelmap.addAttribute("formatos", this.peliculaService.getFormatos());
		modelmap.addAttribute("pelicula", new Pelicula());
		return view;
	}

	@PostMapping(value = "/peliculas/new")
	public String savePelicula(@Valid Pelicula pel, BindingResult result, ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);

		if (result.hasErrors()) {
			model.addAttribute("formatos", this.peliculaService.getFormatos());
			return "/peliculas/formCreatePeliculas";
		} else {
			this.peliculaService.savePelicula(pel);
			if (vendedor.getPeliculas() == null) {
				Collection<Pelicula> peliculas = new ArrayList<Pelicula>();
				peliculas.add(pel);
				vendedor.getPeliculas().addAll(peliculas);
			} else {
				vendedor.getPeliculas().add(pel);
			}
			this.vendedorService.save(vendedor);
			log.info("¡Se ha creado la pelicula correctamente!");
			return mostrarProductos(model);
		}
	}

	@GetMapping(value = "/peliculas/edit/{peliculaId}")
	public String initUpdateForm(@PathVariable("peliculaId") int peliculaId, ModelMap modelMap) {

		Pelicula peliculaEditar = this.peliculaService.findPeliculaById(peliculaId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Pelicula> peliculas = vendedor.getPeliculas();
		List<Integer> idPeliculasPedidos = pedidoService.listaIdPeliculasCompradas();

		if (peliculaEditar == null) {
			modelMap.addAttribute("message", "La pelicula que se quiere editar no existe.");
			return mostrarProductos(modelMap);
		} else if (!peliculas.contains(peliculaEditar)) {
			modelMap.addAttribute("message", "No tiene permisos para editar la pelicula.");
			return mostrarProductos(modelMap);
		} else if (idPeliculasPedidos.contains(peliculaId)) {
			modelMap.addAttribute("message", "La pelicula no puede editarse porque esta en un pedido.");
			return mostrarProductos(modelMap);
		} else {
			modelMap.addAttribute("formatos", this.peliculaService.getFormatos());
			modelMap.put("pelicula", peliculaEditar);
			return "/peliculas/formCreatePeliculas";
		}

	}

	@PostMapping(value = "/peliculas/edit/{peliculaId}")
	public String processUpdatePeliculaForm(@Valid final Pelicula p, BindingResult result,
			@PathVariable("peliculaId") int peliculaId, ModelMap model,
			@RequestParam(value = "version", required = false) Integer version) {

		String view = "/peliculas/formCreatePeliculas";
		if (p.getVersion() != version) {
			model.addAttribute("message", "¡No se pudo actualizar la pelicula!");
			return view;
		}

		if (result.hasErrors()) {
			model.addAttribute("formatos", this.peliculaService.getFormatos());
			model.addAttribute("message", "¡No se pudo actualizar la pelicula!");
		} else {
			p.setId(peliculaId);
			this.peliculaService.savePelicula(p);
			view = "redirect:/peliculas/" + p.getId();
			log.info("¡Se ha editado la pelicula correctamente!");

		}
		return view;
	}

	@GetMapping("/peliculas/delete/{peliculaId}")
	public String deletePelicula(@PathVariable("peliculaId") int peliculaId, final ModelMap modelMap) {

		Pelicula peliculaBorrar = this.peliculaService.findPeliculaById(peliculaId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Pelicula> peliculas = vendedor.getPeliculas();
		List<Integer> idPeliculasPedidos = pedidoService.listaIdPeliculasCompradas();
		List<Comentario> comentarios = comentarioService.findComentariosByPeliculaId(peliculaId);

		if (peliculaBorrar == null) {
			modelMap.addAttribute("message", "La pelicula que se quiere borrar no existe.");
		} else if (!peliculas.contains(peliculaBorrar)) {
			modelMap.addAttribute("message", "No tiene permisos para eliminar la pelicula.");
		} else if (idPeliculasPedidos.contains(peliculaId)) {
			modelMap.addAttribute("message", "La pelicula no puede borrarse porque esta en un pedido.");
		} else {
			if (!comentarios.isEmpty()) {
				for (Comentario c : comentarios) {
					comentarioService.deleteComment(c);
				}
				this.peliculaService.savePelicula(peliculaBorrar);
			}
			peliculas.remove(peliculaBorrar);
			this.vendedorService.save(vendedor);
			this.peliculaService.delete(peliculaBorrar);
			log.info("¡Se ha borrado la pelicula correctamente!");
			modelMap.addAttribute("message", "¡Producto eliminado!");
		}

		return mostrarProductos(modelMap);
	}

	public String mostrarProductos(ModelMap modelMap) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String usuario = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(usuario);

		if (this.vendedorService.obtenerPeliculas(vendedor.getId()) != null) {
			List<Pelicula> peliculas = new ArrayList<>();
			peliculas.addAll(this.vendedorService.obtenerPeliculas(vendedor.getId()));
			modelMap.addAttribute("peliculas", peliculas);
		}
		if (this.vendedorService.obtenerVideojuegos(vendedor.getId()) != null) {
			List<Videojuego> videojuegos = new ArrayList<>();
			videojuegos.addAll(this.vendedorService.obtenerVideojuegos(vendedor.getId()));
			modelMap.addAttribute("videojuegos", videojuegos);
		}
		if (this.vendedorService.obtenerMerchandasings(vendedor.getId()) != null) {
			List<Merchandasing> merch = new ArrayList<>();
			merch.addAll(this.vendedorService.obtenerMerchandasings(vendedor.getId()));
			modelMap.addAttribute("merch", merch);
		}

		return "/productos/productosVendedor";
	}

}
