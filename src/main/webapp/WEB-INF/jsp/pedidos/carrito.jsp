<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="pedido">
	<h1>CESTA DE LA COMPRA</h1>
	<c:out value="${mensaje}" />
	<table id="pedidosTable">
		<thead>
			<tr>
				<th style="width: 150px;">Producto</th>
				<th style="width: 200px;">Nombre</th>
				<th style="width: 120px">Cantidad</th>
				<th style="width: 120px">Precio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${productos}" var="producto">
				<tr>

					<td><img src=<c:out value="${producto.imagen}"/> width="250"height="350"></td>
					<td><c:out value="${producto.nombre}" /></td>
					<td><c:out value="1" /></td>
					<td><c:out value="${producto.precio}" /></td>


					<td><spring:url value="/pedidos/delete/{pedidoId}" var="pedidoUrl">
							<spring:param name="pedidoId" value="" />
					</spring:url> <a href="">Eliminar de carrito</a>
					</td>

				</tr>
			</c:forEach>
			<td>
			<spring:url value="/pedidos/pagar/{pedidoId}" var="pedidoUrl">
							<spring:param name="pedidoId" value="${pedidoId}" />
			</spring:url> <a href="">Pagar</a></td>
		</tbody>		
	</table>
</petclinic:layout>