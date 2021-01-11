package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.service.MerchandasingService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.VideojuegoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CestaController {

	@Autowired
	PeliculaService peliculaService;

	@Autowired
	VideojuegoService videojuegoService;

	@Autowired
	MerchandasingService merchandisingService;

//	private ArrayList<LineaPedido> obtenerCarrito(HttpServletRequest request) {
//	    ArrayList<LineaPedido> carrito = (ArrayList<LineaPedido>) request.getSession().getAttribute("carrito");
//	    if (carrito == null) {
//	        carrito = new ArrayList<>();
//	    }
//	    return carrito;
//	}
//
//	private void guardarCarrito(ArrayList<LineaPedido> carrito, HttpServletRequest request) {
//	    request.getSession().setAttribute("carrito", carrito);
//	}

	@GetMapping(path = "/peliculas/{peliculaId}/addCesta")
	public String añadirCarrito(@PathVariable("peliculaId") int peliculaId, ModelMap model, Map<String, Object> modelo) {
		Pelicula p = this.peliculaService.findPeliculaById(peliculaId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String cliente = userDetail.getUsername();
		String view ="cesta/cestaCompra";
		
		if(auth.getPrincipal() == "anonymousUser") {
			model.addAttribute("mensaje", "¡Debes estar registrado para añadir al carrito!");
		}else {
			LineaPedido lineaPedido = new LineaPedido();
			lineaPedido.setCantidad(1);
			lineaPedido.setPelicula(p);
			
			model.addAttribute("mensaje", "¡Producto añadido!");
			modelo.put("linea", lineaPedido);
			modelo.put("producto", p);
			modelo.put("usuario", cliente);
		}
		return view;
		
	}
	

}
