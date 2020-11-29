package org.springframework.samples.petclinic.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(final ProductService productService) {
		this.productService = productService;

	}

	// muestra la vista con los detalles del producto,
	//TODO: aquí se pondrá también la vista de error (si no existe un producto con esa
	// Id)

	@GetMapping(value = "/cliente/products/{productId}")
	public String showProducto(@PathVariable final int productId, final Map<String, Object> model) {

		Product product = this.productService.findProductById(productId);

		model.put("product", product);
		return "products/productShow";

	}
}