
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;


	@Transactional
	public int conteoPedido() {
		return (int) this.pedidoRepository.count();

	}

	@Transactional
	public Iterable<Pedido> findAll() {
		return this.pedidoRepository.findAll();

	}

	@Transactional(readOnly = true)
	public Optional<Pedido> findPedidoById(final int id) {
		return this.pedidoRepository.findById(id);

	}

	@Transactional
	public void delete(final Pedido pedido) {
		this.pedidoRepository.delete(pedido);

	}

	@Transactional
	public void save(final Pedido pedido) {
		this.pedidoRepository.save(pedido);

	}
}
