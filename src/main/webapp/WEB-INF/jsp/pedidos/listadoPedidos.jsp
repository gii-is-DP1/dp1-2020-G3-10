<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="pedidos">
	<h2>Pedidos</h2>

	<table id="pedidosTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Estado</th>
				<th style="width: 20%;">Precio Total</th>
				<th style="width: 20%;">Fecha</th>
				<th style="width: 20%;">Dirección</th>
				<th style="width: 10%;"></th>
				<th style="width: 10%;"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pedidos}" var="pedido">
				<tr>

					<td><c:out value="${pedido.estado}" /></td>
					<td><c:out value="${pedido.precioTotal}" /></td>
					<td><c:out value="${pedido.fecha}" /></td>
					<td><c:out value="${pedido.direccionEnvio}" /></td>
					
					<td>
						<spring:url value="/pedidos/detalles/{pedidoId}" var="pedidoDetallesUrl">
								<spring:param name="pedidoId" value="${pedido.id}" />
						</spring:url>
						<a href="${fn:escapeXml(pedidoDetallesUrl)}">Detalles</a>
						</td>
					
					<td><c:if test="${pedido.estado == 'PENDIENTE'}">
							<spring:url value="/pedidos/cancelarPedido/{pedidoId}"
								var="pedidoUrl">
								<spring:param name="pedidoId" value="${pedido.id}" />
							</spring:url>
							<a href="${fn:escapeXml(pedidoUrl)}">Cancelar</a>
							

						</c:if></td>
						

				</tr>


			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>