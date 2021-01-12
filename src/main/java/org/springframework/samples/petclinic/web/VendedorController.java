
package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	private static final String	VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM	= "vendedores/editVendedor";

	@Autowired
	private VendedorService		vendedorService;

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
		String vista = "vendedores/listadoVendedores";

		System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    save ");
		if (result.hasErrors()) {
			modelMap.addAttribute("vendedor", vendedor);
			return "vendedores/nuevoVendedor";
		} else {
			this.vendedorService.save(vendedor);
			modelMap.addAttribute("message", "Vendedor guardo correctamente");
			vista = this.listadoVendedor(modelMap);
		}

		return vista;

	}
	@GetMapping(path = "/delete/{vendedorId}")
	public String borrarVendedor(@PathVariable("vendedorId") final int vendedorId, final ModelMap modelMap) {

		String vista = "vendedores/listadoVendedores";

		Optional<Vendedor> vendedor = this.vendedorService.findVendedorById(vendedorId);

		if (vendedor.isPresent()) {

			this.vendedorService.delete(vendedor.get());
			modelMap.addAttribute("message", "Se ha borrado su vendedor");
			vista = this.listadoVendedor(modelMap);
		} else {

			modelMap.addAttribute("message", "No se ha encontrado su vendedor");
			vista = this.listadoVendedor(modelMap);

		}

		return vista;
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

	//Formulario Editar Vendedor

	@GetMapping(value = "/{vendedorId}/edit")
	public String initUpdateOwnerForm(@PathVariable("vendedorId") final int vendedorId, final Model model) {

		Vendedor vendedor = this.vendedorService.findVendedorByIdNormal(vendedorId);
		model.addAttribute(vendedor);
		return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/{vendedorId}/edit")
	public String processUpdateVendedorForm(@Valid final Vendedor vendedor, final BindingResult result, @PathVariable("vendedorId") final int vendedorId, final ModelMap mp) {

		if (result.hasErrors()) {

			mp.addAttribute("vendedor", vendedor);
			mp.addAttribute("message", "El vendedor no se ha podido actualizar correctamente");
			return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
		} else {

			vendedor.setId(vendedorId);

			this.vendedorService.save(vendedor);

			return "redirect:/vendedores/{vendedorId}";
		}
	}

	//Redirigir a detalles de vendedor sin conocer su id
	@GetMapping("/miPerfil")
	public String showVendedor(final ModelMap mp) {

		String vista;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("nombreeeeeeee " + auth.getName());

		String user = auth.getName();                        //pillamos el nombre del user por el oauth

		Vendedor vendedor = this.vendedorService.findVendedorByUserName(user);  //pillamos el vendedor

		if (vendedor == null) {

			SecurityContextHolder.clearContext();
			vista = "redirect:/login";
			mp.addAttribute("message", "El usuario debe volver a loggear para actualizar los cambios");

		} else {

			mp.addAttribute("vendedor", vendedor);

			System.out.println("AUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII" + vendedor.getId());

			vista = "redirect:/vendedores/" + vendedor.getId();

		}

		return vista;
	}

}
