package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Merchandasing;

import org.springframework.samples.petclinic.service.MerchandasingService;

import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MerchandasingController {

	@Autowired
	private final MerchandasingService merchandasingService;

//	@Autowired
//	private final ProductoService productoService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public MerchandasingController(final MerchandasingService merchandasingService
			//,final ProductoService productoService
			) {
		this.merchandasingService = merchandasingService;
		//this.productoService = productoService;

	}

	@GetMapping(value = "/merchandasings")
	public String showMerchandasingsList(Map<String, Object> model) {

		List<Merchandasing> merchandasings = this.merchandasingService.findMerchandasings();
		model.put("merchandasings", merchandasings);
		return "/merchandasings/MerchandasingsList";

	}

//	@GetMapping(value = "/merchandasings/{merchandasingId}/edit")
//	public String initUpdateForm(@PathVariable("merchandasingId") int merchandasingId, ModelMap model) {
//		Merchandasing p = this.merchandasingService.findMerchandasingById(merchandasingId);
//		model.put("merchandasing", p);
//		return "/merchandasings/createOrUpdateMerchandasingForm";
//	}
//
//	@PostMapping(value = "/merchandasings/{merchandasingId}/edit")
//	public String processUpdateMerchandasingForm(@Valid Merchandasing p, BindingResult result,
//			@PathVariable("merchandasingId") int merchandasingId) {
//		if (result.hasErrors()) {
//			return "/merchandasings/createOrUpdateMerchandasingForm";
//
//		} else {
//			Merchandasing pParaActualizar = this.merchandasingService.findMerchandasingById(merchandasingId);
//			try {
//				this.merchandasingService.saveMerchandasing(pParaActualizar);
//			} catch (Exception e) {
//				return "/merchandasings/createOrUpdateMerchandasingForm";
//			}
//
//			return "redirect:/merchandasings/{merchandasingId}";
//		}
//	}
	
	
	
	@GetMapping(value = "/vendedor/merchandasings/{merchandasingId}/edit")
	public String editVaccine(@PathVariable("merchandasingId") final int merchandasingId, final ModelMap modelMap) {

		Merchandasing merchandasing= this.merchandasingService.findMerchandasingById(merchandasingId);
		modelMap.addAttribute("merchandasing", merchandasing);
		return "merchandasings/createOrUpdateMerchandasingForm";
	}

	@PostMapping(value = "/vendedor/merchandasings/{merchandasingId}/edit")
	public String editingVaccine(@Valid final Merchandasing merchandasing, final BindingResult result, @PathVariable("merchandasingId") final int merchandasingId, final ModelMap modelMap) {

		String view;
		if (result.hasErrors()) {
			Merchandasing m = this.merchandasingService.findMerchandasingById(merchandasingId);
			modelMap.addAttribute("merchandasing", m);
			view = "merchandasings/createOrUpdateMerchandasingForm";

		} else {
			Merchandasing m = this.merchandasingService.findMerchandasingById(merchandasingId);
			//modelMap.addAttribute("merchandasing", merchandasing);
			merchandasing.setId(merchandasingId);
			this.merchandasingService.saveMerchandasing(merchandasing);
			view = "welcome";
		}
		return view;
	}
	
	

	@GetMapping("/merchandasings/{merchandasingId}")
	public String showMerchandasing(@PathVariable("merchandasingId") int merchandasingId, Map<String, Object> model) {
		Merchandasing merchandasing = this.merchandasingService.findMerchandasingById(merchandasingId);
		model.put("merchandasing", merchandasing);
		return "/merchandasings/merchandasingDetails";
	}

	@GetMapping(value = "/vendedor/merchandasings/new")
	public String initCreationMerchandasing(Map<String, Object> model) {
		Merchandasing p = new Merchandasing();
		model.put("merchandasing", p);
		return "/merchandasings/createOrUpdateMerchandasingForm";
	}

	@PostMapping(value = "/vendedor/merchandasings/new")
	public String initCreationMerchandasing(@Valid Merchandasing mer, BindingResult result) {
		if (result.hasErrors()) {
			return "/merchandasings/createOrUpdateMerchandasingForm";
		} else {
			this.merchandasingService.saveMerchandasing(mer);
			return "redirect:/merchandasings/" + mer.getId();
		}
	}

	//@DeleteMapping("/merchandasings/{merchandasingId}/delete")
	@GetMapping("/vendedor/merchandasings/{merchandasingId}/delete")
	public String processBorrarMerchandasing(@PathVariable("merchandasingId") int merchandasingId) {
		this.merchandasingService.deleteMerchandasing(merchandasingId);
		return "/merchandasings/MerchandasingsList";

	}

}
