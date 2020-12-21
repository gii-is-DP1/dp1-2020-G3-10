
package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;


	@GetMapping
	public String listadoPedidos(final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";

		Iterable<Pedido> pedidos = this.pedidoService.findAll();

		modelMap.addAttribute("pedidos", pedidos);

		return vista;
	}

	@GetMapping(path = "/new")
	public String crearPedido(final ModelMap modelMap) {

		String view = "pedidos/editPedido";

		modelMap.addAttribute("pedido", new Pedido());

		return view;

	}

	@PostMapping(path = "/save")
	public String salvarPedido(@Valid final Pedido pedido, final BindingResult result, final ModelMap modelMap) {
		String vista = "pedidos/listadoPedidos";
		if (result.hasErrors()) {
			modelMap.addAttribute("pedido", pedido);
			return "pedidos/editPedido";
		} else {
			this.pedidoService.save(pedido);
			modelMap.addAttribute("message", "Pedido guardo correctamente");
		}

		return vista;

	}
	@GetMapping(path = "/delete/{pedidoId}")
	public String borrarPedido(@PathVariable("pedidoId") final int pedidoId, final ModelMap modelMap) {

		String vista = "pedidos/listadoPedidos";

		Optional<Pedido> pedido = this.pedidoService.findPedidoById(pedidoId);

		if (pedido.isPresent()) {

			this.pedidoService.delete(pedido.get());
			modelMap.addAttribute("message", "Se ha borrado su pedido");
		} else {

			modelMap.addAttribute("message", "No se ha encontrado su pedido");
		}

		return vista;
	}

}
