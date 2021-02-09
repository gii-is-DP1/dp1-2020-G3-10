
package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
	
	@Query("SELECT ALL p from Pedido p where p.cliente.id =:idCliente and p.estado = 'CARRITO'")
    public Optional<Pedido> findProductosCarrito(@Param("idCliente") int idCliente);

}
