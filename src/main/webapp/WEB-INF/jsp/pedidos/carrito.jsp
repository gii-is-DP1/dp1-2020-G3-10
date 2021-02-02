<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="cestaCompra">
	<h1>CESTA DE LA COMPRA</h1>

	<h3>
		<c:out value="${mensaje}" />
	</h3>
	<c:choose>
		<c:when test="${carritoVacio==true}">
			<c:out value="¡Vaya! No tienes productos en el carrito." />
		</c:when>
		<c:otherwise>

			<h3>
				<c:out value="Precio Total: ${precioTotal}" />
			</h3>

			<table class="table table-stripped" style="text-align: center;"
				id="pedidosTable">
				<thead>
					<tr>
						<th style="width: 20%;"><center>Producto</center></th>
						<th style="width: 20%;"><center>Nombre</center></th>
						<th style="width: 20%;"><center>Cantidad</center></th>
						<th style="width: 20%;"><center>Precio</center></th>
						<th style="width: 20%;"></th>
					</tr>
				</thead>
				<tbody>

					<c:if test="true" var="peliculasNoVacio">
						<c:forEach items="${peliculas}" var="pelicula">
							<tr>

								<td><img src=<c:out value="${pelicula.imagen}"/>
									width="250" height="350"></td>
								<td><c:out value="${pelicula.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${pelicula.precio}" /></td>

								<td><spring:url
										value="/pedidos/eliminaProductoCarrito/{pedidoId}/{productoId}/{tipo}"
										var="peliculaUrl">
										<spring:param name="productoId" value="${pelicula.id}" />
										<spring:param name="pedidoId" value="${pedidoId}" />
										<spring:param name="tipo" value="${'PELICULA'}" />
									</spring:url> <a href="${fn:escapeXml(peliculaUrl)}" class="btn btn-default">Eliminar</a>
								</td>

							</tr>
						</c:forEach>
					</c:if>

					<c:if test="true" var="videojuegosNoVacio">
						<c:forEach items="${videojuegos}" var="videojuego">
							<tr>

								<td><img src=<c:out value="${videojuego.imagen}"/>
									width="70%" height="70%"></td>
								<td><c:out value="${videojuego.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${videojuego.precio}" /></td>

								<td><spring:url
										value="/pedidos/eliminaProductoCarrito/{pedidoId}/{productoId}/{tipo}"
										var="videojuegoUrl">
										<spring:param name="productoId" value="${videojuego.id}" />
										<spring:param name="pedidoId" value="${pedidoId}" />
										<spring:param name="tipo" value="${'VIDEOJUEGO'}" />
									</spring:url> <a href="${fn:escapeXml(videojuegoUrl)}"
									class="btn btn-default">Eliminar</a></td>

							</tr>
						</c:forEach>
					</c:if>

					<c:if test="true" var="merchandasingsNoVacio">
						<c:forEach items="${merchandasings}" var="merchandasing">
							<tr>

								<td><img src=<c:out value="${merchandasing.imagen}"/>
									width="70%" height="70%"></td>
								<td><c:out value="${merchandasing.nombre}" /></td>
								<td><c:out value="1" /></td>
								<td><c:out value="${merchandasing.precio}" /></td>

								<td><spring:url
										value="/pedidos/eliminaProductoCarrito/{pedidoId}/{productoId}/{tipo}"
										var="merchandasingUrl">
										<spring:param name="productoId" value="${merchandasing.id}" />
										<spring:param name="pedidoId" value="${pedidoId}" />
										<spring:param name="tipo" value="${'MERCHANDASING'}" />
									</spring:url> <a href="${fn:escapeXml(merchandasingUrl)}"
									class="btn btn-default">Eliminar</a></td>

							</tr>
						</c:forEach>
					</c:if>

					<td><spring:url value="/pedidos/{pedidoId}/pagar"
							var="pedidoUrl">
							<spring:param name="pedidoId" value="${pedidoId}" />
						</spring:url> <a href="${fn:escapeXml(pedidoUrl)}" class="btn btn-default">Pagar</a></td>

				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</petclinic:layout>