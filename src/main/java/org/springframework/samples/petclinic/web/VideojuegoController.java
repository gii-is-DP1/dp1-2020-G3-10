package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.VideojuegoService;
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

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public VideojuegoController(VideojuegoService videojuegoService) {
		this.videojuegoService = videojuegoService;
	}

	@GetMapping(value = "/videojuegos")
	public String showVideojuegosList(Map<String, Object> model) {
		List<Videojuego> videojuegos = this.videojuegoService.findVideojuegos();
		model.put("videojuegos", videojuegos);
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
	public String saveVideojuego(@Valid Videojuego vid, BindingResult result) {
		if (result.hasErrors()) {
			return "videojuegos/formCreateVideojuegos";
		} else {
			this.videojuegoService.saveVideojuego(vid);
			return "redirect:/videojuegos/" + vid.getId();
		}
	}

	@GetMapping(value = "/videojuegos/delete/{videojuegoId}")
	public String deleteVideojuego(@PathVariable("videojuegoId") int videojuegoId, final ModelMap modelmap) {
		Videojuego videojuegoBorrar = this.videojuegoService.findVideojuegoById(videojuegoId);
		String view = "/videojuegos/videojuegosList";

		if (videojuegoBorrar != null) {
			this.videojuegoService.delete(videojuegoBorrar);
			view = this.showVideojuegosList(modelmap);
		} else {
			modelmap.addAttribute("message", "ERROR!");
		}
		return view;
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
}
