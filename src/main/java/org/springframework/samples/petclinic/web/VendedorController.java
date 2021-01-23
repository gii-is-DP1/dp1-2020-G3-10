
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.samples.petclinic.service.VideojuegoService;
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
	
	@Autowired
	private PeliculaService peliculaService;
	
	@Autowired
	private VideojuegoService videojuegoService;
	
	@Autowired
	private MerchandasingService merchandasingService;


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

		System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    edit ");

		if (result.hasErrors()) {

			mp.addAttribute("vendedor", vendedor);
			mp.addAttribute("message", "El vendedor no se ha podido actualizar correctamente");
			return VendedorController.VIEWS_VENDEDOR_CREATE_OR_UPDATE_FORM;
		} else {

			System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    2 ");

			vendedor.setId(vendedorId);

			System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    2,5 ");

			this.vendedorService.save(vendedor);

			System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee    3 ");

			return "redirect:/vendedores/{vendedorId}";
		}
	}
	
	
	//VISTA PRODUCTOSVENDEDOR
	@GetMapping(value = "/{vendedorId}/productos")
	public String listProducts(@PathVariable("vendedorId") final int vendedorId, ModelMap modelMap) {
		
		if(this.vendedorService.obtenerPeliculas(vendedorId)!=null) {
			List<Pelicula> peliculas = new ArrayList<>();
			peliculas.addAll(this.vendedorService.obtenerPeliculas(vendedorId));
			modelMap.addAttribute("peliculas", peliculas);
		}
		if(this.vendedorService.obtenerVideojuegos(vendedorId)!=null) {
			List<Videojuego> videojuegos = new ArrayList<>();
			videojuegos.addAll(this.vendedorService.obtenerVideojuegos(vendedorId));
			modelMap.addAttribute("videojuegos", videojuegos);
		}
		if(this.vendedorService.obtenerMerchandasings(vendedorId)!=null) {
			List<Merchandasing> merch = new ArrayList<>();
			merch.addAll(this.vendedorService.obtenerMerchandasings(vendedorId));
			modelMap.addAttribute("merch", merch);
		}
		
		return "/productos/productosVendedor";
	}

}
