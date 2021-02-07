package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
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

@Controller
public class MerchandasingController {

	@Autowired
	private final MerchandasingService merchandasingService;

	@Autowired
	private final PedidoService pedidoService;

	@Autowired
	private final VendedorService vendedorService;
	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final ClienteService clienteService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public MerchandasingController(final MerchandasingService merchandasingService, VendedorService vendedorService,
			PedidoService pedidoService, UserService userService, ClienteService clienteService) {
		this.merchandasingService = merchandasingService;
		this.vendedorService = vendedorService;
		this.pedidoService = pedidoService;
		this.userService = userService;
		this.clienteService = clienteService;

	}

	@GetMapping(value = "/merchandasings")
	public String showMerchandasingsList(Map<String, Object> model) {

		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		List<Integer> idProhibidos = this.pedidoService.listaIdMerchandasingsComprados();
		List<Merchandasing> merchPermitido = new ArrayList<>();
		for (Merchandasing m : merchandasings) {
			if (!idProhibidos.contains(m.getId())) {
				merchPermitido.add(m);
			}
		}
		model.put("merchandasings", merchPermitido);
		return "/merchandasings/MerchandasingsList";

	}

	@GetMapping("/merchandasings/{merchandasingId}")
	public String showMerchandasing(@PathVariable("merchandasingId") int merchandasingId, Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		User usuario = this.userService.findUser(userDetails.getUsername()).get();
		Cliente cliente = clienteService.findClienteByUserName(usuario.getUsername());
		Merchandasing merchandasing = this.merchandasingService.findMerchandasingById(merchandasingId);
		model.put("merchandasing", merchandasing);
		model.put("cliente", cliente);
		return "/merchandasings/merchandasingDetails";
	}

	@GetMapping(value = "/vendedor/merchandasings/new")
	public String initCreationMerchandasing(Map<String, Object> model) {
		model.put("merchandasing", new Merchandasing());
		model.put("tipos", this.merchandasingService.getTipos());
		return "/merchandasings/createOrUpdateMerchandasingForm";
	}

	@PostMapping(value = "/vendedor/merchandasings/new")
	public String initCreationMerchandasing(@Valid Merchandasing mer, BindingResult result, ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);

		if (result.hasErrors()) {
			model.put("tipos", this.merchandasingService.getTipos());
			return "/merchandasings/createOrUpdateMerchandasingForm";
		} else {
			this.merchandasingService.saveMerchandasing(mer);
			if (vendedor.getMerchandasings() == null) {
				Collection<Merchandasing> merchandisings = new ArrayList<Merchandasing>();
				merchandisings.add(mer);
				vendedor.getMerchandasings().addAll(merchandisings);
			} else {
				vendedor.getMerchandasings().add(mer);
			}
			this.vendedorService.save(vendedor);
			return mostrarProductos(model);
		}
	}

	@GetMapping(value = "/vendedor/merchandasings/{merchandasingId}/edit")
	public String editMerch(@PathVariable("merchandasingId") final int merchandasingId, final ModelMap modelMap) {

		Merchandasing merchandisingEditar = this.merchandasingService.findMerchandasingById(merchandasingId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Merchandasing> merchandisings = vendedor.getMerchandasings();
		List<Integer> idVideojuegosPedidos = pedidoService.listaIdMerchandasingsComprados();

		if (merchandisingEditar == null) {
			modelMap.addAttribute("message", "El merchandising que se quiere editar no existe.");
			return mostrarProductos(modelMap);
		} else if (!merchandisings.contains(merchandisingEditar)) {
			modelMap.addAttribute("message", "No tiene permisos para editar el merchandising.");
			return mostrarProductos(modelMap);
		} else if (idVideojuegosPedidos.contains(merchandisingEditar.getId())) {
			modelMap.addAttribute("message", "El merchandising no puede editarse porque esta en un pedido.");
			return mostrarProductos(modelMap);
		} else {
			modelMap.put("tipos", this.merchandasingService.getTipos());
			modelMap.put("merchandasing", merchandisingEditar);
			return "/merchandasings/createOrUpdateMerchandasingForm";
		}

	}

	@PostMapping(value = "/vendedor/merchandasings/{merchandasingId}/edit")
	public String processUpdateMerchandising(@Valid final Merchandasing merchandasing, final BindingResult result,
			@PathVariable("merchandasingId") final int merchandasingId, final ModelMap modelMap) {

		String view = "merchandasings/createOrUpdateMerchandasingForm";
		if (result.hasErrors()) {
			modelMap.put("tipos", this.merchandasingService.getTipos());
			modelMap.addAttribute("message", "¡No se pudo actualizar el merchandising!");
		} else {
			merchandasing.setId(merchandasingId);
			this.merchandasingService.saveMerchandasing(merchandasing);
			view = "redirect:/merchandasings/" + merchandasing.getId();
		}
		return view;
	}

	@GetMapping("/vendedor/merchandasings/{merchandasingId}/delete")
	public String deleteMerchandising(@PathVariable("merchandasingId") int merchandasingId, final ModelMap modelMap) {

		Merchandasing merchBorrar = this.merchandasingService.findMerchandasingById(merchandasingId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Merchandasing> merchandisings = vendedor.getMerchandasings();
		List<Integer> idMerchPedidos = this.pedidoService.listaIdMerchandasingsComprados();

		if (merchBorrar == null) {
			modelMap.addAttribute("message", "El merchandising que se quiere borrar no existe.");
		} else if (!merchandisings.contains(merchBorrar)) {
			modelMap.addAttribute("message", "No tiene permisos para eliminar el merchandising.");
		} else if (idMerchPedidos.contains(merchandasingId)) {
			modelMap.addAttribute("message", "El merchandising no puede borrarse porque esta en un pedido.");
		} else {
			merchandisings.remove(merchBorrar);
			this.vendedorService.save(vendedor);
			this.merchandasingService.delete(merchBorrar);
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
