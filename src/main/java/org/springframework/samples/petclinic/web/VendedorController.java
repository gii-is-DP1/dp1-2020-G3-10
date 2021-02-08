
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	private static final String	VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM	= "vendedores/editVendedor";

	@Autowired
	private VendedorService		vendedorService;

	@Autowired
	private UserService			userService;


	@GetMapping
	public String listadoVendedor(final ModelMap modelMap) {
		String vista = "vendedores/listadoVendedores";

		Iterable<Vendedor> vendedores = this.vendedorService.findAll();

		modelMap.addAttribute("vendedores", vendedores);

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearVendedor(final ModelMap modelMap) {

		String view = "vendedores/nuevoVendedor";

		modelMap.addAttribute("vendedor", new Vendedor());

		return view;

	}

	@PostMapping(path = "/save")
	public String salvarVendedor(@Valid final Vendedor vendedor, final BindingResult result, final ModelMap modelMap) {

		if (result.hasErrors()) {
			modelMap.addAttribute("vendedor", vendedor);

			System.out.print("Hay errores en el formulario (controlador de save vendedor) ");

			return "vendedores/nuevoVendedor";

		} else {
			this.vendedorService.save(vendedor);
			modelMap.addAttribute("message", "Vendedor guardo correctamente");
			return "redirect:/vendedores/" + vendedor.getId();
		}

	}
	@GetMapping(path = "/delete/{vendedorId}")
	public String borrarVendedor(@PathVariable("vendedorId") final int vendedorId, final ModelMap modelMap) {

		String vista = "/welcome";
		;

		Optional<Vendedor> vendedor = this.vendedorService.findVendedorById(vendedorId);

		if (vendedor.isPresent()) {

			this.vendedorService.delete(vendedor.get());
			SecurityContextHolder.clearContext();
			modelMap.addAttribute("message", "Se ha borrado su vendedor");
			return vista;
		} else {

			modelMap.addAttribute("message", "No se ha encontrado su vendedor");
			return vista;

		}

	}

	@GetMapping("/{vendedorId}")
	public String showVendedor(@PathVariable("vendedorId") final int vendedorId, final ModelMap modelMap) {

		String vista = "vendedores/vendedorDetails";

		Vendedor vendedor = this.vendedorService.findVendedorByIdNormal(vendedorId);

		modelMap.addAttribute("vendedor", vendedor);

		return vista;

		//----------------------------------OTRA FORMA------------------------------------

		//		ModelAndView mav = new ModelAndView("vendedores/vendedorDetails");
		//		mav.addObject(this.vendedorService.findVendedorById(vendedorId));
		//		return mav;
	}

	@GetMapping("/miPerfil")
	public String showPerfilVendedor(final ModelMap mp) {
		String vista;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User currUser = this.userService.findUser(auth.getName()).get();

		Vendedor vendedor = this.vendedorService.findVendedorByUsername(currUser.getUsername());

		if (vendedor == null) {

			SecurityContextHolder.clearContext();
			vista = "redirect:/login";
			mp.addAttribute("message", "El usuario debe volver a loggear para actualizar los cambios");

		} else {

			mp.addAttribute("vendedor", vendedor);
			vista = "redirect:/vendedores/" + vendedor.getId();

		}

		return vista;
	}

	//Formulario Editar Vendedor

	@GetMapping(value = "/{vendedorId}/edit")
	public String initUpdateOwnerForm(@PathVariable("vendedorId") final int vendedorId, final Model model) {

		Vendedor vendedor = this.vendedorService.findVendedorByIdNormal(vendedorId);
		model.addAttribute(vendedor);
		return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/{vendedorId}/edit")
	public String processUpdateVendedorForm(@Valid final Vendedor vendedor, final BindingResult result, @PathVariable("vendedorId") final int vendedorId, final ModelMap mp, @RequestParam(value = "version", required = false) final Integer version) {

		System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    edit ");

		if (result.hasErrors()) {

			mp.addAttribute("vendedor", vendedor);
			mp.addAttribute("message", "El vendedor no se ha podido actualizar correctamente");
			return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
		} else {

			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    2 ");

			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    version " + version);

			vendedor.setId(vendedorId);

			//Cosas de versiones

			Integer version2 = vendedor.getVersion();

			if (version2 != version) {
				mp.addAttribute("message", "Se esta produciendo un cambio actualmente, pruebe de nuevo");
				return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
			}

			if (version2 == null) {
				version2 = 0;
			}

			vendedor.setVersion(version2 + 1);

			//----------------------

			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    2,5 ");

			this.vendedorService.save(vendedor);

			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    3 ");

			return "redirect:/vendedores/{vendedorId}";
		}
	}

	@GetMapping(value = "/productos")
	public String listProducts(final ModelMap modelMap) {

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
