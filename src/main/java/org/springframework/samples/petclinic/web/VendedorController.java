
package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

	@Autowired
	private VendedorService vendedorService;


	@GetMapping
	public String listadoVendedor(final ModelMap modelMap) {
		String vista = "vendedores/listadoVendedores";

		Iterable<Vendedor> vendedores = this.vendedorService.findAll();

		modelMap.addAttribute("vendedores", vendedores);

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearVendedor(final ModelMap modelMap) {

		String view = "vendedores/editVendedor";

		modelMap.addAttribute("vendedor", new Vendedor());

		return view;

	}

	@PostMapping(path = "/save")
	public String salvarVendedor(@Valid final Vendedor vendedor, final BindingResult result, final ModelMap modelMap) {
		String vista = "vendedores/listadoVendedores";
		if (result.hasErrors()) {
			modelMap.addAttribute("vendedor", vendedor);
			return "vendedores/editVendedor";
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

		Vendedor vendedor = this.vendedorService.finVendedorByIdNormal(vendedorId);

		modelMap.addAttribute("vendedor", vendedor);

		return vista;

		//----------------------------------OTRA FORMA------------------------------------

		//		ModelAndView mav = new ModelAndView("vendedores/vendedorDetails");
		//		mav.addObject(this.vendedorService.findVendedorById(vendedorId));
		//		return mav;
	}

}
